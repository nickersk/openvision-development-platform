FILES_${PN}-dbg += " \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*.egg-info \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*/*/test \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"


RDEPENDS_${PN}-core += "${PYTHON_PN}-service-identity"
