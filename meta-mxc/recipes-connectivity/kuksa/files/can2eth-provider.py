#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import argparse
import socket
import json
import csv
import struct
from typing import List

import asyncio
from asyncio import Queue
import inspect
import cantools


# KUKSA client library imports
from kuksa_client.grpc import Datapoint, DataEntry, DataType, Metadata, EntryUpdate, SubscribeEntry, View, Field
from kuksa_client.grpc.aio import VSSClient as AioVSSClient

# Configuration (fixed/defaults)
ETH_IP = "127.0.0.1"
ETHA_PORT = 4500   # incoming CAN-over-UDP port
ETHB_PORT = 45000  # outgoing CAN-over-UDP port

DATABROKER_IP = "0.0.0.0"
DATABROKER_PORT = 55555

# Global caches
db = None
can2vss_map = {}      # SignalName -> VSS
vss2can_map = {}      # VSS -> SignalName
vss_types = {}        # VSS -> {"type": ..., "datatype": ...}
signal_to_msg = {}    # SignalName -> cantools.db.Message

def log(msg: str):
    frame = inspect.currentframe().f_back
    func_name = frame.f_code.co_name
    line_no = frame.f_lineno
    print(f"[{func_name}:{line_no}] {msg}", flush=True)

def load_dbc_mapping_and_vss(dbc_path: str, csv_path: str, vss_json_path: str):
    global db, vss2can_map, can2vss_map, vss_types, signal_to_msg

    log(f"[INIT] Loading DBC: {dbc_path}")
    db = cantools.database.load_file(dbc_path)
    signal_to_msg = {}
    for msg in db.messages:
        for sig in msg.signals:
            signal_to_msg[sig.name] = msg
    log(f"[INIT] Loaded {len(signal_to_msg)} signal-to-message mappings")

    log(f"[INIT] Loading mapping CSV: {csv_path}")
    with open(csv_path, newline='', encoding='utf-8') as f:
        reader = csv.DictReader(f)
        for row in reader:
            type = row.get("TYPE") or row.get("type")
            sig = row.get("CAN") or row.get("can")
            vss = row.get("VSS") or row.get("vss")
            if sig and vss:
                sig, vss = sig.strip(), vss.strip()
                if type == "can2vss":
                    can2vss_map[sig] = vss
                elif type == "vss2can":
                    vss2can_map[vss] = sig
    log(f"[INIT] Loaded {len(vss2can_map)} signal mappings and {len(can2vss_map)} vss mappings")

    log(f"[INIT] Loading VSS schema: {vss_json_path}")
    with open(vss_json_path, encoding="utf-8") as f:
        vss_json = json.load(f)

    # --- traverse_vss_node now stores into vss_types (NOT vss2can_map) ---
    def traverse_vss_node(node, path=""):
        name = node.get("name")
        current_path = f"{path}.{name}" if name else path

        # leaf node: store in vss_types
        if isinstance(node, dict) and ("datatype" in node or "type" in node):
            # store even if one of them is missing; value may be None
            vss_types[current_path] = {
                "datatype": node.get("datatype"),
                "type": node.get("type")
            }

        # children 순회 (children can be dict or list)
        children = node.get("children", {})
        if isinstance(children, dict):
            for child_name, child_node in children.items():
                child_path = f"{current_path}.{child_name}" if current_path else child_name
                traverse_vss_node(child_node, child_path)
        elif isinstance(children, list):
            for cnode in children:
                # try get name from cnode or use 'unnamed'
                cname = cnode.get("name") if isinstance(cnode, dict) else None
                child_path = f"{current_path}.{cname}" if cname else current_path
                traverse_vss_node(cnode, child_path)

    # 루트 탐색: top-level keys (e.g., "Vehicle")
    for root_name, root_node in vss_json.items():
        traverse_vss_node(root_node, root_name)

    log(f"[INIT] Loaded {len(vss_types)} VSS definitions (vss_types)")
    # signal_to_msg already cached above

async def vss_update(updates: List[EntryUpdate], socket: socket.socket):
    if not vss2can_map:
        log("No mapping information available")
        return

    for update in updates:
        if update.entry.value is not None:
            signal_name = vss2can_map.get(update.entry.path)
            if signal_name is None:
                log(f"[WARN] No mapping for {signal_name}")
                continue

            msg = signal_to_msg.get(signal_name)
            if not msg:
                log(f"[WARN] Signal '{signal_name}' not found in DBC message map")
                continue

            try:
                msg_data = {s.name: 0 for s in msg.signals}
                msg_data[signal_name] = update.entry.value.value
                data_bytes = msg.encode(msg_data)
                frame = struct.pack(">I", msg.frame_id) + struct.pack(">I", msg.length) + data_bytes
                socket.sendto(frame, (ETH_IP, ETHB_PORT))
                # log(f"[TX] Sent msg_id=0x{msg.frame_id:X}, VSS={signal_name}, value={update.entry.value.value}")
            except Exception as e:
                log(f"[ERROR] while processing {signal_name}: {e}")
    
async def vss_to_can_processor(client: AioVSSClient, sock_out: socket.socket):
    if db is None or not vss2can_map:
        raise RuntimeError("DBC/VSS not loaded")
    
    entries: List[SubscribeEntry] = []
    for name in vss2can_map.keys():
            # Always subscribe to target
            subscribe_entry = SubscribeEntry(name, View.FIELDS, [Field.VALUE])
            log(f"Subscribe entry: {subscribe_entry}")
            entries.append(subscribe_entry)
    while True: 
        async for updates in client.subscribe(entries=entries):
            log(f"Received update of length {len(updates)}")
            await vss_update(updates, sock_out)

