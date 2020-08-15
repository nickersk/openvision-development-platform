FILES_${PN}-dbg += " \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*.egg-info \
    ${libdir}/${PYTHON_DIR}/site-packages/twisted/*/*/test \
"

RDEPENDS_${PN}-core += "${PYTHON_PN}-service-identity"

FILES_${PN}-src = ""
