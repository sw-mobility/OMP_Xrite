# Enable Xen and add Xen Packages
require ${@bb.utils.contains('DISTRO_FEATURES', 'xen', 'mxc-image-xen.inc', '', d)}

IMAGE_INSTALL:append = "${@bb.utils.contains('DISTRO_FEATURES', 'xen', ' xen-examples-default ', '', d)}"
