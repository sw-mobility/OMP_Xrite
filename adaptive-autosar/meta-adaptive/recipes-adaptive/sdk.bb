DESCRIPTION = "Install CMAKE Package into sysroot"
LICENSE = "CLOSED"

SRC_URI = "file://sdk_linux_aarch64.zip"

do_install() {
    install -d ${D}/usr/lib
    cp -r ${WORKDIR}/sdk ${D}/usr/lib/
}

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILES:${PN} += "/usr/lib/sdk/"

INSANE_SKIP:${PN} += "already-stripped"
INSANE_SKIP:${PN} += "file-rdeps"
INSANE_SKIP:${PN} += "staticdev"

REDEPENDS:${PN} += "glibc"
