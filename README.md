# OMP_Xrite
Yocto-based Platform SW. <br>

To run this layer, the yocto recipes from TI are required. Please follow below instructions to apply this layer into TI Yocto SDK.

To download source files provided by TI, run:
```
$ mkdir tisdk
$ cd tisdk
$ git clone https://git.ti.com/git/arago-project/oe-layersetup.git yocto-build
$ cd yocto-build
$ ./oe-layertool-setup.sh -f configs/processor-sdk-linux/processor-sdk-linux-10_00_08_06.txt 
```


To install all prerequisites before starting any build, run:
```
$ cd build
$ . conf/setenv
```


To set a MACHINE(Chipset) TYPE Globally on your recipes, add this configuration into conf/local.conf:
```
MACHINE="j721e-hs-evm"
```

To download TI edge AI SDK, run:
```
$ cd yocto-build/sources
$ git clone https://git.ti.com/git/edgeai/meta-edgeai.git -b 10.01.00.06
```
After download meta-edgeai, should be modify u-boot and kernel file: <br>
TARGET FILE : meta-edgeai/recipes-bsp/u-boot/u-boot-ti-staging_%.bbappend <br>
-> Erase or comment out all contents of the file.

TARGET FILE : meta-edgeai/recipes-kernel/linux/linux-ti-staging_%.bbappend <br>
-> Erase or comment out all contents of the file.

TARGET FILE : meta-edgeai/recipes-kernel/linux/linux-ti-staging-rt_%.bbappend <br>
-> Erase or comment out all contents of the file.