require kuksa-databroker-crates.inc

SUMMARY = "kuksa-Databroker"
DESCRIPTION = "Kuksa Data Broker Yocto Recipe"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRCREV = "30e5c13abc496d0b39aaa6c25acebb088b9902e3"
SRC_URI += "git://github.com/eclipse-kuksa/kuksa-databroker.git;branch=release/0.5.0;protocol=https \
            file://kuksa-databroker.service \
            file://databroker.env \
           "

S = "${WORKDIR}/git"

inherit cargo cargo-update-recipe-crates systemd

DEPENDS += "protobuf-native librsvg-native"

DEBUG_PREFIX_MAP:remove = "-fcanon-prefix-map"

RDEPENDS:${PN} = " systemd ca-certificates"

do_install:append() {
    install -m 0755 ${D}${bindir}/databroker ${D}${bindir}/kuksa-databroker
    install -m 0755 ${D}${bindir}/databroker-cli ${D}${bindir}/kuksa-databroker-cli

    install -d ${D}${sysconfdir}/kuksa
    install -m 0644 ${WORKDIR}/databroker.env ${D}${sysconfdir}/kuksa/databroker.env

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/kuksa-databroker.service ${D}${systemd_system_unitdir}/kuksa-databroker.service
}

SYSTEMD_SERVICE:${PN} = "kuksa-databroker.service"

FILES:${PN} += " \
    ${bindir}/kuksa-databroker \
    ${bindir}/databroker-cli \
    ${systemd_system_unitdir}/kuksa-databroker.service \
    ${sysconfdir}/kuksa/databroker.env \
"

INSANE_SKIP:${PN} += "already-stripped"
