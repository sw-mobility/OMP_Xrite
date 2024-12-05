FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti-staging:"

# Enable Xen booting
require ${@bb.utils.contains('DISTRO_FEATURES', 'xen', 'recipes-kernel/linux/linux-yocto_virtualization.inc', '', d)}
KERNEL_CONFIG_FRAGMENTS:append = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'xen', '${WORKDIR}/xen.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'xen', '${WORKDIR}/xen_mxc.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'xen', '${WORKDIR}/xennat.cfg', '', d)} \
    "
SRC_URI += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'xen', 'file://xen.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'xen', 'file://xen_mxc.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'xen', 'file://xennat.cfg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'xen', 'file://mxc_xen_add_interrupt_router_ranges.patch', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'xen', 'file://mxc_xen_update_pcie_interrupt_cells.patch', '', d)} \
    "


