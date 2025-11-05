do_configure:prepend() {
    sed -i '/find_package(jsoncpp REQUIRED)/a set_target_properties(jsoncpp_lib PROPERTIES POSITION_INDEPENDENT_CODE ON)' ${S}/CMakeLists.txt
}
