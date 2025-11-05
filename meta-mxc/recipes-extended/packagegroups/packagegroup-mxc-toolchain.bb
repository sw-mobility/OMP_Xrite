DESCRIPTION = "Target packages for the standalone SDK"
PR = "r13"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
    cmake \
    aws-iot-fleetwise-edge \
    aws-cli \
    aws-sdk-cpp \
    aws-iot-device-sdk-python-v2 \
    aws-iot-device-sdk-cpp-v2 \
    aws-crt-cpp \
    aws-crt-python  \
    greengrass-bin \
    corretto-11-bin \
    mtd-utils \
    bash \
    util-linux-fdisk \
    util-linux-mkfs \
	e2fsprogs \
	dosfstools \
	pv \
    docker \
	python3-docker \
	grub \
	btrfs-tools \
    module-init-tools \
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
    devmem2 \
    parted \
    kms++ \
    kms++-python \
    dbus-broker \
    expat \
    libxml2 \
    libpcre \
    optee-os \
    optee-client \
    optee-test \
    optee-examples \
    e2fsprogs \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs \
    e2fsprogs-tune2fs \
    dosfstools \
    util-linux-fdisk \
    util-linux-mkfs \
    util-linux-sfdisk \
    util-linux-fsck \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base-meta \
    gstreamer1.0-plugins-good-meta \
    gstreamer1.0-plugins-bad-meta \
    gstreamer1.0-libav \
    less \
    fbset \
    usbutils \
    i2c-tools \
    strace \
    ltrace \
    ${@bb.utils.contains('MACHINE_FEATURES', 'pci', 'pciutils', '',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'zeroconf', 'avahi-utils', '',d)} \
    ti-tidl \
    ti-tidl-osrt \
    edgeai-dl-inferer-staticdev \
    edgeai-apps-utils-source \
    edgeai-tiovx-modules \
    edgeai-tiovx-kernels \
    xen \
    xen-examples-default \
    xen-tools \
    ament-cmake \
    ament-cmake-ros \
"


# python3-scikit-learn
# python3-scikit-build
# python3-scipy
# torch
# torch-libgomp
# torchaudio
# torchvision