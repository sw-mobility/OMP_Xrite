SUMMARY = "PyTorch"
DESCRIPTION = "Tensors and Dynamic neural networks in Python with strong GPU acceleration"

HOMEPAGE = "https://github.com/pytorch/pytorch"

PYV = "cp312"

SRC_URI = "https://download.pytorch.org/whl/cpu/torch-${PV}-${PYV}-${PYV}-manylinux_2_17_aarch64.manylinux2014_aarch64.whl;downloadfilename=${BPN}-${PV}-${PYV}-${PYV}.zip;subdir=${BP}"
SRC_URI[md5sum] = "404131617e3c75bcf21b20289d166dd4"
SRC_URI[sha256sum] = "3e6fd128c25598c76db32d0bbb850d6f2ee51d1f7f15fc2c91bbd4048155ec01"

inherit python3-dir

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file:///${S}/torch-${PV}.dist-info/LICENSE;md5=7c797015e59eabc42d9d19b8f0131b55"

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/torch/lib/lib*"

RPROVIDES:${PN} += " \
	libomp-4b8320f3.so \
    libomp-4b8320f3.so(GOMP_1.0)(64bit) \
    libomp-4b8320f3.so(GOMP_4.0)(64bit) \
    libomp-4b8320f3.so(OMP_1.0)(64bit) \
"

INSANE_SKIP:${PN} = "ldflags "
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

do_install() {
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/torch/lib
	install -m 644 ${S}/torch.libs/lib* ${D}${PYTHON_SITEPACKAGES_DIR}/torch/lib
}

INSANE_SKIP:${PN} = "already-stripped"
INSANE_SKIP:${PN} += "file-rdeps"
BBCLASSEXTEND = "native"