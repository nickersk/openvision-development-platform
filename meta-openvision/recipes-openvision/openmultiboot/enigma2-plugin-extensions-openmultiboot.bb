SUMMARY = "Multi boot loader for enigma2"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit gitpkgv python3native gettext autotools-brokensep rm_python_pyc compile_python_pyo no_python_src

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/OpenVisionE2/openmultibootmanager.git;protocol=git"

S = "${WORKDIR}/git"

DEPENDS = "python3 lzo"

BLOCK2MTD_CHECK = "${@bb.utils.contains("MACHINE_FEATURES", "dreambox", "", "kernel-module-block2mtd", d)}"

RDEPENDS_${PN} = "\
    ${@bb.utils.contains("IMAGE_FSTYPES", "jffs2", "${BLOCK2MTD_CHECK} kernel-module-nandsim" , "", d)} \
    ${@bb.utils.contains("IMAGE_FSTYPES", "ubi", "kernel-module-nandsim" , "", d)} \
    lzo \
    openmultiboot \
    ${@bb.utils.contains("MACHINE_FEATURES", "sh4stb", "unjffs2", "", d)} \
    "

EXTRA_OECONF = "\
    --with-po \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-arch=${TARGET_ARCH} \
    "

# skip this!
install_egg_info() {
}

do_configure_prepend() {
    touch ${S}/NEWS
    touch ${S}/README
    touch ${S}/AUTHORS
    touch ${S}/ChangeLog
}

pkg_preinst_${PN}() {
#!/bin/sh
if mountpoint -q ${libdir}/enigma2/python/Plugins/Extensions/OpenMultiboot; then
    echo "OpenMultiboot will only install on main image."
    echo "Child image is running - canceling installation!"
    sleep 3
    exit 1
else
    echo "Main image is running - proceeding installation..."
    exit 0
fi
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}
