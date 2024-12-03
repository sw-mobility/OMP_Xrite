FILESEXTRAPATHS:prepend := "${THISDIR}/apcu_patch:"

KERNEL_CONFIG_FRAGMENTS:append = "\
	${WORKDIR}/mxc_network_interfaces.cfg	\
	${WORKDIR}/etas_aautosar.cfg	\
"

SRC_URI += " \
	file://mxc_network_interfaces.cfg \
	file://etas_aautosar.cfg \
	file://kernel-micron-st.c.0704.patch;striplevel=0 \
	file://kernel-k3-j721e-common-proc-board.dts.0718.1700.patch;striplevel=0 \
	file://kernel-k3-j721e-common-proc-board.dts.disable_i2c.patch;striplevel=0 \
	file://kernel-k3-j721e-common-proc-board.dts.mcu_gpio.patch;striplevel=0 \
"
PR:append = "_tisdk_1"
