SUMMARY = "A set of python modules for machine learning and data mining"
HOMEPAGE = "http://scikit-learn.org"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=e087d8348a7a6d2b63e1f305d7acf1a9"

FILESEXTRAPATHS:prepand := "${THISDIR}/python3-scikit-learn:"

SRC_URI[sha256sum] = "a2f54c76accc15a34bfb9066e6c7a56c1e7235dda5762b990792330b52ccfb05"

SRC_URI:append = " \
	file://0001-paralyze-compile-test-program.patch \
"

inherit pypi setuptools3

DEPENDS += " \
	libgfortran \
	python3-joblib \
	python3-cython-native \
	python3-numpy-native \
	python3-pythran-native \
	python3-scipy-native \
	python3-numpy \
	python3-scipy \
	python3-cython \
"

DEPENDS:remove:class-native = "libgfortran"
RDEPENDS:${PN} += "python3-numpy python3-scipy python3-joblib python3-threadpoolctl"

BBCLASSEXTEND = "native nativesdk"