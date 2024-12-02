SUMMARY = "SciPy (pronounced “Sigh Pie”) is an open-source software for mathematics, science, and engineering."
HOMEPAGE = "https://github.com/scipy/scipy"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=acc6c1a7669d9987de16de7bde2e822a"

DEPENDS += " \
	${PYTHON_PN}-numpy \
	${PYTHON_PN}-numpy-native \
	${PYTHON_PN}-pybind11-native \
	${PYTHON_PN}-pythran-native \
        ${PYTHON_PN}-beniget \
        ${PYTHON_PN}-beniget-native \
	openblas \
        lapack \
        python3-setuptools \
"

SRC_URI[sha256sum] = "2cf9dfb80a7b4589ba4c40ce7588986d6d5cebc5457cad2c2880f6bc2d42f3a5"

inherit pypi setuptools3

RDEPENDS:${PN} = " \
        ${PYTHON_PN}-numpy \
	openblas \
        lapack \
"

CLEANBROKEN = "1"

export LAPACK = "${STAGING_LIBDIR}"
export BLAS = "${STAGING_LIBDIR}"
export OPENBLAS = "${STAGING_LIBDIR}"

export F90 = "${TARGET_PREFIX}gfortran"
export F77 = "${TARGET_PREFIX}gfortran"
export FARCH = "${TUNE_CCARGS}"

export NUMPY_INCLUDE_PATH = "${STAGING_DIR_TARGET}/usr/lib/python${PYTHON_BASEVERSION}/site-packages/numpy/core/include"

# Numpy expects the LDSHARED env variable to point to a single
# executable, but OE sets it to include some flags as well. So we split
# the existing LDSHARED variable into the base executable and flags, and
# prepend the flags into LDFLAGS
LDFLAGS:prepend := "${@" ".join(d.getVar('LDSHARED', True).split()[1:])} "
export LDSHARED := "${@d.getVar('LDSHARED', True).split()[0]}"

# Tell Numpy to look in target sysroot site-packages directory for libraries
LDFLAGS:append = " -L${STAGING_LIBDIR}/${PYTHON_DIR}/site-packages/numpy/core/lib"

do_fetch () {
    wget --no-check-certificate ${SRC_URI}
}

do_patch:append() {
    bb.build.exec_func('do_patch_library', d)
}

python do_patch_library() {
    with open(d.getVar("S")+"/scipy/stats/setup.py", "r+") as f:
        lines = f.readlines()
        for index in range(len(lines)):
            if "biasedurn_libs = ['npyrandom', 'npymath']" in lines[index]:
                lines.pop(index)
                line = lines.pop(index)
                spaces = len(line) - len(line.lstrip())
                line = lines.pop(index)
                secspaces = len(line) - len(line.lstrip())
                lines.pop(index)

                lines.insert(index, (' ' * secspaces)
                        + "'..', '..', 'core', 'lib','libnpymath.a')])"
                        + os.linesep)
                lines.insert(index, (' ' * spaces)
                        + "biasedurn_libs.append([join(os.environ[\"NUMPY_INCLUDE_PATH\"],"
                        + os.linesep)
                lines.insert(index, (' ' * secspaces)
                        + "'..', '..', 'random', 'lib','libnpyrandom.a')]"
                        + os.linesep)
                lines.insert(index, (' ' * spaces)
                        + "biasedurn_libs = [join(os.environ[\"NUMPY_INCLUDE_PATH\"],"
                        + os.linesep)
            if "library_dirs=biasedurn_libdirs" in lines[index]:
                line = lines.pop(index)
                spaces = len(line) - len(line.lstrip())
                lines.pop(index)

                lines.insert(index, (' ' * spaces)
                        + "extra_objects=biasedurn_libs,"
                        + os.linesep)
                break

    with open(d.getVar("S")+"/scipy/stats/setup.py", "w") as f:
        f.writelines(lines)

    with open(d.getVar("S")+"/scipy/stats/_rcont/setup.py", "r+") as f:
        lines = f.readlines()
        lines.insert(0, "import os" + os.linesep)
        for index in range(len(lines)):
            if "library_dirs=[join(np.get_include()," in lines[index]:
                line = lines.pop(index)
                spaces = len(line) - len(line.lstrip())
                line = lines.pop(index)
                secspaces = len(line) - len(line.lstrip())
                lines.pop(index)
                lines.pop(index)
                lines.pop(index)


                lines.insert(index, (' ' * secspaces)
                        + "'..', '..', 'core', 'lib', 'libnpymath.a')]"
                        + os.linesep)
                lines.insert(index, (' ' * secspaces)
                        + "join(os.environ[\"NUMPY_INCLUDE_PATH\"],"
                        + os.linesep)
                lines.insert(index, (' ' * secspaces)
                        + "'..', '..', 'random', 'lib', 'libnpyrandom.a'),"
                        + os.linesep)
                lines.insert(index, (' ' * spaces)
                        + "extra_objects=[join(os.environ[\"NUMPY_INCLUDE_PATH\"],"
                        + os.linesep)
                break

    with open(d.getVar("S")+"/scipy/stats/_rcont/setup.py", "w") as f:
        f.writelines(lines)
}

do_install:append() {
     find ${D} -name "*.so" -exec chrpath -d {} \;
}

INSANE_SKIP:${PN} = "already-stripped"
