
do_install:append() {
	if ${@bb.utils.contains('DISTRO_FEATURES','xen','true','false',d)}; then
		cat <<-EOF >> "${D}${sysconfdir}/udev/mount.ignorelist"
		/dev/mmcblk0p*
		EOF
	fi
}
