#include <jni.h>
#include <opencv2/opencv.hpp>
#include <map>
#include <utility>
using namespace cv;
using namespace std;

extern "C"
JNIEXPORT void JNICALL
Java_skku_fit4you_1android_activity_OpenCVNativeTest_ConvertRGBtoGray(JNIEnv *env, jobject instance,
                                                                      jlong matAddrInput,
                                                                      jlong matAddrResult) {

    // TODO
    // 입력 RGBA 이미지를 GRAY 이미지로 변환

    Mat &matInput = *(Mat *)matAddrInput;

    Mat &matResult = *(Mat *)matAddrResult;


    cvtColor(matInput, matResult, COLOR_RGBA2GRAY);


}

extern "C"
JNIEXPORT jobject JNICALL
Java_skku_fit4you_1android_activity_RegisterActivity_getBodyInformation(JNIEnv *env,
                                                                        jobject instance,
                                                                        jlong matAddrInput) {

    // TODO
    // TODO
    Mat &matInput = *(Mat *)matAddrInput;
    map<string, string> mMap;


    env->PushLocalFrame(256);
    jclass hashMapClass = env->FindClass("java/util/HashMap");
    jmethodID hashMapInit = env->GetMethodID(hashMapClass, "<init>", "(I)V");
    jmethodID hashMapPut = env->GetMethodID(hashMapClass, "put", "()Ljava/lang/Object;");
    jobject hashMapObj = env->NewObject(hashMapClass, hashMapInit, mMap.size());

    env->CallObjectMethod(hashMapObj, hashMapPut, env->NewStringUTF("Test")), env->NewStringUTF("Hello");
    env->PopLocalFrame(hashMapObj);

    return hashMapObj;
}