SUMMARY = "Python syntax highlighting package"
HOMEPAGE = "https://pygments.org/"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=36a13c90514e2899f1eba7f41c3ee592"

PYPI_PACKAGE = "pygments"
PV = "2.18.0"
SRC_URI[sha256sum] = "786ff802f32e91311bff3889f6e9a86e81505fe99f2735bb6d60ae0c5004f199"

inherit pypi python_hatchling

DEPENDS += "python3-pip-native python3-wheel-native python3-hatchling-native python3-hatch-fancy-pypi-readme python3-hatch-vcs"

RDEPENDS:${PN} += "python3-setuptools"

PIP_INSTALL_OPTIONS = "--no-deps --use-pep517 --root=${D} --prefix=${prefix}"

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/* ${bindir}/* ${datadir}/* ${libdir}/python*/site-packages/*"
