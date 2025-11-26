FILESEXTRAPATHS:prepend := ":${THISDIR}/u-boot-ti-staging:"

SRC_URI += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'xen', 'file://mxc_xen_uboot_add_interrupt_router_range.patch', '', d)} \
"