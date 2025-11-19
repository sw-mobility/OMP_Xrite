SUMMARY = "Python package for attributes"
HOMEPAGE = "https://www.attrs.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5e55731824cf9205cfabeab9a0600887"

PV = "24.2.0"
PYPI_PACKAGE = "attrs"
SRC_URI[sha256sum] = "5cfb1b9148b5b086569baec03f20d7b6bf3bcacc9a42bebf87ffaaca362f6346"

inherit pypi python_hatchling

DEPENDS += "python3-pip-native python3-wheel-native python3-hatchling-native python3-hatch-fancy-pypi-readme-native python3-hatch-vcs-native"

RDEPENDS:${PN} += "python3-setuptools"

PIP_INSTALL_OPTIONS = "--no-deps --use-pep517 --root=${D} --prefix=${prefix}"

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/* ${bindir}/* ${datadir}/* ${libdir}/python*/site-packages/*"

