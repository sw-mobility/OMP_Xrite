SUMMARY = "gRPC Python tools"
HOMEPAGE = "https://grpc.io/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=731e401b36f8077ae0c134b59be5c906"

PYPI_PACKAGE = "grpcio_tools"
PV = "1.68.0"
SRC_URI[sha256sum] = "737804ec2225dd4cc27e633b4ca0e963b0795161bf678285fab6586e917fd867"

inherit pypi setuptools3

DEPENDS += "python3-grpcio python3-pip-native python3-wheel-native"

do_compile:prepend() {
    OLD_CXX="${CXX}"

    set -- ${OLD_CXX}
    export CXX="$1"
    shift
    export CXXFLAGS="$* --sysroot=${STAGING_DIR_TARGET}"
    export LDFLAGS+="--sysroot=${STAGING_DIR_TARGET}"
}

python do_fix_license() {
    import os, shutil

    grpcio_license = os.path.join(d.getVar('WORKDIR'), "..", "..", "python3-grpcio", d.getVar('PV'), "grpcio-%s" % d.getVar('PV'), "LICENSE")
    dst_license = os.path.join(d.getVar('WORKDIR'), "%s-%s" % (d.getVar('PYPI_PACKAGE'),d.getVar('PV')), "LICENSE")

    if os.path.exists(grpcio_license):
        shutil.copy(grpcio_license, dst_license)
        bb.note("Copied grpcio LICENSE to %s" % dst_license)
    else:
        bb.error("grpcio LICENSE not found in %s" % grpcio_license)
}

do_fix_license[nostamp] = "1"

addtask do_fix_license after do_patch before do_configure

RDEPENDS:${PN} += "python3-setuptools python3-protobuf"
