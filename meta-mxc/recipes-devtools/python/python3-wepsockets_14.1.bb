SUMMARY = "WebSocket library for Python"
HOMEPAGE = "https://pypi.org/project/websockets/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e"

PYPI_PACKAGE = "websockets"
PV = "14.1"
SRC_URI[sha256sum] = "398b10c77d471c0aab20a845e7a60076b6390bfdaac7a6d2edb0d2c59d75e8d8"

inherit pypi setuptools3

RDEPENDS:${PN} += "python3-setuptools"
