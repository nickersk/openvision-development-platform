SUMMARY = "GUI frontend for Open Source Linux based receivers"
DESCRIPTION = "Enigma2 is a framebuffer based frontend for DVB functions on Linux settop boxes"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "\
	avahi \
	freetype \
	gettext-native \
	jpeg \
	libdreamdvd libdvbsi++ fribidi libmad libpng libsigc++-2.0 giflib libxml2 \
	${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "libvugles2-${MACHINE} libgles-${MACHINE}", "", d)} \
	openssl libudfread \
	python3-pillow python3-twisted python-wifi \
	swig-native \
	tuxtxt-enigma2 \
	"

DEPENDS_append_sh4 += "\
	libmme-host \
	libmme-image \
	"

DEPENDS_append_rpi += "\
	e2-rpihddevice \
	ffmpeg \
	libdvbcsa \
	libnl \
	userland \
	"

RDEPENDS_${PN} = "\
	alsa-conf \
	enigma2-fonts \
	enigma2-plugin-extensions-pespeedup \
	ethtool \
	glibc-gconv-iso8859-15 \
	${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash middleflash", "", "glibc-gconv-cp1250", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "libvugles2-${MACHINE} libgles-${MACHINE}", "", d)} \
	ntpdate \
	openvision-branding \
	${PYTHON_RDEPS} \
	${@bb.utils.contains("MACHINE_FEATURES", "emmc", "bzip2 rsync", "", d)} \
	"

RDEPENDS_${PN}_append_sh4 += "\
	alsa-utils-amixer-conf \
	libmme-host \
	"

RDEPENDS_${PN}_append_rpi += "\
	e2-rpihddevice \
	libdvbcsa \
	"

RRECOMMENDS_${PN} = "\
	hotplug-e2-helper \
	glibc-gconv-utf-16 \
	${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash middleflash", "", "ofgwrite", d)} \
	python3-sendfile \
	virtual/enigma2-mediaservice \
	"

PYTHON_RDEPS = "\
	python3-codecs \
	python3-core \
	python3-crypt \
	python3-fcntl \
	${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash middleflash", "", "python3-image python3-pillow", d)} \
	python3-mmap \
	python3-netclient \
	python3-netifaces \
	python3-netserver \
	python3-numbers \
	python3-pickle \
	python-process \
	python3-pyusb \
	python3-service-identity \
	python3-shell \
	python3-six \
	python3-threading \
	python3-twisted-core \
	python3-twisted-web \
	python3-xml \
	python3-zopeinterface \
	"

# DVD and iso playback is integrated, we need the libraries
RDEPENDS_${PN} += "libdreamdvd libudfread"
RRECOMMENDS_${PN} += "libdvdcss"

# We depend on the font which we use for TXT subtitles (defined in skin_subtitles.xml)
RDEPENDS_${PN} += "font-valis-enigma"

RDEPENDS_${PN} += "${@bb.utils.contains("MACHINE_FEATURES", "blindscan-dvbc", "virtual/blindscan-dvbc" , "", d)}"

DEMUXTOOL ?= "replex"

DESCRIPTION_append_enigma2-plugin-extensions-cutlisteditor = "enables you to cut your movies."
RDEPENDS_enigma2-plugin-extensions-cutlisteditor = "aio-grab"
DESCRIPTION_append_enigma2-plugin-extensions-graphmultiepg = "shows a graphical timeline EPG."
DESCRIPTION_append_enigma2-plugin-extensions-pictureplayer = "displays photos on the TV."
DESCRIPTION_append_enigma2-plugin-systemplugins-positionersetup = "helps you installing a motorized dish."
DESCRIPTION_append_enigma2-plugin-systemplugins-satelliteequipmentcontrol = "allows you to fine-tune DiSEqC-settings."
DESCRIPTION_append_enigma2-plugin-systemplugins-satfinder = "helps you to align your dish."
DESCRIPTION_append_enigma2-plugin-systemplugins-videomode = "selects advanced video modes"
RDEPENDS_enigma2-plugin-systemplugins-softwaremanager = "python3-twisted-web"
DESCRIPTION_append_enigma2-plugin-systemplugins-wirelesslan = "helps you configuring your wireless lan"
RDEPENDS_enigma2-plugin-systemplugins-wirelesslan = "wpa-supplicant wireless-tools python-wifi"
DESCRIPTION_append_enigma2-plugin-systemplugins-networkwizard = "provides easy step by step network configuration"
# Note that these tools lack recipes
RDEPENDS_enigma2-plugin-extensions-dvdburn = "dvd+rw-tools dvdauthor mjpegtools cdrkit python3-pillow ${DEMUXTOOL} kernel-module-pktcdvd"
RDEPENDS_enigma2-plugin-extensions-dvdplayer = "kernel-module-udf"
RDEPENDS_enigma2-plugin-systemplugins-hotplug = "hotplug-e2-helper"

DESCRIPTION_enigma2-plugin-font-wqy-microhei = "wqy-microhei font supports Chinese EPG"
PACKAGES =+ "enigma2-plugin-font-wqy-microhei"
FILES_enigma2-plugin-font-wqy-microhei = "${datadir}/fonts/wqy-microhei.ttc ${datadir}/fonts/fallback.font"
ALLOW_EMPTY_enigma2-plugin-font-wqy-microhei = "1"

