require recipes-core/images/tisdk-default-image.bb

IMAGE_INSTALL += " \
	aws-cli \
	greengrass-bin  \
	aws-iot-fleetwise-edge \
"

IMAGE_INSTALL:append = " \
	python3-numpy \
	python3-scipy \
	python3-pip \
	python3-boto3 \
	python3-pandas \
	python3-can \
	opencv \
	aws-iot-device-sdk-python-v2 \
	aws-sdk-cpp \
"