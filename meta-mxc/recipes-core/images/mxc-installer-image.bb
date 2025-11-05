SUMMARY = "Installer image for MXC"

DESCRIPTION = "Written to the External memory and used to install other images, Nor-Flash and eMMC flashing scripts included."

MXC_TINY_IMAGE_EXTRA_INSTALL ?= ""

require mxc-tiny-image.inc

IMAGE_FEATURES:remove = "package-management"


IMAGE_INSTALL += " \
	${MXC_TINY_IMAGE_EXTRA_INSTALL} \
    packagegroup-mxc-boot \
"
