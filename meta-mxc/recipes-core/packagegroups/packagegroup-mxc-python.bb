SUMMARY = "Python dependencies for MXC Applications"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PYTHON_PACKAGES = " \
    python3-boto3 \
    python3-can \
    python3-cantools \
    python3-numpy \
    python3-pandas \
    python3-cython \
    python3-gast \
    python3-pythran \
    python3-joblib \
    python3-modules \
    python3-scikit-learn \
    python3-scikit-build \
    python3-scipy \
    python3-pillow \
    python3-sympy \
    torch \
    torch-libgomp \
    torchaudio \
    torchvision \
    torchvision-sublib \
"

RDEPENDS:${PN} += "\
    ${PYTHON_PACKAGES} \
"

ERROR_QA:remove = "version-going-backwards"