async def can_to_vss_processor(client: AioVSSClient, can_queue: Queue):
    if db is None or not vss2can_map:
        raise RuntimeError("DBC/VSS not loaded")
    
    message_cache = {}

    while True:
        data = await can_queue.get()
        
        if not data or len(data) < 12:
            log(f"Invalid packet received")
            continue

        msg_id = struct.unpack('>I', data[0:4])[0]

        if msg_id not in message_cache:
            try:
                message_cache[msg_id] = db.get_message_by_frame_id(msg_id)
            except KeyError:
                message_cache[msg_id] = None

        message = message_cache[msg_id]
        if message is None:
            continue
        
        dlc = message.length
        payload = data[8:8+dlc]

        try:
            decoded = message.decode(payload, decode_choices=False)
        except Exception as e:
            log(f"[ERROR] decode failed for 0x{msg_id:X}: {e}")
            continue

        entry_updates = []

        for sig_name in can2vss_map.keys():
            if sig_name in decoded:
                vss_path = can2vss_map[sig_name]
                val = decoded[sig_name]
                vss_info = vss_types.get(vss_path, {})

                # 실제 set() 준비
                try:
                    dtype_str = vss_info.get("datatype") or vss_info.get("type") or "FLOAT"
                    meta_dtype = getattr(DataType, dtype_str.upper(), DataType.UNSPECIFIED)
                    dp = Datapoint(value=val)
                    de = DataEntry(path=vss_path, value=dp, metadata=Metadata(data_type=meta_dtype))
                    eu = EntryUpdate(de, (Field.VALUE,))
                    entry_updates.append(eu)
#                    log(f"[RX] {vss_path}: {val} (type={vss_info.get('type')}, datatype={vss_info.get('datatype')})")
                except Exception as e:
                    log(f"[ERROR] building EntryUpdate for {vss_path}: {e}")

        if entry_updates:
            try:
                await client.set(updates=tuple(entry_updates))
            except Exception as e:
                log(f"[ERROR] client.set failed: {e}")


async def can_receiver(sock: socket.socket, queue: Queue,):
    """
    Receive CAN-over-UDP frames, decode using DBC, map to VSS, 
    and update KUKSA Databroker with safe metadata check.
    """
    if db is None or not vss2can_map:
        raise RuntimeError("DBC/VSS not loaded")
    
    log(f"Listening for CAN data on UDP port {ETHA_PORT}...")
    loop = asyncio.get_running_loop()

    while True:
        data, addr = await loop.sock_recvfrom(sock, 1024)
        await queue.put(data)

async def main(mapping_file: str, dbc_file: str, vss_file: str):
    sock_in = None
    sock_out = None

    try:
        # NOTE: load order is (dbc, csv, vss_json)
        load_dbc_mapping_and_vss(dbc_file, mapping_file, vss_file)

        # prepare UDP sockets
        sock_in = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        # bind to ETH_IP:ETHA_PORT for incoming CAN frames
        sock_in.bind((ETH_IP, ETHA_PORT))
        # set non-blocking so loop.sock_recvfrom works reliably
        sock_in.setblocking(False)

        sock_out = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

        can_queue = Queue(maxsize=1024)

        async with AioVSSClient(DATABROKER_IP, DATABROKER_PORT) as client:
            log(f"Connected to KUKSA Databroker at {DATABROKER_IP}:{DATABROKER_PORT}")
            t_can_receiver = asyncio.create_task(can_receiver(sock_in, can_queue))
            t_can_to_vss_processor = asyncio.create_task(can_to_vss_processor(client, can_queue))
            t_vss_to_can_processor = asyncio.create_task(vss_to_can_processor(client, sock_out))

            await asyncio.wait([t_can_receiver, t_can_to_vss_processor, t_vss_to_can_processor], return_when=asyncio.FIRST_EXCEPTION)
    except socket.error as e:
        log(f"Socket error: {e}")
    except Exception as e:
        log(f"Unhandled exception in main: {e}")
    finally:
        log("Shutting down sockets.")
        if sock_in: sock_in.close()
        if sock_out: sock_out.close()


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="CAN <-> VSS Bridge")
    parser.add_argument(
        "--mapping-file",
        default="/etc/kuksa/vss_signal_mapping.csv",
        help="Path to the mapping CSV file (default: /etc/kuksa/vss_signal_mapping.csv)"
    )
    parser.add_argument(
        "--dbc-file",
        default="/etc/kuksa/MXC_CAN.dbc",
        help="Path to the DBC file (default: /etc/kuksa/MXC_CAN.dbc)"
    )
    parser.add_argument(
        "--vss",
        dest="vss",
        default="/etc/kuksa/vss_4.0.json",
        help="Path to the VSS JSON file (default: /etc/kuksa/vss_4.0.json)"
    )
    args = parser.parse_args()

    try:
        # pass args in: mapping_file, dbc_file, vss_file
        asyncio.run(main(args.mapping_file, args.dbc_file, args.vss))
    except KeyboardInterrupt:
        log("User terminated the program.")
