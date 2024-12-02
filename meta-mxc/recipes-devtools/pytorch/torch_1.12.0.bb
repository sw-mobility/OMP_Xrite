SUMMARY = "PyTorch"
DESCRIPTION = "Tensors and Dynamic neural networks in Python with strong GPU acceleration"

HOMEPAGE = "https://github.com/pytorch/pytorch"

PYV = "cp310"

SRC_URI = "https://download.pytorch.org/whl/cpu/${BPN}-${PV}-${PYV}-${PYV}-manylinux2014_aarch64.whl;downloadfilename=${BPN}-${PV}-${PYV}-${PYV}.zip;subdir=${BP}"

# SRC_URI[md5sum] = "a7d12bf59b89e554b66956c08330b0e1"
# SRC_URI[sha256sum] = "0399746f83b4541bcb5b219a18dbe8cade760aba1c660d2748a38c6dc338ebc7"
SRC_URI[md5sum] = "9cd892dc1e5499958a491486e7d9bded"
SRC_URI[sha256sum] = "2568f011dddeb5990d8698cc375d237f14568ffa8489854e3b94113b4b6b7c8b"

inherit python3-dir

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file:///${S}/${BPN}-${PV}.dist-info/LICENSE;md5=d994fcc3deab5862274021d556c2a9b8"

# do_unpack[depends] += "unzip-native:do_populate_sysroot"

DEPENDS += "python3 torch-libgomp"
RDEPENDS_${PN} += "python3-typing-extensions"

RDEPENDS_${PN} += "libgomp-d22c30c5.so.1(GOMP_4.0)(64bit) \
		libgomp-d22c30c5.so.1(OMP_4.5)(64bit) \
		libgomp-d22c30c5.so.1(OMP_1.0)(64bit) \
		"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info"
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}"
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/${PN}gen"
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/caffe2"

do_install() {
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}gen
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/caffe2

	install -m 644 ${S}/${PN}-${PV}.dist-info/* ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}-${PV}.dist-info/
	cp --no-preserve=ownership -r ${S}/${PN}/* ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}
	cp --no-preserve=ownership -r ${S}/${PN}gen/* ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}gen
	cp --no-preserve=ownership -r ${S}/caffe2/* ${D}${PYTHON_SITEPACKAGES_DIR}/caffe2

	rm ${D}${PYTHON_SITEPACKAGES_DIR}/${PN}/lib/libgomp*
}

INSANE_SKIP:${PN} = "already-stripped"
