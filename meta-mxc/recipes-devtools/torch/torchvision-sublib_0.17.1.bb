SUMMARY = "Torch Vision"
DESCRIPTION = "Sub library for torchvision compilation."

HOMEPAGE = "https://github.com/pytorch/vision"

PYV = "cp312"

SRC_URI = "https://download.pytorch.org/whl/cpu/torchvision-${PV}-${PYV}-${PYV}-linux_aarch64.whl;downloadfilename=${BPN}-${PV}-${PYV}-${PYV}.zip;subdir=${BP}"
SRC_URI[md5sum] = "2bfb0a71319123e07856fefd191fd025"
SRC_URI[sha256sum] = "54902877410ffb5458ee52b6d0de4b25cf01496bee736d6825301a5f0398536e"

inherit python3-dir

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file:///${S}/torchvision-${PV}.dist-info/LICENSE;md5=bd7749a3307486a4d4bfefbc81c8b796"

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/torchvision.lib/lib*"
FILES:${PN} += "${libdir}"
DEPENDS += "python3-sympy jpeg libpng ffmpeg torch"

INSANE_SKIP:${PN} += "ldflags "
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

RPROVIDES:${PN} += " \
    libjpeg.1634491c.so.8 \
    libpng16.af82d4d3.so.16 \
    libswscale.081a0d67.so.5 \
    libswresample.80c50f33.so.3 \
    libavutil.9088af75.so.56 \
    libavcodec.2800e627.so.58 \
    libavformat.4cb76267.so.58 \
    ld-linux-aarch64.514b5772.so.1 \
"


do_install() {
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/torchvision.lib
	install -m 644 ${S}/torchvision.libs/lib* ${D}${PYTHON_SITEPACKAGES_DIR}/torchvision.lib
    install -m 644 ${S}/torchvision.libs/lib* ${D}${libdir}
    install -m 644 ${S}/torchvision.libs/ld* ${D}${libdir}
    # install -m 644 ${RECIPE_SYSROOT}${libdir}/ld-linux-aarch64.514b5772.so.1 ${D}${libdir}
}

INSANE_SKIP:${PN} += "already-stripped "
INSANE_SKIP:${PN} += "file-rdeps "
INSANE_SKIP:${PN} += "ldflags "