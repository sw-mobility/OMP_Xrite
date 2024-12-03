SUMMARY = "super minimal image for MXC"

DESCRIPTION = "Nor-Flash and eMMC flashing scripts included."

MXC_TINY_IMAGE_EXTRA_INSTALL ?= ""

require mxc-tiny-image.inc

IMAGE_FEATURES:remove = "package-management"


IMAGE_INSTALL += " \
	${MXC_TINY_IMAGE_EXTRA_INSTALL} \
    packagegroup-mxc-boot \
"
