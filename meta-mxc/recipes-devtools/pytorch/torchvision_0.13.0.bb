SUMMARY = "Torch Vision"
DESCRIPTION = "The torchvision package consists of popular datasets, model architectures,\
			   and common image transformations for computer vision."

HOMEPAGE = "https://github.com/pytorch/vision"

PYV = "cp38"

SRC_URI = "https://download.pytorch.org/whl/cpu/${BPN}-${PV}-${PYV}-${PYV}-manylinux2014_aarch64.whl;downloadfilename=${BPN}-${PV}-${PYV}-${PYV}.zip;subdir=${BP}"

SRC_URI[md5sum] = "312a0648c826d5fe74456753a4907f56"
SRC_URI[sha256sum] = "667cac55afb13cda7d362466e7eba3119e529b210e55507d231bead09aca5e1f"

inherit python3-dir

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file:///${S}/${BPN}-${PV}.dist-info/LICENSE;md5=bd7749a3307486a4d4bfefbc81c8b796"

# do_unpack[depends] += "unzip-native:do_populate_sysroot"

DEPENDS += "python3 python3-scipy"
RDEPENDS:${PN} += "python3-typing-extensions python3-numpy python3-requests"
RDEPENDS:${PN} += "torch python3-pillow python3-scipy"

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info"
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}"

do_install() {
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}

	install -m 644 ${S}/${PN}-${PV}.dist-info/* ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info/
	cp --no-preserve=ownership -r ${S}/${PN}/* ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}
}

