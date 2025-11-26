SUMMARY = "A final implementation of JSONPath for Python"
DESCRIPTION = "jsonpath-ng aims to be standard compliant, supports arithmetic, binary operators, and AST for metaprogramming."
HOMEPAGE = "https://github.com/h2non/jsonpath-ng"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PYPI_PACKAGE = "jsonpath-ng"

SRC_URI[sha256sum] = "f6f5f7fd4e5ff79c785f1573b394043b39849fb2bb47bcead935d12b00beab3c"

inherit pypi setuptools3

RDEPENDS:${PN} += "\
    python3-core \
    python3-six \
    python3-ply \
    python3-decorator \
    python3-datetime \
"

BBCLASSEXTEND = "native nativesdk"

