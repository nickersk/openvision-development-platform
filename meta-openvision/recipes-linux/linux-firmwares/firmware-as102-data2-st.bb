require linux-firmware.inc

DESCRIPTION = "Firmware for as102_data2_st"

do_install() {
	install -d ${D}${nonarch_base_libdir}/firmware
	install -m 0644 as102_data2_st.hex ${D}${nonarch_base_libdir}/firmware
}
