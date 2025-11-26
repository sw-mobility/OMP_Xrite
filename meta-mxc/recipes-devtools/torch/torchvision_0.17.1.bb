SUMMARY = "Torch Vision"
DESCRIPTION = "The torchvision package consists of popular datasets, model architectures,\
			   and common image transformations for computer vision."

HOMEPAGE = "https://github.com/pytorch/vision"

PYV = "cp312"

############# ORIGINAL #############
SRC_URI = "https://download.pytorch.org/whl/cpu/${BPN}-${PV}-${PYV}-${PYV}-linux_aarch64.whl;downloadfilename=${BPN}-${PV}-${PYV}-${PYV}.zip;subdir=${BP}"

SRC_URI[md5sum] = "2bfb0a71319123e07856fefd191fd025"
SRC_URI[sha256sum] = "54902877410ffb5458ee52b6d0de4b25cf01496bee736d6825301a5f0398536e"
####################################

export TORCHVISION_INCLUDE="${STAGING_INCDIR}"
export TORCHVISION_USE_PNG = "1"
export TORCHVISION_USE_JPEG = "1"
export TORCHVISION_USE_FFMPEG = "1"
export FFMPEG_ROOT = "${STAGING_LIBDIR}/.."
export FFMPEG_VERSION = "6.0"

inherit python3-dir

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file:///${S}/${BPN}-${PV}.dist-info/LICENSE;md5=bd7749a3307486a4d4bfefbc81c8b796"

DEPENDS += "torch python3-scipy torchvision-sublib python3-sympy jpeg libpng ffmpeg python3-pillow"

RDEPENDS_${PN} += " \
    libjpeg.1634491c.so.8 \
    libpng16.af82d4d3.so.16 \
    libswscale.081a0d67.so.5 \
    libswresample.80c50f33.so.3 \
    libavutil.9088af75.so.56 \
    libavcodec.2800e627.so.58 \
    libavformat.4cb76267.so.58 \
    ld-linux-aarch64.514b5772.so.1 \
"
RDEPENDS_${PN} += "python3-typing-extensions python3-numpy python3-requests"
RDEPENDS_${PN} += "torch python3-pillow python3-scipy python3-sympy"

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info"
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}"

do_install() {
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}

	install -m 644 ${S}/${PN}-${PV}.dist-info/* ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info/
	cp --no-preserve=ownership -r ${S}/${PN}/* ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}
}

INSANE_SKIP:${PN} += "already-stripped "
INSANE_SKIP:${PN} += "file-rdeps"
