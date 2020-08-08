# Remove unneeded util-linux-mount from RDEPENDS and RRECOMMENDS
RRECOMMENDS_ntfs-3g = ""

inherit upx_compress

RDEPENDS_ntfs-3g += "kernel-module-ntfs kernel-module-fuse"
