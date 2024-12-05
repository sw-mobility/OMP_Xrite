require recipes-extended/xen-examples/xen-examples.inc

CFG_NAME = "config_mxc_default"
CFG_NAME_DOMU = "mando-domu.cfg"

DESTDIR = "${D}${sysconfdir}/xen/auto"
FILES:${PN} += "${sysconfdir}/xen/auto/${CFG_NAME_DOMU}"

do_install:append() {
	install -d ${DESTDIR}
	install -m 644 ${S}/${CFG_NAME_DOMU} ${DESTDIR}
}


SRC_URI += "file://${CFG_NAME} \
			file://${CFG_NAME_DOMU} \
			"

#INSANE_SKIP:${PN} += " version-going-backwards "
