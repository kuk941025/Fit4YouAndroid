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

Mat fitting(const Mat& body, vector<string> &clothe_data, vector<int> &size);

int main(int argc, char** argv)
{ 
  vector<string> long_shirt2 = { "cl/S2.png","56","68"}; 
  vector<string> long_pants = { "cl/L_pants.png","39.5","53.5" };
  
  //example data for body
                         //height//head  //body //leg  //arm
  vector<int> size_data1 = {179, 18,27, 41,65, 41,87, 20,75};
  vector<int> size_data2 = {163, 17,29, 35,61, 35,73, 20,75};
                               // 1  2   3  4   5  6   7  8

  Mat image_body = imread("179 Avatar.jpg", -1); //몸 이미지 읽어오는 부분
  Mat image_body2 = imread("163 Avatar.jpg", -1);
  
  fitting(image_body, long_pants, size_data1);

   return 0;
}//main

Mat fitting(const Mat& body, vector<string> &clothe_data, vector<int> &size){
 
  cv::Mat clothe;
  clothe  = imread(clothe_data[0], IMREAD_UNCHANGED);
  int pix = (1233/size[0]);
  cv::Point pos;
  double size1; double size2;

  if(clothe_data[0]=="cl/L1.png"||clothe_data[0]=="cl/L2.png"||clothe_data[0]=="cl/L3.png"||clothe_data[0]=="cl/O1.png"||clothe_data[0]=="cl/O2.png"||clothe_data[0]=="cl/S1.png"||clothe_data[0]=="cl/S2.png"){

       pos.x = 300;
     //긴소매랑 짧은소매 첫번째 옷들 어꺠선이 딴 상의들과 다릅니다.
   if(clothe_data[0]=="cl/L1.png"||clothe_data[0]=="cl/S1.png"){ 
        pos.y = 40 + pix*size[2]; 
    } 
   else{ pos.y = 22 + pix*size[2];}
          //blank+head
   
   size1 = pix*(atof(clothe_data[1].c_str())+20);
                      //body+arm
   size2 = pix*(atof(clothe_data[2].c_str()));
   //cout<<"width : "<<size1<<" length : "<<size2<<endl;
  }
  else{

    if(clothe_data[0]=="cl/L_skirt.png"||clothe_data[0]=="cl/S_skirt1.png"||clothe_data[0]=="cl/S_skirt2.png"){
       pos.x = 360;
       size1 = pix*(atof(clothe_data[1].c_str()))+90;
   }
   else{
       pos.x = 420;
       size1 = pix*(atof(clothe_data[1].c_str()));
   }
   pos.y = pix*size[2]+ pix*size[4]-5*pix;
          //blank+head+body - hip
   size2 = pix*(atof(clothe_data[2].c_str()));
//   cout<<"width : "<<size1<<" length : "<<size2<<endl;
   
  }//END_else

 Mat output;
 resize(clothe, clothe, Size(size1, size2));
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
   imwrite("total_result.png", output);
   //imshow("Display window", output);
   return output;
 
}

