FILESEXTRAPATHS:prepend := "${THISDIR}/apcu_patch:"

KERNEL_CONFIG_FRAGMENTS:append = "\
	${WORKDIR}/etas_aautosar.cfg	\
"

SRC_URI += " \
	file://etas_aautosar.cfg \
"
PR:append = "_tisdk_1"
