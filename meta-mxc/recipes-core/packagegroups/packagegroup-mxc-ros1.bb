PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

ROS1_COMMON_PACKAGES = " \
    rosconsole \
    rosparam \
    catkin \
    genmsg \
    rosgraph \
    genpy \
    rosbuild \
    rospack \
    rosmake \
    rosboost-cfg \
    rosbash \
    roslib \
    roscreate \
    mk \
    rosclean \
    rosunit \
    roslang \
    roscpp \
    rosout \
    roscpp-serialization \
    roscpp-traits \
    topic-tools \
    rostest \
    rostopic \
    message-generation \
    cpp-common \
    message-filters \
    rosservice \
    rospy \
    rosgraph-msgs \
    rosnode \
    std-srvs \
    xmlrpcpp \
    roslaunch \
    rosmaster \
    roswtf \
    rosbag-storage \
    rosbag \
    rosmsg \
    std-msgs \
    message-runtime \
    rostime \
    gencpp \
    roslz4 \
"

ROS1_COMMON_UTIL_PACKAGES = " \
    ros-base \
"

RDEPENDS:${PN} += "\
    ${ROS1_COMMON_PACKAGES} \
    ${ROS1_COMMON_UTIL_PACKAGES} \
"

ERROR_QA:remove = "version-going-backwards"