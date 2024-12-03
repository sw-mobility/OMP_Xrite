SUMMARY = "Utilities for MXC Applications"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

UTIL_PACKAGES = " \
    opencv \
    glibc \
    curl \
    ti-rtos-firmware \
    lapack \
    openblas \
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
    speedtest-cli \
    netplan \
    net-tools \
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

# RDEPENDS:${PN}:append:k3 = " ${OPTEE_PKGS}"
ERROR_QA:remove = "version-going-backwards"