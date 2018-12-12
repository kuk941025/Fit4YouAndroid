#include <jni.h>
#include <opencv2/core.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/opencv.hpp>
#include <utility>
#include <string.h>
#include <list>
#include <string>
#include <android/log.h>

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





extern "C"
JNIEXPORT void JNICALL
Java_skku_fit4you_1android_fragment_FitRoomFragment_tryClothing(JNIEnv *env, jobject instance,
                                                                jobject imgAvatar,
                                                                jobject imgBasicClothing,
                                                                jintArray userArr_,
                                                                jintArray sizeArr_,
                                                                jint clothingType,
                                                                jint layout_height,
                                                                jint layout_width) {
    jint *userArr = env->GetIntArrayElements(userArr_, NULL);
    jint *sizeArr = env->GetIntArrayElements(sizeArr_, NULL);

    // TODO
    vector<string> clothingInfo = {to_string(sizeArr[0]), to_string(sizeArr[1])};
    vector<int> size = {userArr[0], userArr[1], userArr[2], userArr[3], userArr[4], userArr[5], userArr[6], userArr[7], userArr[8]};
    //vector<string> clothingInfo = {"56", "68"};
    //vector<int> bodyInfo = {179, 17, 29, 35, 61, 35, 73, 20, 75};

    Mat &image_clothing = *(Mat *)imgBasicClothing;
    Mat &image_avatar = *(Mat *) imgAvatar;
    int pix = ((int)layout_height / size[0]);
    cv::Point pos;
    double size1, size2;
    if ((int) clothingType <= 1){ //if top
        pos.x = 300;
        if (clothingType == 1) pos.y = 40 + pix*size[2];
        else pos.y = 22 + pix*size[2];

        size1 = pix*(atof(clothingInfo[0].c_str()) + 20);
        size2 = pix*(atof(clothingInfo[1].c_str()));
    }
    else{
        if (clothingType == 3){
            pos.x = 360;
            size1 = pix*(atof(clothingInfo[0].c_str())) + 90;
        }
        else{
            pos.x = 420;
            size1 = pix*(atof(clothingInfo[0].c_str()));
        }
        pos.y = pix*size[2] + pix*size[4] - 5 * pix;
        size2 = pix*(atof(clothingInfo[1].c_str()));
    }

    Mat result;
    Mat output, body;
    //body = image_avatar;
    //cv::Mat clothe =
            //image_clothing;
    resize(image_clothing, result, Size());
//    resize(output, image_clothing, Size(size1, size2));

    image_avatar.copyTo(output);

    for (int y = std::max(pos.y, 0); y < image_avatar.rows; ++y){
        int fY = y - pos.y;
        if (fY >= image_clothing.rows)
            break;
        for (int x = std::max(pos.x, 0); x < image_avatar.cols; ++x){
            int fX = x - pos.x;
            if (fX >= image_clothing.cols)
                break;

            // determine the opacity of the foregrond pixel, using its fourth (alpha) channel.
            double opacity =
                    ((double)image_clothing.data[fY * image_clothing.step + fX * image_clothing.channels() + 3])/ 255.;

            for (int c = 0; opacity > 0 && c < image_avatar.channels(); ++c)
            {
                unsigned char clothe_Px =
                        image_clothing.data[fY * image_clothing.step + fX * image_clothing.channels() + c];
                unsigned char body_Px =
                        image_avatar.data[y * image_avatar.step + x * image_avatar.channels() + c];
                image_avatar.data[y*image_avatar.step + image_avatar.channels()*x + c] =
                        body_Px * (1. - opacity) + clothe_Px * opacity;
            }//End_opacity
        }//End posX
    }//End posY
    output.copyTo(image_avatar);
//    image_avatar = output;

    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", clothingInfo[0].c_str());


    env->ReleaseIntArrayElements(userArr_, userArr, 0);
    env->ReleaseIntArrayElements(sizeArr_, sizeArr, 0);
}