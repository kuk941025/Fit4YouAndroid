#include <opencv2/core.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/imgproc.hpp> //for resize

//#include <stdafx.h>
#include <opencv2/opencv.hpp>

#include <iostream>
#include <list>
#include <string>

using namespace cv;
using namespace std;

void fitting(const Mat& body, vector<string> &clothe_data, vector<int> &size);
Mat _fitting(const Mat &body, Mat &clothe, Point2i location, double sW, double sH);
void show_result(const Mat &result);

int main(int argc, char** argv)
{  
  // for storing standard data for clothes..
  vector<string> long_shirt1 = {"cl/L1.png","6","82","0.28","0.18"}; 
  vector<string> long_shirt2 = { "cl/L2.png","5","70","0.27","0.18" };
  vector<string> long_shirt3 = { "cl/L3.png","13","70","0.27","0.18" };
  vector<string> outer1 = { "cl/O1.png","8","75","0.27","0.18" };
  vector<string> outer2 = { "cl/O2.png","3","75","0.28","0.18" };
  vector<string> shirt1 = { "cl/S1.png","29","80","0.27","0.18" };
  vector<string> shirt2 = { "cl/S2.png","25","73","0.28","0.19" };
  vector<string> long_pants = { "cl/L_pants.png","70","250","0.27","0.17" };
  vector<string> long_skirt = { "cl/L_skirt.png","34","240","0.28","0.15" };
  vector<string> pants = { "cl/S_pants.png", "70","250","0.27","0.17" };
  vector<string> skirt1 = { "cl/S_skirt1.png","30","245","0.28","0.17" };
  vector<string> skirt2 = { "cl/S_skirt2.png","33","243","0.28","0.17" };

  // for resizing as size input
  // ex) size_data = { height, weight, clothe's 00size, clothe's 00size}
  // -> I cannot decide what data should be considered yet
  //                    but basically it depends on height
  vector<int> size_data = {180,70,66,20};
  
  // Therefore, from Android this code require the data about 
  // what clotehs and what size

  // like  Android input integer 3 ->
  // and using doubled array like
   vector<vector<string>> clothe_list = {long_shirt1, long_shirt2, long_shirt3, outer1, outer2, shirt1, shirt2, long_pants, long_skirt, pants, skirt1, skirt2};
  // So, clothe_list[3] -> long_shirt3 data array
  // Do fitting(,,,clothe_list[3])
  // good?

  Mat image_body;
  image_body = imread("body.png", IMREAD_COLOR);
  
  if(image_body.empty()){
     cout<<"fail to load image"<<endl;
     return 0;
   }

   //body image resize for convinence  //garo, sero	  
  resize(image_body,image_body,Size(300,500)); 
  cout <<"size : "<<image_body.rows<<" "<<image_body.cols;
  
  fitting(image_body, clothe_list[0],size_data);
//  fitting(image_body, long_shirt3,size_data);

  return 0;
}//END_Main

void fitting(const Mat& body, vector<string> &clothe_data, vector<int> &size){
   Mat image_clothe;
   image_clothe = imread(clothe_data[0], IMREAD_UNCHANGED); 

   cv::Point pos;
   pos.x = atoi(clothe_data[1].c_str()); //string to int
   pos.y = atoi(clothe_data[2].c_str());
   
   double size1 = atof(clothe_data[3].c_str()); // string to float
   double size2 = atof(clothe_data[4].c_str());

   /**

     later, do detailed control size by body & real clothe_data
     ex) height is 180 but clothe's length is over 000
     and then size1 * 1.5

   **/
                                         
   _fitting(body,image_clothe, pos, size1, size2);
   //_fitting(body,image_clothe, pos, 1, 1.2);   
   return;
}

Mat _fitting(const Mat &body, Mat &clothe, Point2i pos, double sW, double sH){
   Mat output;
   //cout<<s1<<s2<<std::endl;
   resize(clothe, clothe, Size(), sW, sH);
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

