#include <jni.h>
#include <opencv2/core.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/opencv.hpp>
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

    Vec4b a;

    return ret;
}

extern "C"
JNIEXPORT void JNICALL
Java_skku_fit4you_1android_activity_UploadClothingActivity_addColorToClothing(JNIEnv *env,
                                                                              jobject instance,
                                                                              jlong matAddrInput,
                                                                              jint color_red,
                                                                              jint color_blue,
                                                                              jint color_green) {

    // TODO
    Mat &matInput = *(Mat *)matAddrInput;

    int height = matInput.rows;
    int width = matInput.cols;

    int selected_r = (int) color_red;
    int selected_b = (int) color_blue;
    int selected_g = (int) color_green;

    for (int y = 0; y < height; y++){
        for (int x = 0; x < width; x ++){
            Vec4b pixcel = matInput.at<Vec4b>(y, x);

            int b = pixcel[0];
            int g = pixcel[1];
            int r = pixcel[2];

            int trans = pixcel[3];
            if (b == 255 && g == 255 && r == 255 && trans > 0){
                matInput.at<Vec4b>(y,x)[0] = selected_r;
                matInput.at<Vec4b>(y,x)[1] = selected_b;
                matInput.at<Vec4b>(y,x)[2] = selected_g;
            }
        }
    }


}