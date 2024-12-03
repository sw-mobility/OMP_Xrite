FILESEXTRAPATHS:prepend := ":${THISDIR}/apcu_patch:"

SRC_URI += " \
    file://uboot-am65-cpsw-nuss.c-0402.patch \
	file://uboot-j721e_evm_a72_defconfig-0718.patch \
	file://uboot-k3-j721e-binman.dtsi-0402.patch \
	file://uboot-k3-j721e-common-proc-board-u-boot.dtsi-0402.patch \
	file://uboot-k3-j721e-common-proc-board.dts-0417.patch \
	file://uboot-k3-j721e-ddr-evm-lp4-4266.dtsi.patch \
    file://uboot-k3-j721e-common-proc-board.dts.disable_i2c.patch \
    file://uboot-k3-j721e-common-proc-board.dts.mcu_gpio.patch \
"
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
