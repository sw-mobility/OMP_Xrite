SUMMARY = "EdgeAI utils for MXC Applications"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

MXC_EDGEAI_PACKAGES = " \
    ti-tidl \
    ti-tidl-osrt \
    edgeai-dl-inferer-staticdev \
    edgeai-apps-utils-source \
    edgeai-tiovx-modules \
    edgeai-tiovx-kernels \
"

RDEPENDS:${PN} += "\
    ${MXC_EDGEAI_PACKAGES} \
"

ERROR_QA:remove = "version-going-backwards"