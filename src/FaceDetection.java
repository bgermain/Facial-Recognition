import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvRectangle;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_core.CV_AA;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
 
public class FaceDetection {
	
	///// WORK ON DETECTING THE LARGEST RECTANGLE (closest person) THEN CROP THE DETECTED IMAGE AND SAVING IT.
 
  // The cascade definition to be used for detection.
  private static final String CASCADE_FILE = "src/haarcascade_frontalface_alt.xml";
 
  public IplImage DetectFaces(String filename) throws Exception {

    // Load the image that was previously scanned in.
    IplImage originalImage = cvLoadImage(filename, 1);
 
    // We need a grayscale image in order to do the recognition, so we
    // create a new image of the same size as the original one.
    IplImage grayImage = IplImage.create(originalImage.width(), originalImage.height(), IPL_DEPTH_8U, 1);
 
    // We convert the original image to grayscale.
    cvCvtColor(originalImage, grayImage, CV_BGR2GRAY);
 
    CvMemStorage storage = CvMemStorage.create();
 
    // We instantiate a classifier cascade to be used for detection, using the cascade definition.
    CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad(CASCADE_FILE));
 
    // We detect the faces.
    CvSeq faces = cvHaarDetectObjects(grayImage, cascade, storage, 1.1, 1, 0);
    
    //Initialize the static variables for determining the area to crop the largest detected face.
    FaceScanner.height = 0;
    FaceScanner.width = 0;
    FaceScanner.x = 0;
    FaceScanner.y = 0;
    
    //We iterate over the discovered faces and draw red rectangles around them.
    for (int i = 0; i < faces.total(); i++) {
      CvRect rect = new CvRect(cvGetSeqElem(faces, i));
      if(FaceScanner.width < rect.width()){
    	  FaceScanner.width = rect.width();
    	  FaceScanner.height = rect.height();
    	  FaceScanner.x = rect.x();
    	  FaceScanner.y = rect.y();
      }

      cvRectangle(originalImage, cvPoint(rect.x(), rect.y()), cvPoint(rect.x() + rect.width(), rect.y() + rect.height()), CvScalar.RED, 2, CV_AA, 0);
    }
 
    // Save the image to a new file.
    cvSaveImage(filename, originalImage);
    
    return originalImage;
  }
}