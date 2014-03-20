/*
 * Detector.cpp
 *
 *  Created on: 2014年3月18日
 *      Author: marshal
 */

#include <opencv2/opencv.hpp>
#include <iostream>
#include <android/log.h>

#include "marshal_opencvproto_Detector.h"

using namespace cv;
using namespace std;

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "opencv_detector", __VA_ARGS__))

JNIEXPORT void JNICALL Java_marshal_opencvproto_Detector_detect(JNIEnv *env,
		jobject thiz, jlong matPtr) {
	Mat *mat = (Mat*) matPtr;

	LOGI("mat col: %d", mat->cols);
}

JNIEXPORT jlong JNICALL Java_marshal_opencvproto_Detector_generateHistogram(
		JNIEnv *env, jobject thiz, jlong bitmapMatPtr) {
	LOGI("generate hist..");

	//彩色图Mat和灰度图Mat
	Mat *color = (Mat*) bitmapMatPtr, *grayScale = new Mat();
	//转换彩色图矩阵数据到灰度图矩阵
	cvtColor(*color, *grayScale, CV_RGB2GRAY);
	//创建直方图矩阵
	Mat *hist = new Mat();

	//直方图矩阵参数
	int channels[] = { 0 };
	int histSize[] = { 64 };
	float range[] = { 0, 256 };
	const float* ranges[] = { range };

	//计算直方图
	calcHist(grayScale, 1, channels, Mat(), *hist, 1, histSize, ranges, true,
			false);

	normalize(*hist, *hist, 0, 255, NORM_MINMAX);

	//TODO 未做归一化，做的时候有错误

	LOGI("generate ok!");

	return (jlong) hist;

}

