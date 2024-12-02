SUMMARY = "Ahead of Time compiler for numeric kernels"
HOMEPAGE = "https://pythran.readthedocs.io/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e277a0b6033e0cb3d510c86b74144b01"

SRC_URI[md5sum] = "3090288af50566af75cb058d1878aaad"
SRC_URI[sha256sum] = "8aad08162f010e5425a7b254dd68d83311b430bb29f9252dce2eff3ba39497dd"

inherit pypi setuptools3

DEPENDS = "python3-setuptools python3-gast python3-beniget python3-ply python3-setuptools-native"
RDEPENDS_${PN} = "${PYTHON_PN}-gast"

do_fetch () {
    wget --no-check-certificate ${SRC_URI}
}

BBCLASSEXTEND = "native"
