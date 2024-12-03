SUMMARY = "Xen Utilities for Xen hypervisor"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

XEN_PACKAGES = " \
    xen \
    xen-examples-default \
    xen-tools \
"

RDEPENDS:${PN} += "\
    ${XEN_PACKAGES} \
"

ERROR_QA:remove = "version-going-backwards"
