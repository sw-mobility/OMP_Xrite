FILESEXTRAPATHS:prepend := "${THISDIR}/apcu_patch:"

KERNEL_CONFIG_FRAGMENTS:append = "\
	${WORKDIR}/mxc_network_interfaces.cfg	\
	${WORKDIR}/etas_aautosar.cfg	\
"

SRC_URI += " \
	file://mxc_network_interfaces.cfg \
	file://etas_aautosar.cfg \
	file://kernel-micron-st.c.0704.patch;striplevel=0 \
	file://kernel-k3-j721e-mcu-wakeup.dtsi.patch;striplevel=0 \
	file://kernel-k3-j721e-main.dtsi.patch;striplevel=0 \
"

PR:append = "_tisdk_1"
