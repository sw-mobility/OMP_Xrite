SUMMARY = "Minimal bootable & flashable image for MXC"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

BOOT_PACKAGES = " \
    mtd-utils \
    bash \
    util-linux-fdisk \
    util-linux-mkfs \
	e2fsprogs \
	dosfstools \
	pv \
"

FOTA_PACKAGES = " \
    btrfs-tools \
    zstd \
    parted \
    u-boot-tools \
    libubootenv \
"
RDEPENDS:${PN} += "\
    ${BOOT_PACKAGES} \
    ${FOTA_PACKAGES} \
"

ERROR_QA:remove = "version-going-backwards"
