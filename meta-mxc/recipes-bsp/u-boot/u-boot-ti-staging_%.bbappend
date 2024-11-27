FILESEXTRAPATHS:prepend := ":${THISDIR}/apcu_patch:"

SRC_URI += " \
	file://uboot-k3-j721e-ddr-evm-lp4-4266.dtsi.patch \
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
