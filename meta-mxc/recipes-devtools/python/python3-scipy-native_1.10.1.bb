SUMMARY = "SciPy (pronounced “Sigh Pie”) is an open-source software for mathematics, science, and engineering."
HOMEPAGE = "https://github.com/scipy/scipy"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=acc6c1a7669d9987de16de7bde2e822a"

SRC_URI[sha256sum] = "2cf9dfb80a7b4589ba4c40ce7588986d6d5cebc5457cad2c2880f6bc2d42f3a5"

inherit pypi native setuptools3

export F90="${FC}"
export F77="${FC}"
S = "${WORKDIR}/scipy-${PV}"

PYTHON_PN="python3"
DEPENDS += " \
	python3-cython \
	python3-cython-native \
	python3-numpy-native \
	python3-pybind11-native \
	python3-pythran-native \
	python3-gast-native \
	python3-beniget-native \
	python3-ply-native \
	openblas \
	openblas-native \
	lapack \
	lapack-native \
"

DEPENDS:append:class-target = " \
	python3-numpy \
"

RDEPENDS:${PN} = " \
    ${PYTHON_PN}-numpy \
    openblas \
"

PACKAGECONFIG ?= "lapack"

PACKAGECONFIG[openblas] = "-Dblas=openblas -Dlapack=openblas,,openblas,openblas"
PACKAGECONFIG[lapack] = "-Dblas=lapack -Dlapack=lapack,,lapack,lapack"
PACKAGECONFIG[f77] = "-Duse-g77-abi=true,,,"

CLEANBROKEN = "1"

export LAPACK = "${STAGING_LIBDIR}"
export BLAS = "${STAGING_LIBDIR}"

