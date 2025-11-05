do_configure:prepend() {
    sed -i 's/-Werror=shadow//g' ${S}/CMakeLists.txt
}

EXTRA_OECMAKE += "-DCMAKE_CXX_FLAGS='-Wno-error=shadow'"