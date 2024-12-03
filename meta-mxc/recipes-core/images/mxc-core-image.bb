SUMMARY = "HL Mando Core Image for MXC Application"

DESCRIPTION = "Core Image for MXC Application"

ARAGO_TINY_IMAGE_EXTRA_INSTALL ?= ""

require mxc-core-image.inc
require mxc-core-packagegroups.bb


IMAGE_FEATURES:remove = "package-management"

IMAGE_INSTALL += " \
	mxc-core-packagegroups \
	${ARAGO_TINY_IMAGE_EXTRA_INSTALL} \
"
