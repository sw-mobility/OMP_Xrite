DESCRIPTION = "VRTE SDK Installation"
LICENSE = "CLOSED"

SRC_URI = "file://vrte.zip \
           file://exmd.sh"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/opt/vrte
    cp -r ${WORKDIR}/vrte ${D}/opt/
    install -m 0755 ${WORKDIR}/exmd.sh ${D}/opt/vrte/usr/bin/
}

BUILD_RESULT = "${STAGING_DIR_HOST}/usr/lib/ad_result/build"

do_install:append() {
    cp ${BUILD_RESULT}/bin/Publisher ${D}/opt/vrte/usr/bin/
    cp ${BUILD_RESULT}/bin/Subscriber ${D}/opt/vrte/usr/bin/
    cp ${BUILD_RESULT}/bin/dltd__Machine_A_flatcfg.bin ${D}/opt/vrte/dev-aap-dlt/etc/ecu-cfg/
    cp ${BUILD_RESULT}/bin/log_dltd__SoftwareCluster_0_flatcfg.bin ${D}/opt/vrte/dev-aap-dlt/etc/ecu-cfg/
    cp ${BUILD_RESULT}/bin/exm__SoftwareCluster_0_flatcfg.bin ${D}/opt/vrte/exm-aap-execution-manager/etc/ecu-cfg/
    cp ${BUILD_RESULT}/bin/log_exm_a__SoftwareCluster_0_flatcfg.bin ${D}/opt/vrte/exm-aap-execution-manager/etc/ecu-cfg/
    cp -r ${BUILD_RESULT}/etc/ecu-cfg ${D}/opt/vrte/usr/etc/
}


FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

FILES:${PN} += "/opt/vrte/"

INSANE_SKIP:${PN} += "already-stripped"
INSANE_SKIP:${PN} += "staticdev"

RDEPENDS:${PN} += "ad-build"
RDEPENDS:${PN} += "libacl libsystemd libpcap"

DEPENDS += "ad-build"
