SUMMARY = "Utilities for MXC Applications"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

UTIL_PACKAGES = " \
    opencv \
    glibc \
    curl \
    ti-rtos-firmware \
    zlib \
    openssl \
    openssl-bin \
    openssl-conf \
    openssl-engines \
    can-utils \
    flashrom \
    file \
    gcc \
    mtd-utils \
    vlan \
    python3-speedtest-cli \
    netplan \
    net-tools \
    tree \
    linuxptp \
    ${@bb.utils.contains('DISTRO_FEATURES', 'xen', '', 'systemd',d)} \
"

ADDITIONAL_PACKAGES = " \
    libgcc \
    libgcc-dev \
    libstdc++-dev \
    libgomp-dev \
    ${LIBC_DEPENDENCIES} \
    libc-staticdev \
    linux-libc-headers-dev \
    curl-dev \
    i2c-tools-dev \
    lzo-dev \
    opkg-dev \
    readline-dev \
    libusb-compat-dev \
    libusb1-dev \
    zlib-dev \
    ncurses-dev \
    opkg-dev \
    util-linux-dev \
    dosfstools \
"

RDEPENDS:${PN} += "\
    ${UTIL_PACKAGES} \
"

ERROR_QA:remove = "version-going-backwards"