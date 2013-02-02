import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

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
import static com.googlecode.javacv.cpp.opencv_core.CV_AA;

public class FaceDetector {
 
	// The cascade definition to be used for detection.
	private static final String CASCADE_FILE = "src/haarcascade_frontalface_alt.xml";
	
	public FaceDetector(){
		// Initialize Variables
	}
	 
	public IplImage DetectFaces(IplImage image) throws Exception {
	 
	    // Converts the image to gray scale for detection to work, using the same dimensions as the original.
	    IplImage grayImage = IplImage.createFrom(convertColorToGray(image.getBufferedImage()));
	    
	    CvMemStorage storage = CvMemStorage.create();
	 
	    // Using the cascade file, this creates a classification for what objects to detect. In our case it is the anterior of the face.
	    CvHaarClassifierCascade classifier = new CvHaarClassifierCascade(cvLoad(CASCADE_FILE));
	 
	    // Detect Haar-like objects, depending on the classifier. In this case we use a classifier for detecting the anterior of the face.
	    CvSeq faces = cvHaarDetectObjects(grayImage, classifier, storage, 1.1, 1, 0);
	    
	    // Initialize the static variables in FaceScanner for determining the area to crop the largest detected face.
	    FaceScanner.height = 0;
	    FaceScanner.width = 0;
	    FaceScanner.x = 0;
	    FaceScanner.y = 0;
	    
	    // Loop through all detected faces and save the largest (closest) face.
	    for (int i = 0; i < faces.total(); i++) {
		    CvRect rect = new CvRect(cvGetSeqElem(faces, i));
		    if(FaceScanner.width < rect.width()){
			    FaceScanner.width = rect.width();
			    FaceScanner.height = rect.height();
			    FaceScanner.x = rect.x();
			    FaceScanner.y = rect.y();
		    }

		    if(FaceScanner.displayRects){
			    /*Uncomment to draw the rectangles around the detected faces.*/
			    //if(rect.width() > 130 && rect.height() > 130){
			    	// Draw a square around the detected face.
			    	cvRectangle(image, cvPoint(rect.x(), rect.y()), cvPoint(rect.x() + rect.width(), rect.y() + rect.height()), CvScalar.GREEN, 2, CV_AA, 0);
			  	//}
			  	/*-----------------------------------------------------------*/
		    }
	    }
	 
	    // Checks that there was a detected face in the image before saving. Also, the detected "face" must be large enough to be considered
	    // a detected face. This is to limit the amount of erroneous detections. This saves the full size image with detections drawn on
	    // whole image before cropping.
	    if(!(FaceScanner.height == 0 && FaceScanner.width == 0) && !(FaceScanner.height < 130 && FaceScanner.width < 130)){
	    	// Save the image with rectangles.
	    	//cvSaveImage(filename.replace(".png", "-Rect.png"), image);
	    }else{
	    	return null;
	    }
	    
	    return image;
	}
	
	/* convertColorToGray
	 * Changes a color image to gray scale.
	 */
	private BufferedImage convertColorToGray(BufferedImage image){
		try{
			BufferedImage gray = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
			ColorConvertOp convertOp = new ColorConvertOp(image.getColorModel().getColorSpace(), gray.getColorModel().getColorSpace(), null);
			convertOp.filter(image,gray);
			return gray;
		}catch(Exception e){
			System.out.println("Color conversion failed.");
		}
		return image;		
	}
}