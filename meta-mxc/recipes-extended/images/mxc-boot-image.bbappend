HOST_ROOTFS_SOURCE ?= "mxc-core-image"
DOMU_ROOTFS_SOURCE ?= "mxc-domu"

do_add_core_image() {
	PARTNUM=1
	HOST_IMAGE="${DEPLOY_DIR_IMAGE}/${HOST_ROOTFS_SOURCE}-${MACHINE}.tar.xz"
	[ -f "${HOST_IMAGE}" ] && install -m 0644 ${HOST_IMAGE} ${WORKDIR}/rootfs/home/root
	if ${@bb.utils.contains('DISTRO_FEATURES','xen','true','false',d)}; then
		DOMU_IMAGE="${DEPLOY_DIR_IMAGE}/${DOMU_ROOTFS_SOURCE}-${MACHINE}.tar.xz"
		[ -f "${DOMU_IMAGE}" ] && install -m 0644 ${DOMU_IMAGE} ${WORKDIR}/rootfs/home/root
		PARTNUM=2
	fi

	cat <<-EOF > "${WORKDIR}/rootfs/home/root/init_flash.sh"
	# Set up OSPI Nor
	flash_erase /dev/mtd0 0 0
	flash_erase /dev/mtd1 0 0
	flash_erase /dev/mtd2 0 0
	flash_erase /dev/mtd5 0 0
	dd if=/boot/tiboot3.bin of=/dev/mtdblock0
	dd if=/boot/tispl.bin of=/dev/mtdblock1
	dd if=/boot/u-boot.img of=/dev/mtdblock2
	dd if=/boot/sysfw.itb of=/dev/mtdblock5

	dd if=/dev/zero of=/dev/mmcblk0 bs=1024 count=1024
	sync

	SIZE=\`fdisk -l /dev/mmcblk0 |grep Disk | awk '{print \$5}'\`
	PARTSIZE=\`expr \$SIZE / $PARTNUM\`
	# Create partitions for root filesystem
	# To prevent asking removing signature of partitions, add options -W, -w with always
	if [[ \$SIZE == \$PARTSIZE ]]; then
		cat << END | fdisk /dev/mmcblk0 -W always -w always
		n
		p
		1


		w
		END
		mkfs.ext4 -L "root" /dev/mmcblk0p1
		mount -t ext4 /dev/mmcblk0p1 /mnt
		pv `basename $HOST_IMAGE` | tar xJf - -C /mnt
		umount /mnt
	else
		PARTSIZE=\`expr \$PARTSIZE / 1024 / 1024\`
		cat << END | fdisk /dev/mmcblk0 -W always -w always
		n
		p
		1

		+\${PARTSIZE}M
		n
		p
		2


		w
		END
		mkfs.ext4 -L "root" /dev/mmcblk0p1
		mkfs.ext4 -L "domuroot" /dev/mmcblk0p2
		mount -t ext4 /dev/mmcblk0p1 /mnt
		pv `basename $HOST_IMAGE` | tar xJf - -C /mnt
		umount /mnt
		mount -t ext4 /dev/mmcblk0p2 /mnt
		pv `basename $DOMU_IMAGE` | tar xJf - -C /mnt
		umount /mnt
	fi
	sync
	sync
	EOF
	chmod a+x "${WORKDIR}/rootfs/home/root/init_flash.sh"
}

do_add_core_image[depends] += "${HOST_ROOTFS_SOURCE}:do_image_complete"
do_add_core_image[depends] += "${@bb.utils.contains('DISTRO_FEATURES', 'xen', '${DOMU_ROOTFS_SOURCE}:do_image_complete', '', d)}"

IMAGE_BOOT_FILES:append = "${@bb.utils.contains('DISTRO_FEATURES', 'xen', ' boot.fit ', '', d)}"

addtask add_core_image after do_rootfs before do_image
