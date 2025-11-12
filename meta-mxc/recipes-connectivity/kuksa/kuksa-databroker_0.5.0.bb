require kuksa-databroker-crates.inc

SUMMARY = "kuksa-Databroker"
DESCRIPTION = "Kuksa Data Broker Yocto Recipe"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRCREV = "30e5c13abc496d0b39aaa6c25acebb088b9902e3"
SRC_URI += "git://github.com/eclipse-kuksa/kuksa-databroker.git;branch=release/0.5.0;protocol=https \
           "

S = "${WORKDIR}/git"

inherit cargo cargo-update-recipe-crates

DEPENDS += "protobuf-native librsvg-native"

DEBUG_PREFIX_MAP:remove = "-fcanon-prefix-map"

RDEPENDS:${PN} = " ca-certificates"

do_install:append() {
    install -m 0755 ${D}${bindir}/databroker ${D}${bindir}/kuksa-databroker
    install -m 0755 ${D}${bindir}/databroker-cli ${D}${bindir}/kuksa-databroker-cli
}

FILES:${PN} += " \
    ${bindir}/kuksa-databroker \
    ${bindir}/databroker-cli \
"

INSANE_SKIP:${PN} += "already-stripped"
