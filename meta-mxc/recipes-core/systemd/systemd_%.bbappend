do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','xen','true','false',d)}; then
        rm -rf ${D}${sysconfdir}/systemd/network/10-eth.network
        rm -rf ${D}${sysconfdir}/systemd/network/15-eth.network
        rm -rf ${D}${sysconfdir}/systemd/network/30-wlan.network
        rm -rf ${D}${sysconfdir}/systemd/network/60-usb.network
    fi
}
