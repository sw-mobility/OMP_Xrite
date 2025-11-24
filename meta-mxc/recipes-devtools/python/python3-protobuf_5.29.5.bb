SUMMARY = "Protocol Buffers Python implementation"
HOMEPAGE = "https://github.com/protocolbuffers/protobuf"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=37b5762e07f0af8c74ce80a8bda4266b"

PYPI_PACKAGE = "protobuf"
PV = "5.29.5"
SRC_URI[sha256sum] = "bc1463bafd4b0929216c35f437a8e28731a2b7fe3d98bb77a600efced5a15c84"

inherit pypi setuptools3

DEPENDS += "python3-pip-native python3-wheel-native"

RDEPENDS:${PN} += "python3-setuptools"

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/* ${bindir}/* ${datadir}/* ${libdir}/python*/site-packages/*"

