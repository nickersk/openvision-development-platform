SUMMARY = "PyAMF provides Action Message Format (AMF) support for Python that is compatible with the Adobe Flash Player"
HOMEPAGE = "http://www.pyamf.org/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d8bf5ff31155bfe951a02be0c29215d3"

SRC_URI = "https://files.pythonhosted.org/packages/f4/f5/d2fb7c5ee1d3e296a328a0205bcf0cce78b57f0a1c822f7e5eadacbe83e2/Py3AMF-${PV}.tar.gz"

SRC_URI[sha256sum] = "6dac2d34a09daf5351e654e8cdc3026b3560a6db498c17cdcc84322b3149952c"

S = "${WORKDIR}/Py3AMF-${PV}"

inherit setuptools3 distutils3

include python-package-split.inc
