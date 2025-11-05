# look for files in this layer first
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# SRC_URI += " \
#     file://console-bridge-vendor_CHANGE_CMAKELIST.patch \
# "
do_compile[network] = "1"

ROS_BUILDTOOL_DEPENDS += " \
    ament-cmake-vendor-package \
    python3-vcstool \
    python3-vcstool-native \
"

INSANE_SKIP:${PN} += "dev-so"
INSANE_SKIP:${PN} += "pkgconfig"