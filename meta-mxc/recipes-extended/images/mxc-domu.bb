require recipes-core/images/mxc-core-packagegroups.bb
require recipes-core/images/mxc-core-image.inc

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS:prepend := "${THISDIR}/mxc-domu:"

SRC_URI:append = "file://20-wired.network"


do_deploy_netconfigs_domU() {
	install -d ${IMAGE_ROOTFS}${sysconfdir}/systemd/network/
	install -m 0644 ${THISDIR}/${PN}/20-wired.network ${IMAGE_ROOTFS}${sysconfdir}/systemd/network/
}

IMAGE_INSTALL:remove = " \
		xen-tools-xenstat \
		xen-tools-xenmon \
		${XEN_KERNEL_MODULES} \
		xen-tools \
		qemu \
"

IMAGE_INSTALL:append = " openssh-sftp openssh-sftp-server "

IMAGE_INSTALL:append = " \
	  mxc-core-packagegroups \
	  aws-cli \
	  aws-sdk-cpp \
	  python3-joblib \
"

# Increase the freespace
IMAGE_ROOTFS_EXTRA_SPACE ?= "54000"

# Update hostname to mxcdomu.
update_hostname() {
    DOMU_HOSTNAME="mxcdomu"
  	echo "${DOMU_HOSTNAME}" > ${IMAGE_ROOTFS}${sysconfdir}/hostname
}
ROOTFS_POSTPROCESS_COMMAND += "update_hostname;"
addtask deploy_netconfigs_domU after do_rootfs before do_image

# The domU partition will be increased after the first image boot.
# Allow yocto to add some extra space required for an initial boot based on
# the default overhead factor (IMAGE_OVERHEAD_FACTOR). Avoid adding extra space
# here (IMAGE_ROOTFS_EXTRA_SPACE), unless necessary.
IMAGE_ROOTFS_EXTRA_SPACE = "0"