#include "opencv2/objdetect/objdetect.hpp" 
#include "opencv2/highgui/highgui.hpp" 
#include "opencv2/imgproc/imgproc.hpp"
#include <opencv2/core.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/opencv.hpp>

#include <iostream> 
#include <stdio.h>

using namespace std; 
using namespace cv;

/** Function Headers */ 

void detectAndDisplay( Mat frame );

/** Global variables */ 
String body_cascade_name = "haarcascade_fullbody.xml";
CascadeClassifier body_cascade;

String face_cascade_name = "haarcascade_frontalface_default.xml";
CascadeClassifier face_cascade;
 
string window_name = "testing"; 

/** @function main */
int main( int argc, const char** argv ) 
{ 
  Mat img1=imread("body_img/s1.PNG");

   //-- 1. Load the cascades 

   if( !body_cascade.load( body_cascade_name ) )
    { 
       printf("--(!)Error loading 1\n"); return -1; 
     };
   if( !face_cascade.load( face_cascade_name ) )
    { 
       printf("--(!)Error loading 2\n"); return -1; 
     };

resize(img1,img1,Size(),1.5,1.5); 
//detectAndDisplay(img);

//detectAndDisplay(img2);
detectAndDisplay(img1); 

int c = waitKey(0); 

    
return 0; 
}

/** @function detectAndDisplay */ 
void detectAndDisplay( Mat frame ) 
{ 
    vector<Rect> bodys;
    vector<Rect> faces;

    Mat frame_gray;

    cvtColor( frame, frame_gray, CV_BGR2GRAY ); 
    equalizeHist( frame_gray, frame_gray );

    //-- detect body & face */
    body_cascade.detectMultiScale(frame_gray, bodys, 1.1, 2, 18|9, Size(3,7));
    face_cascade.detectMultiScale(frame_gray, faces, 1.1, 2, 0|CASCADE_SCALE_IMAGE, Size(30, 30) ); 

    for(int i = 0; i < faces.size(); i++) {            
         rectangle(frame,Point(faces[i].x,faces[i].y),Point(faces[i].x+faces[i].width, faces[i].y+faces[i].height),Scalar(0,0,255),4,8,0);

cout<<"face - "<<"h :"<<faces[i].height<<" w : "<<faces[i].width<<endl;

         rectangle(frame,Point (faces[i].x, faces[i].y+faces[i].height),Point (faces[i].x+faces[i].width, (2.5)*(faces[i].y+faces[i].height)),Scalar(0,0,255),4,8,0);

     rectangle(frame,Point(faces[i].x+faces[i].width, faces[i].y+faces[i].height),Point(faces[i].x+2*(faces[i].width), faces[i].y+faces[i].height),Scalar(0,0,255),4,8,0);

rectangle(frame,Point(faces[i].x-faces[i].width, faces[i].y),Point(faces[i].x,faces[i].y+faces[i].height),Scalar(0,0,255),4,8,0);

cout<<"waist : "<<(3.5)*(faces[i].y+faces[i].height)<<endl;
cout<<"shoulder : "<<3.*(faces[i].width)<<endl;         
  }

    for( int j = 0; j < bodys.size(); j++ ) 
        { 
            //Point center( bodys[j].x + bodys[j].width*0.5, bodys[j].y+ + bodys[j].height*0.5 ); 
            rectangle(frame, bodys[j].tl(), bodys[j].br(),Scalar( 255, 0, 255 ),2,8);
            cout<<"body - "<<"tl : "<<bodys[j].tl()<<" br :"<<bodys[j].br()<<endl;
            //cout<<"x :"<<bodys[j].x<<"y :"<<bodys[j].y<<endl;
            cout<<"body - "<<"h :"<<bodys[j].height<<" w :"<<bodys[j].width<<endl;
        } 
    imshow( window_name, frame ); 
}
