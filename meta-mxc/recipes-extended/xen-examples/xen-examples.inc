# Copyright 2024 HL Mando
#
# Recipe to configure a complete Xen boot flow on various use-cases
inherit deploy

SUMMARY = "Recipe to configure a complete Xen boot flow on various use-cases"
SECTION = "utils"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS += " imagebuilder-native"
RDEPENDS:${PN} += " xen kernel-devicetree"
FILESEXTRAPATHS:prepend := "${THISDIR}/xen-examples:"

S = "${WORKDIR}"
B = "${WORKDIR}/build"

FILES:${PN} = "/boot/boot.scr \
		/boot/boot.source \
		/boot/boot.fit \
		/boot/boot.its \
		/boot/${CFG_NAME_DOMU} \
		"

EXTRA_CMDS_FILE = "extra_cmds_mxc"
SRC_URI = "file://${EXTRA_CMDS_FILE}"

do_configure() {
    DTB_NAME="$(basename ${KERNEL_DEVICETREE})"
    CFG_FILE="${B}/${CFG_NAME}"

    cp -vf "${S}/${CFG_NAME}" "${CFG_FILE}"
    cp -vf "${S}/${EXTRA_CMDS_FILE}" "${B}/${EXTRA_CMDS_FILE}"

    # Append machine-specific linux .dtb file name
    echo "DEVICE_TREE=\"${DTB_NAME}\"" >> "${CFG_FILE}"

    # Append ramdisk image name for each DomU
    NUM_DOMUS=$(grep "^NUM_DOMUS=[0-9]" ${CFG_FILE} | cut -d '=' -f2 | head -n1)
    if [ -z "${NUM_DOMUS}" ]; then
        NUM_DOMUS=0
    fi

    i=0
    while test ${i} -lt ${NUM_DOMUS}; do
        echo "DOMU_RAMDISK[${i}]=\"${DOM0LESS_ROOTFS}-${MACHINE}.cpio.gz\"" >> ${CFG_FILE}
        i=`expr ${i} + 1`;
    done

    # Append file name with extra commands to be added in boot script
    if [ -n "${EXTRA_CMDS_FILE}" ]; then
        echo "APPEND_EXTRA_CMDS=\"${B}/${EXTRA_CMDS_FILE}\"" >> "${CFG_FILE}"
    fi

	# Append Fit Image configuration
	echo "FIT=\"boot.fit\"" >> "${CFG_FILE}"
	echo "FIT_ENC_KEY_DIR=\"${UBOOT_SIGN_KEYDIR}/custMpk\"" >> "${CFG_FILE}"
	echo "FIT_ENC_UB_DTB=\"${DEPLOY_DIR_IMAGE}/u-boot.dtb\"" >> "${CFG_FILE}"
}

do_compile() {
    LOAD_CMD='load mmc ${mmcdev}:${mmcpart_ext}'
    UBOOT_DIR="${DEPLOY_DIR_IMAGE}"
    IMAGEBUILDER_CONFIG="${CFG_NAME}"
    PREPEND_PATH="/boot"

    # Run Imagebuilder script
    uboot-script-gen -c ${IMAGEBUILDER_CONFIG} \
        -d "${UBOOT_DIR}" \
        -t "${LOAD_CMD}" \
        -p "${PREPEND_PATH}" \
        -s

    if [ -f "${UBOOT_DIR}/boot.scr" ]; then
    	mv ${UBOOT_DIR}/boot.scr ${UBOOT_DIR}/boot.source ${B}/
	fi

    if [ -f "${UBOOT_DIR}/boot.fit" ]; then
    	mv ${UBOOT_DIR}/boot.fit ${UBOOT_DIR}/boot.its ${B}/
	fi
}

do_install() {
    install -d ${D}/boot

    if [ -f "${B}/boot.scr" ]; then
    	install -m 0755 boot.scr ${D}/boot/
    	install -m 0755 boot.source ${D}/boot/
	fi

    if [ -f "${B}/boot.fit" ]; then
		install -m 0755 boot.fit ${D}/boot/
		install -m 0755 boot.its ${D}/boot/
	fi
    install -m 0644 ${S}/${CFG_NAME_DOMU} ${D}/boot/
}

do_deploy() {
    if [ -f "${B}/boot.scr" ]; then
    	install -m 0755 ${B}/boot.scr ${DEPLOYDIR}/
    	install -m 0755 ${B}/boot.source ${DEPLOYDIR}/
	fi

    if [ -f "${B}/boot.fit" ]; then
    	[ -f "${DEPLOY_DIR_IMAGE}/boot.its" ] && rm ${DEPLOY_DIR_IMAGE}/boot.its
    	[ -f "${DEPLOY_DIR_IMAGE}/boot.fit" ] && rm ${DEPLOY_DIR_IMAGE}/boot.fit
		install -m 0755 ${B}/boot.fit ${DEPLOYDIR}/
		install -m 0755 ${B}/boot.its ${DEPLOYDIR}/
	fi
}

addtask do_deploy after do_compile
do_compile[depends] += " \
    virtual/kernel:do_deploy \
    virtual/bootloader:do_deploy \
    xen:do_deploy \
"
