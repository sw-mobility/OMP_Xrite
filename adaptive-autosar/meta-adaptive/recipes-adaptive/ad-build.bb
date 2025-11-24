DESCRIPTION = "AAdaptive Application Build"
LICENSE = "CLOSED"

SRC_URI = "file://AraCM_IPC.zip"

S = "${WORKDIR}/AraCM_IPC"


inherit cmake

SDK_PATH = "${STAGING_DIR_HOST}/usr/lib/sdk"

EXTRA_OECMAKE += "\
    -DSDK_PATH=${SDK_PATH} \
    -DCMAKE_PREFIX_PATH=${SDK_PATH}/linux-aarch64/opt/vrte/lib/cmake \
    -DCMAKE_INSTALL_PREFIX=${S}/build \
"

OECMAKE_GENERATOR = "Unix Makefiles"


do_configure:prepend() {
    mkdir -p ${S}/build
    cd ${S}/build
}

B = "${S}/build"

do_install() {
    oe_runmake install
}


do_install:append() {
     install -d ${D}/usr/lib/ad_result
     cp -r ${B} ${D}/usr/lib/ad_result
}

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILES:${PN} += "/usr/lib/ad_result/build/"
REDEPENDS:${PN} += "sdk"
DEPENDS = "sdk"
INSANE_SKIP:${PN} += "installed-vs-shipped"
INSANE_SKIP:${PN} += "rpaths"
INSANE_SKIP:${PN} += "ldflags"
