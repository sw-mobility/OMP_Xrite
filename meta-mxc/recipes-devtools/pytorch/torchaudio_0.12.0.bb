SUMMARY = "Torch Audio"
DESCRIPTION = "Data manipulation and transformation for audio signal processing, powered by PyTorch"

HOMEPAGE = "https://github.com/pytorch/audio"

PYV = "cp38"

SRC_URI = "https://download.pytorch.org/whl/cpu/${BPN}-${PV}-${PYV}-${PYV}-manylinux2014_aarch64.whl;downloadfilename=${BPN}-${PV}-${PYV}-${PYV}.zip;subdir=${BP}"

SRC_URI[md5sum] = "78a7a7dda778ae303f235d57717bc749"
SRC_URI[sha256sum] = "1c5cf143305ebb36c8715cac2418147a37275cdf3b44331ea44ef6722ee2b7e5"

inherit python3-dir

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file:///${S}/${BPN}-${PV}.dist-info/LICENSE;md5=570eef9ba0d4b42e6e7be2b2ca31f13c"

# do_unpack[depends] += "unzip-native:do_populate_sysroot"

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

