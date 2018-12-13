#include <opencv2/core.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/imgproc.hpp> //for resize

#include <opencv2/opencv.hpp>

#include <iostream>
#include <list>
#include <string>

using namespace cv;
using namespace std;

void fitting_up(const Mat& body, vector<string> &clothe_data, vector<int> &size);
void fitting_down(const Mat& body, vector<string> &clothe_data, vector<int> &size);
Mat _fitting(const Mat &body, Mat &clothe, Point2i location, double sW, double sH);
void show_result(const Mat &result);

int main(int argc, char** argv)
{  
  //example data for clothes..
  //필요한 옷 데이터 입니다 {입히려는 옷, 후드는 가슴단면 총길이 바지는 허리단면, 총장}
  //  
  //나머지 데이터도 이런식으로 전달해주세요

  //사이즈가 정수일지 소수일지 몰라 일단 스트링 배열인데 안드로이드에서 어떻게 주는지는 모르겠슴니다
  //스트링으로 일단 숫자 받아서 뒤에 처리하는 부분 있슴니다.

  vector<string> long_shirt2 = { "cl/S2.png","56","68"}; 
  vector<string> long_pants = { "cl/L_pants.png","39.5","53.5" };
  
  //example data for body
                         //height//head  //body //leg  //arm
  vector<int> size_data1 = {179, 18,27, 41,65, 41,87, 20,75};
  vector<int> size_data2 = {163, 17,29, 35,61, 35,73, 20,75};
                              // 1  2   3  4   5  6   7  8

  Mat image_body;  Mat image_body2;
  image_body = imread("179 Avatar.jpg", -1); //몸 이미지 읽어오는 부분
  image_body2 = imread("163 Avatar.jpg", -1);

  //상하의 함수가 분리되어있습니다.
 // fitting_up(image_body, long_shirt2, size_data1);
 // fitting_up(image_body2, long_shirt2, size_data2);

  fitting_down(image_body, long_pants, size_data1);
  fitting_down(image_body2, long_pants, size_data2);
  return 0;
}//END_Main

   //if the UPPER clothe
void fitting_up(const Mat& body, vector<string> &clothe_data, vector<int> &body_size){

   Mat image_clothe; //옷 이미지 읽어오는 부분
   //mat으로 바로 전달해주려면 아래 한줄을 지우고 image_clothe에 assign해주세요
   image_clothe = imread(clothe_data[0], IMREAD_UNCHANGED); 

   int pix = (1233/body_size[0]);   
   
   cv::Point pos;
   pos.x = 300;
   //긴소매랑 짧은소매 첫번째 옷들 어꺠선이 딴 상의들과 다릅니다.
   if(clothe_data[0]=="cl/L1.png"||clothe_data[0]=="cl/S1.png"){ pos.y = 40 + pix*body_size[2]; } 
   else{ pos.y = 22 + pix*body_size[2];}
          //blank+head
   
   double size1 = pix*(atof(clothe_data[1].c_str())+20);
                      //body+arm
   double size2 = pix*(atof(clothe_data[2].c_str()));
   //cout<<"width : "<<size1<<" length : "<<size2<<endl;
                                         
   _fitting(body,image_clothe, pos, size1, size2);
   return;
}

void fitting_down(const Mat& body, vector<string> &clothe_data, vector<int> &size){

   Mat image_clothe;
   image_clothe = imread(clothe_data[0], IMREAD_UNCHANGED); 

   int pix = (1233/size[0]);   
   //치마랑 바지랑 허리 스타일이 다름니다.
   cv::Point pos;
   if(clothe_data[0]=="cl/L_skirt.png"||clothe_data[0]=="cl/S_skirt1.png"||clothe_data[0]=="cl/S_skirt2.png"){
       pos.x = 360;
   }
   else{
       pos.x = 420;
   }
   pos.y = pix*size[2]+ pix*size[4]-5*pix;
          //blank+head+body - hip
//   cout<<size[0]<<" posX: "<<pos.x<<" posY: "<<pos.y<<" "<<endl;

   double size1;
   if(clothe_data[0]=="cl/L_skirt.png"||clothe_data[0]=="cl/S_skirt1.png"||clothe_data[0]=="cl/S_skirt2.png"){
         size1 = pix*(atof(clothe_data[1].c_str()))+90;  
   }
   else{
         size1 = pix*(atof(clothe_data[1].c_str()));
   }
   double size2 = pix*(atof(clothe_data[2].c_str()));
//   cout<<"width : "<<size1<<" length : "<<size2<<endl;

   _fitting(body,image_clothe, pos, size1, size2);
   return;
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
   imwrite("result.png",output);
   show_result(output);
   return output;
}//_fitting


void show_result(const Mat &result){  //for checking and markiing grad

	cv::Point gradR;
	gradR.x = 0;
	cv::Point gradC;
	gradC.y = 20;
	int gradFace=2;
	double gradScale=0.2;
	string grad_now ="start";
	
	for(int i=0;i<1000;i=i+20){
	   gradR.y = i;//position
	   gradC.x = i;
	   //number of grad
	   stringstream tmp;
	   tmp << i;	  
	   grad_now = tmp.str(); 
	   cv::putText( result, grad_now, gradR, gradFace, gradScale, Scalar::all(0));
	   cv::putText( result, grad_now, gradC, gradFace, gradScale, Scalar::all(0));
	}

     imshow("Display window", result);
     waitKey(0);
     return;
}//show_result

