SUMMARY = "CAN to VMs"
DESCRIPTION = "systemd config for sending CAN Messages from MCU to VMs."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://can2vm.service;beginline=1;endline=12;md5=e52329f80928416dff0d4a410f36a058"

SRC_URI = "file://can2vm.service \
		   file://can2eth_packet_forward.py \
		   "

S = "${WORKDIR}"

inherit systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "can2vm.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} += "${exec_prefix}/local/can2eth_packet_forward.py"

do_install:append () {
    # install systemd unit files
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${exec_prefix}/local
    install -m 0644 ${WORKDIR}/can2vm.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/can2eth_packet_forward.py ${D}${exec_prefix}/local
}
