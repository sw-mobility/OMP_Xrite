SUMMARY = "super minimal image for MXC"

DESCRIPTION = "For FOTA A/B Update and emergency booting."

MXC_TINY_IMAGE_EXTRA_INSTALL ?= ""

require mxc-tiny-image.inc

IMAGE_FEATURES:remove = "package-management"


IMAGE_INSTALL += " \
	${MXC_TINY_IMAGE_EXTRA_INSTALL} \
    packagegroup-mxc-boot \
    packagegroup-mxc-utils \
"
