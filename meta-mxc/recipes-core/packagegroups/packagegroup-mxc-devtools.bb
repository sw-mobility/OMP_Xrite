SUMMARY = "Tools that are used for MXC developments"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

MXC_DOCKER = "\
    docker \
	docker-ce \
	python3-docker \
	grub \
	btrfs-tools \
	k3s \
"
# --- MXC_DEV_TOOLS_EXTRA ---
MXC_DEV_TOOLS_BASE = " \
    module-init-tools \
    thermal-init \
    bash \
    vim \
    opkg-bash-completion \
    udev-extraconf \
    libgpiod \
    libgpiod-tools \
    glib-2.0 \
    phytool \
    ca-certificates \
    boost \
    numactl \
    nodejs \
    procps \
"

MXC_DEV_TOOLS_NETWORK = " \
    iputils \
    curl \
    initscript-telnetd \
    systemd-telnetd \
    ethtool \
    tcpdump \
    dropbear \
    openssh-sftp-server \
    iptables \
    iperf3 \
    netperf \
    nfs-utils-client \
    cifs-utils \
    gdbserver \
"

MXC_DEV_TOOLS_EXTRA = " \
    devmem2 \
    parted \
    kms++ \
    kms++-python \
    dbus-broker \
    expat \
    libxml2 \
    libpcre \
"

OPTEE_PKGS = " \
    optee-os \
    optee-client \
    optee-test \
    optee-examples \
"

ARAGO_FSTOOLS = "\
    e2fsprogs \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs \
    e2fsprogs-tune2fs \
    dosfstools \
    util-linux-fdisk \
    util-linux-mkfs \
    util-linux-sfdisk \
    util-linux-fsck \
"

RTSP_TOOLS = "\
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base-meta \
    gstreamer1.0-plugins-good-meta \
    gstreamer1.0-plugins-bad-meta \
    gstreamer1.0-libav \
"

ARAGO_UTILS = "\
    less \
    fbset \
    usbutils \
    i2c-tools \
    strace \
    ltrace \
    ${@bb.utils.contains('MACHINE_FEATURES', 'pci', 'pciutils', '',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'zeroconf', 'avahi-utils', '',d)} \
"


RDEPENDS:${PN} += "\
    ${MXC_DEV_TOOLS_BASE} \
    ${MXC_DEV_TOOLS_NETWORK} \
    ${MXC_DEV_TOOLS_EXTRA} \
    ${OPTEE_PKGS} \
    ${ARAGO_FSTOOLS} \
    ${ARAGO_UTILS} \
    ${RTSP_TOOLS} \
    ${MXC_DOCKER} \
"

