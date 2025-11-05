SUMMARY = "HL Mando Core Image for MXC Application"

DESCRIPTION = "Core Image for MXC Application"

ARAGO_TINY_IMAGE_EXTRA_INSTALL ?= ""

require mxc-core-image.inc

IMAGE_FEATURES:remove = "package-management"

IMAGE_INSTALL += " \
	${@bb.utils.contains('DISTRO_FEATURES', 'xen', '', 'packagegroup-mxc-boot', d)} \
	packagegroup-mxc-aws \
	packagegroup-mxc-utils \
    packagegroup-mxc-devtools \
	${ARAGO_TINY_IMAGE_EXTRA_INSTALL} \
"

IMAGE_INSTALL:append = "${@bb.utils.contains('DISTRO_FEATURES', 'ros1', ' packagegroup-mxc-ros1 ', '', d)}"
IMAGE_INSTALL:append = "${@bb.utils.contains('DISTRO_FEATURES', 'ros2', ' packagegroup-mxc-ros2 ', '', d)}"