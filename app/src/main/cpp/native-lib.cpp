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
Mat _fitting(const Mat &body, Mat &clothe, Point2i pos, double sW, double sH){
    Mat output;
    //cout<<s1<<s2<<std::endl;
    resize(clothe, clothe, Size(sW, sH));
    body.copyTo(output);

    for (int y = std::max(pos.y, 0); y < body.rows; ++y){
        int fY = y - pos.y;
        if (fY >= clothe.rows)
            break;
        for (int x = std::max(pos.x, 0); x < body.cols; ++x){
            int fX = x - pos.x;
            if (fX >= clothe.cols)
                break;

            // determine the opacity of the foregrond pixel, using its fourth (alpha) channel.
            double opacity =
                    ((double)clothe.data[fY * clothe.step + fX * clothe.channels() + 3])/ 255.;

            for (int c = 0; opacity > 0 && c < output.channels(); ++c)
            {
                unsigned char clothe_Px =
                        clothe.data[fY * clothe.step + fX * clothe.channels() + c];
                unsigned char body_Px =
                        body.data[y * body.step + x * body.channels() + c];
                output.data[y*output.step + output.channels()*x + c] =
                        body_Px * (1. - opacity) + clothe_Px * opacity;
            }//End_opacity
        }//End posX
    }//End posY
    //imwrite("result.png",output);
    //show_result(output);
    return output;
}//_fitting

void fitting_up(const Mat& body, const Mat& clothing, vector<string> &clothe_data, vector<int> &body_size){

    Mat image_clothe; //옷 이미지 읽어오는 부분
    //mat으로 바로 전달해주려면 아래 한줄을 지우고 image_clothe에 assign해주세요
    //image_clothe = imread(clothe_data[0], IMREAD_UNCHANGED);
    //image_clothe = clothing;

    //int pix = (1233/body_size[0]);
//
//    cv::Point pos;
//    pos.x = 300;
//    //긴소매랑 짧은소매 첫번째 옷들 어꺠선이 딴 상의들과 다릅니다.
//    //if(clothe_data[0]=="cl/L1.png"||clothe_data[0]=="cl/S1.png"){ pos.y = 40 + pix*body_size[2]; }
//    //else{ pos.y = 22 + pix*body_size[2];}
//    pos.y = 22 + pix*body_size[2];
//    //blank+head
//
//    double size1 = pix*(atof(clothe_data[0].c_str())+20);
//    //body+arm
//    double size2 = pix*(atof(clothe_data[1].c_str()));
//    //cout<<"width : "<<size1<<" length : "<<size2<<endl;
//
//    //_fitting(body,image_clothe, pos, size1, size2);
    return;
}

void fitting_down(const Mat& body, vector<string> &clothe_data, vector<int> &size){

    Mat image_clothe;
    image_clothe = imread(clothe_data[0], IMREAD_UNCHANGED);

    int pix = (1233/size[0]);
    //치마랑 바지랑 허리 스타일이 다름니다.
    cv::Point pos;
//    if(clothe_data[0]=="cl/L_skirt.png"||clothe_data[0]=="cl/S_skirt1.png"||clothe_data[0]=="cl/S_skirt2.png"){
//        pos.x = 360;
//    }
//    else{
        pos.x = 420;
//    }
    pos.y = pix*size[2]+ pix*size[4]-5*pix;
    //blank+head+body - hip
//   cout<<size[0]<<" posX: "<<pos.x<<" posY: "<<pos.y<<" "<<endl;

    double size1;
//    if(clothe_data[0]=="cl/L_skirt.png"||clothe_data[0]=="cl/S_skirt1.png"||clothe_data[0]=="cl/S_skirt2.png"){
//        size1 = pix*(atof(clothe_data[1].c_str()))+90;
//    }
//    else{
        size1 = pix*(atof(clothe_data[0].c_str()));
//    }
    double size2 = pix*(atof(clothe_data[1].c_str()));
//   cout<<"width : "<<size1<<" length : "<<size2<<endl;

    _fitting(body,image_clothe, pos, size1, size2);
    return;
}





extern "C"
JNIEXPORT void JNICALL
Java_skku_fit4you_1android_fragment_FitRoomFragment_tryClothing(JNIEnv *env, jobject instance,
                                                                jobject imgAvatar,
                                                                jobject imgBasicClothing,
                                                                jintArray userArr_,
                                                                jintArray sizeArr_,
                                                                jint clothingType) {
    jint *userArr = env->GetIntArrayElements(userArr_, NULL);
    jint *sizeArr = env->GetIntArrayElements(sizeArr_, NULL);

    // TODO
    //vector<string> clothingInfo = {to_string(sizeArr[0]), to_string(sizeArr[1])};
    //vector<int> bodyInfo = {userArr[0], userArr[1], userArr[2], userArr[3], userArr[4], userArr[5], userArr[6], userArr[7], userArr[8]};
    vector<string> clothingInfo = {"56", "68"};
    vector<int> bodyInfo = {179, 17, 29, 35, 61, 35, 73, 20, 75};

    Mat &image_clothing = *(Mat *)imgBasicClothing;
    Mat &image_avatar = *(Mat *) imgAvatar;
    if ((int) clothingType == 0){
        //fitting_up(image_avatar, image_clothing, clothingInfo, bodyInfo);
    }
    else{

    }
    env->ReleaseIntArrayElements(userArr_, userArr, 0);
    env->ReleaseIntArrayElements(sizeArr_, sizeArr, 0);
}