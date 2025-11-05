# require mxc-core-image.bb
SUMMARY = "Packagegroups for MXC"
LICENSE = "MIT"

PR:append = "_tisdk_2"

IMAGE_INSTALL:append = " \
    packagegroup-mxc-utils \
    packagegroup-mxc-devtools \
"