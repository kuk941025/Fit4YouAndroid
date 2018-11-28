#include <opencv2/core.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/imgproc.hpp> //for resize

//#include <stdafx.h>
#include <opencv2/opencv.hpp>

#include <iostream>
#include <string>

using namespace cv;
using namespace std;


void fitting(const Mat &background, const Mat &foreground, Mat &output, Point2i location, float s1, float s2);


int main(int argc, char** argv)
{
	
	Mat image_body, image_clothe;
	
	image_clothe = imread("red.png", IMREAD_UNCHANGED); 
	//알파 채널 포함된 32비트 컬러 이미지, Transparent PNG
	image_body = imread("body.jpg", IMREAD_COLOR);  
	//채널 당 8비트인 24비트 컬러 이미지

	if ( image_body.empty() || image_clothe.empty() )  // Check for invalid input
	{
		cout << "Could not open or find the image" << std::endl;
		return -1;
	}

	
	namedWindow("Display window", WINDOW_AUTOSIZE); // Create a window for display.
	//namedWindow("Display window", WINDOW_NORMAL|WINDOW_KEEPRATIO); // Create a window for display.


	//body image resize for convinence  //sero, garo	
	resize(image_body,image_body,Size(),1.5626,1.5625); 
	cout <<"size : "<<image_body.rows<<" "<<image_body.cols;
	
	//do fitting
	Mat image_result;
        //fitting(position, size,size)
	fitting(image_body, image_clothe, image_result, cv::Point(80, 60), 0.35, 0.35);
	
	//for markiing grad
	cv::Point gradR;
	gradR.x = 0;
	cv::Point gradC;
	gradC.y = 20;
	int gradFace=2;
	double gradScale=0.2;
	string grad_now ="start";
	

	for(int i=0;i<image_body.rows;i=i+20){
	   gradR.y = i;//position
	   gradC.x = i;
	   //number of grad
	   stringstream tmp;
	   tmp << i;	  
	   grad_now = tmp.str(); 
	   cv::putText( image_result, grad_now, gradR, gradFace, gradScale, Scalar::all(0));
	   cv::putText( image_result, grad_now, gradC, gradFace, gradScale, Scalar::all(0));
	}


	imshow("Display window", image_result);
        imwrite("test.png",image_result);
	waitKey(0); 
	return 0;
}


void fitting(const Mat &background, const Mat &foreground, Mat &output, Point2i location, float s1, float s2)
{	
	Mat image_clothe;
	//cout<<s1<<s2<<std::endl;
	resize(foreground, image_clothe, Size(), s1, s2);
	background.copyTo(output);

// start at the row indicated by location, or at row 0 if location.y is negative.
	for (int y = std::max(location.y, 0); y < background.rows; ++y)
	{
		int fY = y - location.y; // because of the translation

		 // we are done of we have processed all rows of the foreground image.
		if (fY >= image_clothe.rows)
			break;

		// start at the column indicated by location, 

		// or at column 0 if location.x is negative.
		for (int x = std::max(location.x, 0); x < background.cols; ++x)
		{
			int fX = x - location.x; // because of the translation.

		// we are done with this row if the column is outside of the foreground image.
			if (fX >= image_clothe.cols)
				break;

		// determine the opacity of the foregrond pixel, using its fourth (alpha) channel.
			double opacity =
				((double)image_clothe.data[fY * image_clothe.step + fX * image_clothe.channels() + 3])/ 255.;


			// and now combine the background and foreground pixel, using the opacity, 

			// but only if opacity > 0.
			for (int c = 0; opacity > 0 && c < output.channels(); ++c)
			{
				unsigned char image_clothe_Px =
					image_clothe.data[fY * image_clothe.step + fX * image_clothe.channels() + c];
				unsigned char backgroundPx =
					background.data[y * background.step + x * background.channels() + c];
				output.data[y*output.step + output.channels()*x + c] =
					backgroundPx * (1. - opacity) + image_clothe_Px * opacity;
			}
		}
	}
}


