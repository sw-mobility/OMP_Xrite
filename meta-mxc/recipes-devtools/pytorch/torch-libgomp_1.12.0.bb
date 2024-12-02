SUMMARY = "PyTorch-libgomp"
DESCRIPTION = "Tensors and Dynamic neural networks in Python with strong GPU acceleration"

HOMEPAGE = "https://github.com/pytorch/pytorch"

PYV = "cp38"

SRC_URI = "https://download.pytorch.org/whl/cpu/torch-${PV}-${PYV}-${PYV}-manylinux2014_aarch64.whl;downloadfilename=${BPN}-${PV}-${PYV}-${PYV}.zip;subdir=${BP}"

SRC_URI[md5sum] = "a7d12bf59b89e554b66956c08330b0e1"
SRC_URI[sha256sum] = "0399746f83b4541bcb5b219a18dbe8cade760aba1c660d2748a38c6dc338ebc7"

inherit python3-dir

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file:///${S}/torch-${PV}.dist-info/LICENSE;md5=d994fcc3deab5862274021d556c2a9b8"

# do_unpack[depends] += "unzip-native:do_populate_sysroot"

INSANE_SKIP:${PN} = "ldflags "
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/torch/lib/libgomp*"

RPROVIDES:${PN} += "libgomp-d22c30c5.so.1(GOMP_4.0)(64bit) \
		libgomp-d22c30c5.so.1(OMP_4.5)(64bit) \
		libgomp-d22c30c5.so.1(OMP_1.0)(64bit) \
		"

do_install() {
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/torch/lib
	install -m 644 ${S}/torch/lib/libgomp* ${D}${PYTHON_SITEPACKAGES_DIR}/torch/lib
}

# INSANE_SKIP_${PN} += "already-stripped"
INSANE_SKIP:${PN} += "already-stripped"
