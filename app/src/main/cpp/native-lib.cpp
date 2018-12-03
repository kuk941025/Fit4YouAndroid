#include <jni.h>
#include <opencv2/opencv.hpp>
#include <map>
#include <utility>
#include <string.h>
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
JNIEXPORT jobjectArray JNICALL
Java_skku_fit4you_1android_activity_RegisterActivity_receiveData(JNIEnv *env, jobject instance) {

    // TODO
    jobjectArray ret;
    int i;

    char *message[5] = {"qwe", "asd", "qewqeq", "sdada", "Adsaad"};

    ret = (jobjectArray)env->NewObjectArray(5, env->FindClass("java/lang/String"), env->NewStringUTF(""));

    for (i = 0; i < 5; i++){
        env->SetObjectArrayElement(ret, i, env->NewStringUTF(message[i]));
    }
    return ret;
}

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_skku_fit4you_1android_activity_RegisterActivity_sendData(JNIEnv *env, jobject instance,
                                                              jobjectArray strArray) {

    char test[5][20];
    // TODO
    int stringCount = env->GetArrayLength(strArray);
    for (int i = 0; i < stringCount; i++){
        jstring string = (jstring) (env->GetObjectArrayElement(strArray, i));
        const char *rawString = env->GetStringUTFChars(string, 0);
        env->ReleaseStringUTFChars(string, rawString);
        strcpy(test[i], rawString);
    }
    jobjectArray ret = (jobjectArray)env->NewObjectArray(5, env->FindClass("java/lang/String"), env->NewStringUTF(""));;
    for (int i = 0; i < stringCount; i++){
        env->SetObjectArrayElement(ret, i, env->NewStringUTF(test[i]));
    }
    return ret;
}