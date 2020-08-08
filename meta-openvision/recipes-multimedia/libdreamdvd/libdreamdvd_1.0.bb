SUMMARY = "libdvdnav wrapper for enigma2 based stbs"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS = "libdvdnav"

inherit autotools pkgconfig git-project

SRC_URI = "git://github.com/mirakels/libdreamdvd.git"

SRC_URI_append_sh4 = " file://libdreamdvd-1.0-support_sh4.patch;patch=1"

CFLAGS_append_sh4 = " -std=gnu11"
