# Adaptive AUTOSAR
Goal : Adaptive AUTOSAR Functional Cluster Analysis  
Based on : ETAS Adaptive AUTOSAR Platform  
Reference : Adaptive AUTOSAR Realease R21-11  



## Overview
This folder focuses on analyzing and understanding the Functional Clusters (FCs) defined in the Adaptive AUTOSAR Platform.  
The analysis is conducted based on the ETAS Adaptive Platform, following the guidelines and specifications provided in AUTOSAR Adaptive Platform Release R21-11.



## Build Instructions for Adaptive AUTOSAR Application (Linux)
Instructions for building an Adaptive Application in a Linux environment with RTA-VRTE installed.  
To build the Adaptive Application, follow these steps in the `build` folder of your project:
| Seq | Command | Description |
| --- | ------- | ----------- |
| 1 | `source /opt/etas/rta-vrte-<version>/linux-toolchain/environment-setup-cortexa57-etas-linux` | Set up environment variables for cross-compilation (run once per shell session). If you have a target board toolchain, source the corresponding setup script. |
| 2 | `cmake -G "Unix Makefiles" -DCMAKE_TOOLCHAIN_FILE=/opt/etas/rta-vrte-<version>/toolchain_files/linux_aarch64_toolchain.cmake -DSDK_PATH=/opt/etas/rta-vrte-<version>/sdk ..` | Generate build files using CMake |
| 3 | `make install` | Build and install the application |
