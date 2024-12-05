# Copyright 2024 HL Mando

FILESEXTRAPATHS:prepend := "${THISDIR}/xen:"

XEN_REL = "4.17"
XEN_BRANCH = "stable-${XEN_REL}"

PV = "${XEN_REL}+stable${SRCPV}"

SRCREV = "5d9a931fe2c1310dbfd946bbc1e22a177add4f5c"
SRC_URI = " \
    git://xenbits.xen.org/xen.git;branch=${XEN_BRANCH} \
    file://0001-menuconfig-mconf-cfg-Allow-specification-of-ncurses-location.patch \
    file://mxc_kconfig_debug.patch \
    file://mxc_xen_device_tree_for_ti.patch \
    file://xen_mxc.cfg \
    "

LIC_FILES_CHKSUM = "file://COPYING;md5=d1a1e216f80b6d8da95fec897d0dbec9"

do_deploy:append() {
	# Create relative symbolic link for xen
	cd ${DEPLOYDIR} && ln -sf xen-${MACHINE} ${DEPLOYDIR}/xen && cd -
}

