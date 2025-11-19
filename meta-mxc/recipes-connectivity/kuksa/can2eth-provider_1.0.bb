SUMMARY = "Can2Eth Provider"
DESCRIPTION = "Kuksa Can2Eth Provider"
LICENSE = "CLOSED"

SRC_URI = "file://can2eth-provider.py \
        file://can2eth-provider.service \
"
S = "${WORKDIR}"

inherit systemd

RDEPENDS:${PN} += " python3-kuksa-client python3-cantools"
# 설치 경로를 /usr/local 쪽으로
do_install() {
# 실행 파일 위치
install -d ${D}/usr/local/bin
install -m 0755 ${WORKDIR}/can2eth-provider.py ${D}/usr/local/bin/can2eth-provider.py

# systemd 서비스
install -d ${D}${systemd_system_unitdir}
install -m 0644 ${WORKDIR}/can2eth-provider.service ${D}${systemd_system_unitdir}/can2eth-provider.service
}

FILES:${PN} += "/usr/local/bin/* ${systemd_system_unitdir}/*"

SYSTEMD_SERVICE:${PN} = "can2eth-provider.service"
