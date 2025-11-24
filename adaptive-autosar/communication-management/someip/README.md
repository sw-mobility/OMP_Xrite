# SOME/IP Communication on Adaptive AUTOSAR
This repository provides an implementation of SOME/IP communication based on Adaptive AUTOSAR.
It includes Subscriber and Publisher components for testing SOME/IP Event, Field, and Method (Get/Set/Notify) functionalities.

### 01. Platform & Build Environment
- Adaptive AUTOSAR Platform : ETAS ISOLAR-VRTE/RTA-VRTE v3.9.0
  >  Adaptive AUTOSAR Version : R21-11
- Target Board OS : Linux (aarch64)

### 02. SOME/IP Service Definition
- **Service (Service ID / Instance ID):** VehicleControlService (0x100 / 1)

### 03. SOME/IP Interface Definition

| **Item** | **Event** | **Method** | **Field** |
|-----------|------------|-------------|-------------|
| **Name** | VehicleSpeed | getEvDriveReadyStatus | ParkingBrakeStatus |
| **ID** | Event ID: 1 | Method ID: 1 | Event ID: 2<br>Getter ID: 3<br>Setter ID: 4 |
| **Data** | VehicleSpd | Input: `EvDrvRdySta_Req`<br>Output: `EvDrvRdySta_Resp` | PrkBrkSta |
| **Data Type** | `sint16` | `uint8`<br>(0: None / 1: Drivable / 2: N/A / 3: Error) | `uint8`<br>(0: Off / 1: On / 2: Error) |


### 04. Sample Log output
The following examples show Subscriber outputs when interacting with the Publisher for Event, Method, and Field.  
These logs demonstrate the functionality and communication flow of the SOME/IP service.

#### (1) Event
- The Subscriber receives event data from the Publisher and outputs it to the log.
```
[Event] VehicleSpeed : 50
```

#### (2) Method
- The Subscriber sends a request to the Publisher (input value: 1), 
receives the response, and logs it. 
- The status is displayed according to the response value (None / Drivable / N/A / Error).
```
[Method] Request Drive Ready Status (input value : 1)
[Method] getDriveReadyStatus : 1 (Drivable)
```

#### (3) Field
- Publisher Notify: Initializes or updates the Field value → `[Field – Notify]`
- Subscriber Get: Reads the Field value and logs it (e.g., 1) → `[Field – Get]`
- Subscriber Set: Sends a Set request → Publisher Notify → Subscriber logs the change → `[Field – Set(Req)]` / `[Field – Notify]`
- Publisher Set Response: Logs whether the Set operation was successful → `[Field – Set(Res)]`
```
[Field – Notify] ParkingBrakeStatus : 1
[Field – Get] ParkingBrakeStatus : 1(On)
[Field – Set (Req)] ParkingBrakeStatus : 0
[Field – Notify] ParkingBrakeStatus : 0
[Field – Set (Res)] ParkingBrakeStatus : 0
```

