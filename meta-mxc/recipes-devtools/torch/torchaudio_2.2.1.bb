SUMMARY = "Torch Audio"
DESCRIPTION = "Data manipulation and transformation for audio signal processing, powered by PyTorch"

HOMEPAGE = "https://github.com/pytorch/audio"

PYV = "cp312"

# SRC_URI = "https://download.pytorch.org/whl/cpu/${BPN}-${PV}-${PYV}-${PYV}-manylinux2014_aarch64.whl;downloadfilename=${BPN}-${PV}-${PYV}-${PYV}.zip;subdir=${BP}"
SRC_URI = "https://download.pytorch.org/whl/cpu/${BPN}-${PV}-${PYV}-${PYV}-linux_aarch64.whl;downloadfilename=${BPN}-${PV}-${PYV}-${PYV}.zip;subdir=${BP}"

SRC_URI[md5sum] = "e57e363d1d4b08a7c7f775fb77d6fd78"
SRC_URI[sha256sum] = "bd389f33b7dbfc44e5f4070fc6db00cc560992bea8378a952889acfd772b7022"

inherit python3-dir

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file:///${S}/${BPN}-${PV}.dist-info/LICENSE;md5=570eef9ba0d4b42e6e7be2b2ca31f13c"

DEPENDS += "python3"
RDEPENDS:${PN} += "torch"

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info"
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}"

do_install() {
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}

	install -m 644 ${S}/${PN}-${PV}.dist-info/* ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info/
	cp --no-preserve=ownership -r ${S}/${PN}/* ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}
}

INSANE_SKIP:${PN} += "file-rdeps"