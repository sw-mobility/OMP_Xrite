# Copyright 2024 HL Mando

FILESEXTRAPATHS:prepend := "${THISDIR}/xen:"

XEN_REL = "4.17"
XEN_BRANCH = "stable-${XEN_REL}"

PV = "${XEN_REL}+stable${SRCPV}"

SRCREV = "5d9a931fe2c1310dbfd946bbc1e22a177add4f5c"
SRC_URI = " \
    git://xenbits.xen.org/xen.git;branch=${XEN_BRANCH} \
    file://0001-python-pygrub-pass-DISTUTILS-xen-4.15.patch \
    "

FILES:${PN}-scripts-common += " ${sysconfdir}/xen/*.cfg"

FILES:${PN}-xl += " \
    ${libdir}/xen/bin/init-dom0less \
"

FILES:${PN}-test += " \
    ${libdir}/xen/bin/test-paging-mempool \
"

FILES:${PN}-xencommons += " \
    ${localstatedir} \
"

SYSTEMD_SERVICE:${PN}-xencommons:remove = " \
    var-lib-xenstored.mount \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=d1a1e216f80b6d8da95fec897d0dbec9"
