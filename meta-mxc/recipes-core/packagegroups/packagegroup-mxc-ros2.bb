PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

ROS2_COMMON_PACKAGES = " \
    ros2-control \
    ros2-control-test-assets \
    ros2-controllers \
    ros2-controllers-test-nodes \
    ros2-ouster \
    ros2-socketcan \
    ros2-socketcan-msgs \
    ros2acceleration \
    ros2action \
    ros2bag \
    ros2cli \
    ros2cli-common-extensions \
    ros2cli-test-interfaces \
    ros2component \
    ros2controlcli \
    ros2doctor \
    ros2interface \
    ros2launch \
    ros2launch-security \
    ros2launch-security-examples \
    ros2lifecycle \
    ros2lifecycle-test-fixtures \
    ros2multicast \
    ros2node \
    ros2nodl \
    ros2param \
    ros2pkg \
    ros2run \
    ros2service \
    ros2test \
    ros2topic \
    rcl-yaml-param-parser \
    libyaml \
"

ROS2_COMMON_UTIL_PACKAGES = " \
    foonathan-memory-staticdev \
    rclcpp \
    rclcpp-lifecycle \
    rclcpp-action \
    rclcpp-components \
    builtin-interfaces \
    common-interfaces \
    fastrtps-cmake-module \
    rosidl-default-runtime \
    rosidl-default-generators \
    rosidl-generator-c \
    rosidl-generator-cpp \
    rosidl-cmake \
    rosidl-adapter \
    ros-environment \
    ros-workspace \
    pluginlib \
    python3-colcon-core \
    python3-colcon-ros \
    python3-colcon-cmake \
    python3-colcon-common-extensions \
    python3-colcon-python-setup-py \
    python3-colcon-devtools \
    cv-bridge \
    pcl \
    qhull \
"

RDEPENDS:${PN} += "\
    ${ROS2_COMMON_PACKAGES} \
    ${ROS2_COMMON_UTIL_PACKAGES} \
"

ERROR_QA:remove = "version-going-backwards"