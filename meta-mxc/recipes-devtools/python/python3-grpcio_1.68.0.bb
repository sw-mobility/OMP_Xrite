SUMMARY = "gRPC Python library"
HOMEPAGE = "https://grpc.io/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=731e401b36f8077ae0c134b59be5c906"

PYPI_PACKAGE = "grpcio"
PV = "1.68.0"
SRC_URI[sha256sum] = "7e7483d39b4a4fddb9906671e9ea21aaad4f031cdfc349fec76bdfa1e404543a"

inherit pypi setuptools3

RDEPENDS:${PN} += "python3-setuptools python3-protobuf"
