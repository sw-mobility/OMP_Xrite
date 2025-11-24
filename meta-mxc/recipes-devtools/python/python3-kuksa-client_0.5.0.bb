SUMMARY = "KUKSA client Python module"
HOMEPAGE = "https://pypi.org/project/kuksa-client/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

PV = "0.5.0"
SRC_URI[sha256sum] = "102af8a5e377cfe8fd3dfbc4d32872919a5441bec4dc52379496687b201eaf51"
PYPI_PACKAGE = "kuksa_client"

inherit pypi setuptools3

DEPENDS += "python3-setuptools-native"

# C 확장 모듈 + protobuf → 항상 RDEPENDS
RDEPENDS:${PN} += "python3-grpcio python3-grpcio-tools python3-protobuf "

# meta-python 존재 순수 Python 패키지
RDEPENDS:${PN} += " python3-attrs python3-cmd2 python3-colorama python3-jsonpath-ng \
                   python3-ply python3-pygments python3-pyperclip python3-wcwidth python3-websockets "

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/kuksa_client \
                 ${PYTHON_SITEPACKAGES_DIR}/kuksa_client-*.egg-info"

do_install:append() {
    rm -rf ${D}${PYTHON_SITEPACKAGES_DIR}/kuksa_client/tests
    rm -rf ${D}${PYTHON_SITEPACKAGES_DIR}/kuksa_client/sdv
    rm -rf ${D}${PYTHON_SITEPACKAGES_DIR}/kuksa_client/__pycache__
}
