# Enable Xen and add Xen Packages
include recipes-extended/images/xen-image-minimal.bb

# SystemD needs CONFIG_XEN_GNTDEV as built-in instead of module
IMAGE_INSTALL:remove = "kernel-module-xen-gntdev"
IMAGE_INSTALL:remove = "docker"
IMAGE_INSTALL:remove = "packagegroup-core-ssh-openssh"

IMAGE_INSTALL += "zlib-dev xen-tools-xenstat xen-tools-xenmon"

# IMAGE_INSTALL:remove = " \
#     packagegroup-mxc-utils \
#     packagegroup-mxc-python \
# "
