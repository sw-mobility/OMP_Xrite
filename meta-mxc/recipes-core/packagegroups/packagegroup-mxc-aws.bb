SUMMARY = "Utilities for MXC AWS Applications"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

AWS_PACKAGES = " \
    aws-iot-fleetwise-edge \
    aws-cli \
    aws-sdk-cpp \
    aws-iot-device-sdk-python-v2 \
    aws-iot-device-sdk-cpp-v2 \
    aws-crt-cpp \
    aws-crt-python  \
    greengrass-bin \
    corretto-11-bin \
"

RDEPENDS:${PN} += "\
    ${AWS_PACKAGES} \
"

ERROR_QA:remove = "version-going-backwards"
