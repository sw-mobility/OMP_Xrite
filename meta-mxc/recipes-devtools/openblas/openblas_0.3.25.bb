DESCRIPTION = "OpenBLAS is an optimized BLAS library based on GotoBLAS2 1.13 BSD version."
SUMMARY = "OpenBLAS : An optimized BLAS library"
AUTHOR = "Alexander Leiva <norxander@gmail.com>"
HOMEPAGE = "http://www.openblas.net/"
SECTION = "libs"
LICENSE = "BSD-3-Clause"

DEPENDS = "libgfortran"

LIC_FILES_CHKSUM = "file://LICENSE;md5=5adf4792c949a00013ce25d476a2abc0"

SRC_URI = "git://github.com/OpenMathLib/OpenBLAS.git;protocol=https;branch=develop;rev=5e1a429eab44731b6668b8f6043c1ea951b0a80b"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5adf4792c949a00013ce25d476a2abc0"

EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'
EXTRA_OEMAKE:append:aarch64 = " CROSS=1 TARGET=ARMV8"

S = "${WORKDIR}/git"

FILES:${PN} += "${libdir}/*"
FILES:${PN}-dev = "${includedir} ${libdir}/lib${PN}.so"
FILES:${PN}-dev += "${libdir}/cmake/openblas"

do_install() {
        oe_runmake DESTDIR=${D} PREFIX=${prefix} install
        rmdir ${D}${bindir}
}
