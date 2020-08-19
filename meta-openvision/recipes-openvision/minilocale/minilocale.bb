SUMMARY = "Size reduced but fully functional locale support"
LICENSE = "GPLv2"
SECTION = "base"
PRIORITY = "required"

require conf/license/license-gplv2.inc

SRC_URI = "\
	https://raw.githubusercontent.com/OpenVisionE2/hypercube-files/master/locales.tar.xz;name=locales \
	https://raw.githubusercontent.com/OpenVisionE2/hypercube-files/master/c-utf-8.tar.xz;name=c-utf-8 \
	file://locale.alias \
	file://locale.sh \
	"

SRC_URI[locales.md5sum] = "992e814d6ea94e2a52a6f77d0321675e"
SRC_URI[locales.sha256sum] = "f3f6bdb4f6113aa5b7266357c24310c36bb9750d139016390bcace8452d08ae1"

SRC_URI[c-utf-8.md5sum] = "cd524216a6f6e609ce410b160510300d"
SRC_URI[c-utf-8.sha256sum] = "c9f5abcf2b44c599bbf474c81e88f36b9cd595d0ce4e641e7ae11b08c1baf2df"

S = "${WORKDIR}${libdir}/locale"

LOCALEDIR = "${libdir}/locale"
LOCALEDIR2 = "${datadir}/locale"

LANGUAGES = "ar_AE bg_BG ca_AD cs_CZ da_DK de_DE el_GR en_US es_ES et_EE fa_IR fi_FI fr_FR \
             fy_NL he_IL hr_HR hu_HU id_ID is_IS it_IT ku_TR lt_LT lv_LV nb_NO nl_NL nn_NO pl_PL pt_BR pt_PT \
             ro_RO ru_RU sk_SK sl_SI sr_RS sv_SE th_TH tr_TR uk_UA vi_VN zh_CN zh_HK"


RPROVIDES_${PN}  = "${@" ".join(map(lambda s: "virtual-locale-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RPROVIDES_${PN} += "${@" ".join("virtual-locale-%s" % p.split('_')[0] for p in d.getVar('LANGUAGES').split())}"
RPROVIDES_${PN} += "${@" ".join(map(lambda s: "locale-base-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RCONFLICTS_${PN} = "${@" ".join(map(lambda s: "locale-base-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"
RREPLACES_${PN}  = "${@" ".join(map(lambda s: "locale-base-%s" % s, d.getVar('LANGUAGES').split())).lower().replace('_','-')}"

do_install() {
	install -d ${D}${sysconfdir}/profile.d
	install -m 0644 ${WORKDIR}/locale.sh ${D}${sysconfdir}/profile.d/locale.sh
	install -d ${D}${LOCALEDIR2}
	install ${WORKDIR}/locale.alias ${D}${LOCALEDIR2}

	install -d ${D}${LOCALEDIR}
	cp -rp ${S}/* ${D}/${LOCALEDIR}

	for langpath in $(find ${D}${LOCALEDIR}/* -maxdepth 1 -type d); do
		lang=$(basename $langpath)
		if [ "x$lang" != "xC.UTF-8" -a "x$lang" != "xlocale" -a "x$lang" != "xLC_MESSAGES" ]; then
			# For smallflash/middleflash use the LC_COLLATE and LC_CTYPE from C.UTF-8 for all languages
		        if echo "${MACHINE_FEATURES}" | grep -q '\(smallflash\|middleflash\)'
		        then
		                [ -e ${D}${LOCALEDIR}/$lang/LC_CTYPE ] && rm -f ${D}${LOCALEDIR}/$lang/LC_CTYPE
			        [ -e ${D}${LOCALEDIR}/$lang/LC_COLLATE ] && rm -f ${D}${LOCALEDIR}/$lang/LC_COLLATE
		        fi
	                [ ! -e ${D}${LOCALEDIR}/$lang/LC_CTYPE ] && ln -sf ../C.UTF-8/LC_CTYPE ${D}${LOCALEDIR}/$lang/LC_CTYPE
		        [ ! -e ${D}${LOCALEDIR}/$lang/LC_COLLATE ] && ln -sf ../C.UTF-8/LC_COLLATE ${D}${LOCALEDIR}/$lang/LC_COLLATE
		fi
	done

	# For smallflash delete all LC_COLLATE and use POSIX LC_COLLATE instead
	if echo "${MACHINE_FEATURES}" | grep -q smallflash
	then
		find ${D}${LOCALEDIR}/ -name 'LC_COLLATE' \( -type f -o -type l \) -exec rm -f {} +
	fi

	ln -s sr_RS ${D}${LOCALEDIR}/sr_YU
}

FILES_${PN} = "${LOCALEDIR} ${LOCALEDIR2} ${sysconfdir}/profile.d"

do_package_qa[noexec] = "1"
