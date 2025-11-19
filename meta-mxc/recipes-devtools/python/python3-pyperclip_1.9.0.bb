SUMMARY = "Cross-platform clipboard functions"
HOMEPAGE = "https://pypi.org/project/pyperclip/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=dc8ed8ba9f09f565f0fe63910e4ce0fc"

PYPI_PACKAGE = "pyperclip"
PV = "1.9.0"
SRC_URI[sha256sum] = "b7de0142ddc81bfc5c7507eea19da920b92252b548b96186caf94a5e2527d310"

inherit pypi setuptools3

python do_fix_license() {
    import os, shutil
    s_dir = d.getVar('S')
    src_license = os.path.join(s_dir, "LICENSE.txt")
    dst_license = os.path.join(s_dir, "LICENSE")
    if os.path.exists(src_license):
        shutil.copy(src_license, dst_license)
        bb.note("LICENSE.txt copied to LICENSE")
    else:
        bb.warn("LICENSE.txt not found in %s" % s_dir)
}

do_fix_license[nostamp] = "1"

addtask do_fix_license before do_populate_lic

RDEPENDS:${PN} += "python3-setuptools"
