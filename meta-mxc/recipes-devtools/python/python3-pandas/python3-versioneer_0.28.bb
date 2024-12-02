SUMMARY = "Easy VCS-based management of project version strings"
HOMEPAGE = "https://github.com/python-versioneer/python-versioneer"
AUTHOR = "Brian Warner <>"
LICENSE = "Unlicense"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7246f848faa4e9c9fc0ea91122d6e680"

SRC_URI = "https://files.pythonhosted.org/packages/15/86/bed1c929495d8ca30512c8fcc6e9c2555ecffcdd32f0c04f11e492eba9e0/versioneer-0.28.tar.gz"
SRC_URI[md5sum] = "1301a2586acc921d151dc15490bfb476"
SRC_URI[sha256sum] = "7175ca8e7bb4dd0e3c9779dd2745e5b4a6036304af3f5e50bd896f10196586d6"

S = "${WORKDIR}/versioneer-0.29"

DEPENDS += " \
    python3-toml \
    python3-toml-native \
    python3-tomli \
    python3-tomli-native \
"

do_fetch () {
    wget --no-check-certificate ${SRC_URI}
}

inherit pypi setuptools3
BBCLASSEXTEND = "native"
ERROR_QA:remove = "version-going-backwards"