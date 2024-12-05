# OMP_Xrite
Yocto-based Platform SW. <br>

To run this layer, the yocto recipes from TI are required. Please follow below instructions to apply this layer into TI Yocto SDK.

To download source files provided by TI, run:
```
$ mkdir tisdk
$ cd tisdk
$ git clone https://git.ti.com/git/arago-project/oe-layersetup.git yocto-build
$ cd yocto-build
$ ./oe-layertool-setup.sh -f configs/processor-sdk-linux/processor-sdk-linux-09_01_00_06.txt 
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
$ git clone https://git.ti.com/git/edgeai/meta-edgeai.git -b kirkstone
$ cd meta-edgeai
$ git reset --hard 02ce7e60b449c72def00f26afc549034cbd82ba9
```
After download meta-edgeai, should be modify u-boot and kernel file: <br>
TARGET FILE : meta-edgeai/recipes-bsp/u-boot/u-boot-ti-staging_%.bbappend <br>
Erase below line in SRC_URI:append:j721e
```
file://0001-HACK-arm-k3-j721e-sk-RPi-header-pins-set-to-default-.patch
```

TARGET FILE : meta-edgeai/recipes-kernel/linux/rtos-mem-map.inc <br>
Erase below line in top
```
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti-staging/${MACHINE}:"
```
And then, add below line in top
```
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti-staging/j721e-evm:"
```

