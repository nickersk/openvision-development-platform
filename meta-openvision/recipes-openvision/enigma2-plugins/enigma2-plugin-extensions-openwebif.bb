DESCRIPTION = "Control your receiver with a browser"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;firstline=10;lastline=12;md5=9c14f792d0aeb54e15490a28c89087f7"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "python3-cheetah-native"

RDEPENDS_${PN} = "\
	aio-grab \
	python3-cheetah \
	python3-compression\
	python3-ipaddress\
	python3-json \
	python3-misc \
	python3-numbers \
	python3-pyopenssl \
	python3-shell \
	python3-six \
	python3-unixadmin \
	"

inherit gitpkgv distutils-openplugins gettext

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"

SRC_URI = "git://github.com/OpenVisionE2/OpenWebif.git;protocol=git"

SRC_URI_append_sh4 = " file://revert_grab_for_sh4.patch"

S = "${WORKDIR}/git"

do_compile() {
	cheetah-compile -R --nobackup ${S}/plugin
}

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/OpenWebif"

do_install_append() {
	install -d ${D}${PLUGINPATH}
	cp -r ${S}/plugin/* ${D}${PLUGINPATH}
	chmod a+rX ${D}${PLUGINPATH}/
	rm -f ${D}${PLUGINPATH}/public/images/boxes/*.png
	rm -f ${D}${PLUGINPATH}/public/images/remotes/*.png
	rm -f ${D}${PLUGINPATH}/public/static/remotes/*.html
	install -m 0644 ${S}/plugin/public/images/boxes/unknown.png ${D}${PLUGINPATH}/public/images/boxes/
	install -m 0644 ${S}/plugin/public/images/remotes/ow_remote.png ${D}${PLUGINPATH}/public/images/remotes/
	if [ "${TARGET_ARCH}" = "sh4" ]; then
		install -m 0644 ${S}/plugin/public/images/remotes/spark.png ${D}${PLUGINPATH}/public/images/remotes/
		install -m 0644 ${S}/plugin/public/static/remotes/spark.html ${D}${PLUGINPATH}/public/static/remotes/
	else
		install -m 0644 ${S}/plugin/public/images/remotes/dmm1.png ${D}${PLUGINPATH}/public/images/remotes/
		install -m 0644 ${S}/plugin/public/static/remotes/dmm1.html ${D}${PLUGINPATH}/public/static/remotes/
	fi
	if [ -e "${S}/plugin/public/images/boxes/${MACHINE}.png" ]; then
		install -m 0644 ${S}/plugin/public/images/boxes/${MACHINE}.png ${D}${PLUGINPATH}/public/images/boxes/
	fi
	if [ -e "${S}/plugin/public/images/remotes/${RCNAME}.png" ]; then
		install -m 0644 ${S}/plugin/public/images/remotes/${RCNAME}.png ${D}${PLUGINPATH}/public/images/remotes/
		install -m 0644 ${S}/plugin/public/static/remotes/${RCNAME}.html ${D}${PLUGINPATH}/public/static/remotes/
	fi
	if [ "${MACHINE}" = "et9x00" ]; then
		install -m 0644 ${S}/plugin/public/images/remotes/et9500.png ${D}${PLUGINPATH}/public/images/remotes/
		install -m 0644 ${S}/plugin/public/static/remotes/et9500.html ${D}${PLUGINPATH}/public/static/remotes/
	elif [ "${MACHINE}" = "et6x00" ]; then
		install -m 0644 ${S}/plugin/public/images/remotes/et6500.png ${D}${PLUGINPATH}/public/images/remotes/
		install -m 0644 ${S}/plugin/public/static/remotes/et6500.html ${D}${PLUGINPATH}/public/static/remotes/
	elif [ "${MACHINE}" = "azboxhd" ]; then
		install -m 0644 ${S}/plugin/public/images/remotes/azboxelite.png ${D}${PLUGINPATH}/public/images/remotes/
		install -m 0644 ${S}/plugin/public/static/remotes/azboxelite.html ${D}${PLUGINPATH}/public/static/remotes/
	elif [ "${MACHINE}" = "ventonhdx" ]; then
		install -m 0644 ${S}/plugin/public/images/remotes/ini1.png ${D}${PLUGINPATH}/public/images/remotes/
		install -m 0644 ${S}/plugin/public/static/remotes/ini1.html ${D}${PLUGINPATH}/public/static/remotes/
		install -m 0644 ${S}/plugin/public/images/remotes/ini2.png ${D}${PLUGINPATH}/public/images/remotes/
		install -m 0644 ${S}/plugin/public/static/remotes/ini2.html ${D}${PLUGINPATH}/public/static/remotes/
	fi
}

FILES_${PN} = "${PLUGINPATH}"

PACKAGES =+ "${PN}-themes ${PN}-vxg"
FILES_${PN}-themes = "${libdir}/enigma2/python/Plugins/Extensions/OpenWebif/public/themes"
FILES_${PN}-vxg = "${libdir}/enigma2/python/Plugins/Extensions/OpenWebif/public/vxg"
RDEPENDS_${PN}-themes = "${PN}"
RDEPENDS_${PN}-vxg = "${PN}"
ALLOW_EMPTY_${PN}-themes = "1"
ALLOW_EMPTY_${PN}-vxg = "1"
