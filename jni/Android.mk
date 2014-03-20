#android.mk语法  http://www.kandroid.org/ndk/docs/ANDROID-MK.html

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

OPENCV_INSTALL_MODULES:=on
#OPENCV_CAMERA_MODULES:=on

include /opt/OpenCV-2.4.6-android-sdk/sdk/native/jni/OpenCV.mk

LOCAL_MODULE    := Detector 
LOCAL_SRC_FILES := Detector.cpp

LOCAL_LDLIBS :=-llog

include $(BUILD_SHARED_LIBRARY)