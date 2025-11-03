FILESEXTRAPATHS:prepend := ":${THISDIR}/apcu_patch:"

SRC_URI += " \
    file://uboot-k3-j721e-common-proc-board-u-boot.dtsi.patch \
    file://uboot-k3-j721e-common-proc-board.dts.patch \
    file://uboot-k3-j721e-ddr-evm-lp4-4266.dtsi.patch \
"

IS_XEN  = "${@bb.utils.contains('DISTRO_FEATURES','xen','1','0',d)}"

do_configure:prepend() {
    bbnote "IS_XEN=${IS_XEN}"
}

DEFAULT_UBOOT_CFG = "file://uboot-j721e_evm_a72_defconfig-default.patch"
XEN_ONLY_UBOOT_CFG = "file://uboot-j721e_evm_a72_defconfig-xen_only.patch"

UBOOT_CFG_SELECTED = "${@( \
    d.getVar('XEN_ONLY_UBOOT_CFG')  if (d.getVar('IS_XEN')=='1') else \
    d.getVar('DEFAULT_UBOOT_CFG') \
)}"

SRC_URI:append = " ${UBOOT_CFG_SELECTED}"

PR:append = "_tisdk_0"

do_deploy:append:k3r5 () {
    for f in ${B}/tiboot3-*.bin; do
        if [[ "$f" == *"_sr1_1-hs-evm"* ]]; then
            install -m 644 $f "${DEPLOYDIR}/tiboot3.bin"
        fi
    done

	for f in ${B}/sysfw*.itb; do
        if [[ "$f" == *"_sr1_1-hs-evm"* ]]; then
            install -m 644 $f "${DEPLOYDIR}/sysfw.itb"
        fi
	done
}
