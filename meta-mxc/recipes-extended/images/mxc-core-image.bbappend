# Enable Xen and add Xen Packages
require ${@bb.utils.contains('DISTRO_FEATURES', 'xen', 'mxc-image-xen.inc', '', d)}

IMAGE_INSTALL:append = "${@bb.utils.contains('DISTRO_FEATURES', 'xen', ' xen-examples-default ', '', d)}"
IMAGE_INSTALL:append = "${@bb.utils.contains('DISTRO_FEATURES', 'xen', ' systemd-can2vm ', '', d)}"
IMAGE_BOOT_FILES:append = "${@bb.utils.contains('DISTRO_FEATURES', 'xen', ' boot.fit ', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = "  \
    file://10-wired.network \
    file://iptables.rules \
    file://sysctl.conf  \
	file://10-bind.network  \
    file://xenbr0.netdev    \
	file://xenbr0.network	\
"

do_deploy_netconfigs_dom0() {

    install -m 0644 ${THISDIR}/${PN}/10-wired.network ${IMAGE_ROOTFS}${sysconfdir}/systemd/network/

    if ${@bb.utils.contains('DISTRO_FEATURES','xen','true','false',d)}; then
        install -d ${IMAGE_ROOTFS}${sysconfdir}/systemd/network
        install -d ${IMAGE_ROOTFS}${sysconfdir}/iptables

        install -m 0644 ${THISDIR}/${PN}/iptables.rules ${IMAGE_ROOTFS}${sysconfdir}/iptables/

        install -m 0644 ${THISDIR}/${PN}/sysctl.conf ${IMAGE_ROOTFS}${sysconfdir}/
        install -m 0644 ${THISDIR}/${PN}/10-bind.network ${IMAGE_ROOTFS}${sysconfdir}/systemd/network/
        
        install -m 0644 ${THISDIR}/${PN}/xenbr0.netdev ${IMAGE_ROOTFS}${sysconfdir}/systemd/network/
        install -m 0644 ${THISDIR}/${PN}/xenbr0.network ${IMAGE_ROOTFS}${sysconfdir}/systemd/network/
    fi
}

addtask deploy_netconfigs_dom0 after do_rootfs before do_image

