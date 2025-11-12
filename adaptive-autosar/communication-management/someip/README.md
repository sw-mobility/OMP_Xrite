# SOME/IP Communication on Adaptive AUTOSAR
This repository provides an implementation of SOME/IP communication based on Adaptive AUTOSAR.
It includes Subscriber and Publisher components for testing SOME/IP Event, Field, and Method (Get/Set/Notify) functionalities.

### 01. SOME/IP Service Definition
- **Service (Service ID / Instance ID):** VehicleControlService (0x100 / 1)


### 02. SOME/IP Interface Definition

| **Item** | **Event** | **Method** | **Field** |
|-----------|------------|-------------|-------------|
| **Name** | VehicleSpeed | getEvDriveReadyStatus | ParkingBrakeStatus |
| **ID** | Event ID: 1 | Method ID: 1 | Event ID: 2<br>Getter ID: 3<br>Setter ID: 4 |
| **Data** | VehicleSpd | Input: `EvDrvRdySta_Req`<br>Output: `EvDrvRdySta_Resp` | PrkBrkSta |
| **Data Type** | `sint16` | `uint8`<br>(0: None / 1: Drivable / 2: N/A / 3: Error) | `uint8`<br>(0: Off / 1: On / 2: Error) |