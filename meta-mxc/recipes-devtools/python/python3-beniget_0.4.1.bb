SUMMARY = "Extract semantic information about static Python code"
HOMEPAGE = "https://github.com/serge-sans-paille/beniget/"
AUTHOR = "serge-sans-paille <serge.guelton@telecom-bretagne.eu>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=02550c296a72ab0b70961eb70a5a7242"

SRC_URI = "https://files.pythonhosted.org/packages/14/e7/50cbac38f77eca8efd39516be6651fdb9f3c4c0fab8cf2cf05f612578737/beniget-0.4.1.tar.gz"
SRC_URI[md5sum] = "a2bbe7f17f10f9c127d8ef00692ddc55"
SRC_URI[sha256sum] = "75554b3b8ad0553ce2f607627dad3d95c60c441189875b98e097528f8e23ac0c"

S = "${WORKDIR}/beniget-0.4.1"

RDEPENDS_${PN} = "python-gast"

do_fetch () {
    wget --no-check-certificate ${SRC_URI}
}
inherit setuptools3
BBCLASSEXTEND = "native nativesdk"
