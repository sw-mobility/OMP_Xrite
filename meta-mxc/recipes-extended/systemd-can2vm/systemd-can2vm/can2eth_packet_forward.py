import os, argparse
import socket
import threading
import select

def checkPortNumber(value):
    port = int(value)
    if(port <= 0 or port > 0xffff):
        raise argparse.ArgumentTypeError("Invalid argument %s. Port Number should be between 1 and 65535" % value)
    return port

parser = argparse.ArgumentParser()
parser.add_argument('-p', '--port', help=' : set port number for packets to forward. default 4500', type=checkPortNumber, default=4500)
parser.add_argument('-t', '--tport', help=' : set port number for packets to retranmit to MCU. default 45000', type=checkPortNumber, default=45000)
parser.add_argument('-i', '--interface', help=' : Please set the interface for arp to get Guest IP. default xenbr0', default='xenbr0')
args = parser.parse_args()

us = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM);
us.bind(("169.254.169.185",4480))
us2 = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM);

txreceiver = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM);
txreceiver.bind(('', args.tport))

guests = []
def updateGuestList():
    global guests
    guests = os.popen("arp -n -i " + args.interface + " | sed '1d' | awk -F ' ' '{print $1}'").read()[:-1].split('\n')
    threading.Timer(10, updateGuestList).start()
    guests.append("")

updateGuestList()

READONLY = ( select.POLLIN | select.POLLPRI )
txaddr = None;

poller = select.poll()
poller.register(us, READONLY)
poller.register(txreceiver, READONLY)

fd_to_select = { us.fileno(): us, txreceiver.fileno(): txreceiver, }

while True:
    events = poller.poll()

    for fd, flag in events:
        s = fd_to_select[fd]

        if flag & (select.POLLIN | select.POLLPRI):
            msg = s.recvfrom(1024)
#            print ("Message: {0} from {1}".format(msg[0], msg[1]))
            if s is us:
                for i in guests:
                    us2.sendto(msg[0], (i, args.port))
                    txaddr = msg[1]
            elif (s is txreceiver) and (txaddr is not None):
#                print ("transmit addr: {0}".format(txaddr))
                us.sendto(msg[0], txaddr)

