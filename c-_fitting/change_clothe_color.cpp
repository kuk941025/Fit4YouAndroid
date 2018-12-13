#include <opencv2/core.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/imgproc.hpp> //for resize
#include <opencv2/opencv.hpp>

#include <string>
#include <iostream>

using namespace cv;
using namespace std;

Mat change_color(String img_name);

int main(int argc, char* argv[])
{
   change_color("clothes/c1.png");
    return 0;
}//EndMain

Mat change_color(String img_name){
    Mat img_input = imread(img_name, IMREAD_UNCHANGED); 
     
    int height = img_input.rows;
    int width = img_input.cols;

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
	Vec4b pixel = img_input.at<Vec4b>(y, x);
	
         int b = pixel[0];
         int g = pixel[1];
	 int r = pixel[2];

         int trans = pixel[3];

	if( b==255 && g==255 && r==255 && trans >0 ){
          //if(trans==0){ 
           img_input.at<Vec4b>(y,x)[0]=223;
           img_input.at<Vec4b>(y,x)[1]=123;
           img_input.at<Vec4b>(y,x)[2]=15;

         } //EndIF    
       }//EndFor
    }
   imshow("result",img_input);
   waitKey(0);

   return img_input;
}