# Fake package that doesn't actually get built, but allows OE to detect
# the RDEPENDS for the plugins above, preventing [build-deps] warnings.
RDEPENDS_${PN}-build-dependencies = "\
	aio-grab \
	dvd+rw-tools dvdauthor mjpegtools cdrkit python3-pillow ${DEMUXTOOL} \
	python3-twisted-web \
	wpa-supplicant wireless-tools python-wifi \
	"

inherit gitpkgv python3native upx_compress autotools pkgconfig rm_python_pyc compile_python_pyo

ENIGMA2_BRANCH = "develop"
PV = "develop+git${SRCPV}"
PKGV = "develop+git${GITPKGV}"

SRC_URI = "git://github.com/OpenVisionE2/enigma2-openvision.git;branch=${ENIGMA2_BRANCH}"
SRC_URI_append += "${@bb.utils.contains("MACHINE_FEATURES", "uianimation", " file://use-lv3ddriver-for-uianimation.patch" , "", d)}"

LDFLAGS_prepend = " -lxml2 "

S = "${WORKDIR}/git"

FILES_${PN} += "${datadir}/keymaps ${bindir} /usr/lib"
FILES_${PN}-meta = "${datadir}/meta"
PACKAGES += "${PN}-meta ${PN}-build-dependencies"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES =+ "enigma2-fonts"
PKGV_enigma2-fonts = "2018.08.15"
FILES_enigma2-fonts = "${datadir}/fonts"

def get_crashaddr(d):
    if d.getVar('CRASHADDR', True):
        return '--with-crashlogemail="${CRASHADDR}"'
    else:
        return ''

EXTRA_OECONF = "\
	--with-libsdl=no --with-boxtype=${MACHINE} \
	--enable-dependency-tracking \
	ac_cv_prog_c_openmp=-fopenmp \
	${@get_crashaddr(d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "textlcd", "--with-textlcd" , "", d)} \
	${@bb.utils.contains_any("MACHINE_FEATURES", "7segment 7seg", "--with-7segment" , "", d)} \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	--with-boxbrand=${BOX_BRAND} \
	--with-oever=${VISIONVERSION} \
	${@bb.utils.contains("MACHINE_FEATURES", "bwlcd128", "--with-bwlcd128" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "bwlcd140", "--with-bwlcd140" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "bwlcd255", "--with-bwlcd255" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "colorlcd220", "--with-colorlcd220" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "colorlcd390", "--with-colorlcd390" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "colorlcd400", "--with-colorlcd400" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "colorlcd480", "--with-colorlcd480" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "colorlcd720", "--with-colorlcd720" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "colorlcd800", "--with-colorlcd800" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "nolcd", "--with-nolcd" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "hiaccel", "--with-libhiaccel" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "triaccel", "--with-libtriaccel" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "--with-libvugles2" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "--with-osdanimation" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "olde2api", "--with-olde2api" , "", d)} \
	"

EXTRA_OECONF_sh4 = "\
	--enable-${MACHINE} --with-lcd \
	--with-libsdl=no --with-boxtype=${MACHINE} \
	--with-boxbrand=${BOX_BRAND} \
	--with-oever=${VISIONVERSION} \
	--enable-dependency-tracking \
	ac_cv_prog_c_openmp=-fopenmp \
	${@get_crashaddr(d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "textlcd", "--with-textlcd" , "", d)} \
	${@bb.utils.contains_any("MACHINE_FEATURES", "7segment 7seg", "--with-7segment" , "", d)} \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

# pass the enigma branch to automake
EXTRA_OEMAKE = "\
	ENIGMA2_BRANCH=${ENIGMA2_BRANCH} \
	"

# some plugins contain so's, their stripped symbols should not end up in the enigma2 package
FILES_${PN}-dbg += "\
	${libdir}/enigma2/python/*.debug \
	${libdir}/enigma2/python/*/*.debug \
	${libdir}/enigma2/python/*/*/*.debug \
	${libdir}/enigma2/python/*/*/*/*.debug \
	${libdir}/enigma2/python/*/*/*/*/*.debug \
	${libdir}/enigma2/python/*/*/*/*/*/*.debug \
	${libdir}/enigma2/python/*/*/*/*/*/*/*.debug \
	${libdir}/enigma2/python/*/*/*/*/*/*/*/*.debug \
	${libdir}/enigma2/python/*/*/*/*/*/*/*/*/*.debug \
	${libdir}/enigma2/python/*/*/*/*/*/*/*/*/*/*.debug \
	"

do_install_append() {
	install -d ${D}${datadir}/keymaps
	if [ "${@bb.utils.contains("MACHINE_FEATURES", "multilib", "1", "0", d)}" = "1" ]; then
		install -d ${D}/usr/lib
		ln -s ${libdir}/enigma2 ${D}/usr/lib/enigma2
		ln -s ${libdir}/${PYTHONPATHVERSION} ${D}/usr/lib/${PYTHONPATHVERSION}
	fi
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends='')
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True, extra_depends='')
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True, extra_depends='')
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True, extra_depends='')
    enigma2_podir = bb.data.expand('${datadir}/enigma2/po', d)
    do_split_packages(d, enigma2_podir, '^(\w+)/[a-zA-Z0-9_/]+.*$', 'enigma2-locale-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
}

CXXFLAGS_append_sh4 += " -std=c++11 -fPIC -fno-strict-aliasing "
