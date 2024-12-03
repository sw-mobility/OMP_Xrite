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

RDEPENDS:${PN} += "\
    ${BOOT_PACKAGES} \
"

# RDEPENDS:${PN}:append:k3 = " ${OPTEE_PKGS}"
ERROR_QA:remove = "version-going-backwards"
