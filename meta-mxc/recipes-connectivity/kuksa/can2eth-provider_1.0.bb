SUMMARY = "Can2Eth Provider"
DESCRIPTION = "Kuksa Can2Eth Provider"
LICENSE = "CLOSED"

SRC_URI = "file://can2eth-provider.py \
        file://can2eth-provider.service \
        file://config/vss_signal_mapping.csv \
        file://config/MXC_CAN.dbc \
"
S = "${WORKDIR}"

inherit systemd

RDEPENDS:${PN} += " python3-kuksa-client python3-cantools"
# 설치 경로를 /usr/local 쪽으로
do_install() {
# 실행 파일 위치
install -d ${D}/usr/local/bin
install -m 0755 ${WORKDIR}/can2eth-provider.py ${D}/usr/local/bin/can2eth-provider.py

install -d ${D}${sysconfdir}/kuksa
install -m 0755 ${WORKDIR}/config/vss_signal_mapping.csv ${D}${sysconfdir}/kuksa/vss_signal_mapping.csv
install -m 0755 ${WORKDIR}/config/MXC_CAN.dbc ${D}${sysconfdir}/kuksa/MXC_CAN.dbc

# systemd 서비스
install -d ${D}${systemd_system_unitdir}
install -m 0644 ${WORKDIR}/can2eth-provider.service ${D}${systemd_system_unitdir}/can2eth-provider.service
}

FILES:${PN} += "/usr/local/bin/* ${systemd_system_unitdir}/* ${sysconfdir}/kuksa/*"

SYSTEMD_SERVICE:${PN} = "can2eth-provider.service"
