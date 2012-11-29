import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

import org.neuroph.imgrec.ImageRecognitionHelper;

public class FaceScanner{
    
    IplImage image;
    static String filename;
    static int width, height, x, y;
    static boolean detect;
    ImageRecognitionHelper helper;
    Dimension canvasDim;
    FaceDetection faceDetect;
    
    public FaceScanner(String name) {
    	filename = name;
    }
    
    public static IplImage scanFrame(UserInterface ui, CanvasFrame canvas) throws Exception {
        final OpenCVFrameGrabber frameGrabber = new OpenCVFrameGrabber(0);
        try {
            canvas.setSize(640, 480);
            frameGrabber.start();
            IplImage img = frameGrabber.grab();
            if (img != null) {
                canvas.showImage(img);
                return img;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return frameGrabber.grab();
    }
    
	public void runScan(UserInterface ui) throws Exception {
		Boolean scanIn = false;
		CanvasFrame canvas = ui.canvas;
		BufferedImage croppedImage;
		canvasDim = new Dimension(640, 480);
		faceDetect = new FaceDetection();
		detect = false;
		
		while(true){
			while(canvas.isEnabled()){
            	image = scanFrame(ui, canvas);
            	Thread.sleep(10);
            	scanIn = true;
        	}
			if(scanIn && !(canvas.isEnabled())){
				scanIn = false;
				// Scan and save image.
				try {
					cvSaveImage("ScannedImages/" + ui.nameText.getText() + ".bmp", image);
					JOptionPane.showMessageDialog(null, "Image Saved!");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "The image could not be saved, please try again.");
				}
				
				//helper = new ImageRecognitionHelper();
				//helper.createNewNeuralNetwork("ann", canvasDim, ColorMode.FULL_COLOR, arg3, arg4, arg5);
			}else if(detect && !(canvas.isEnabled())){
				try{
					detect = false;
					image = faceDetect.DetectFaces("ScannedImages/" + ui.nameText.getText() + ".bmp");
					canvas.showImage(image);
					Thread.sleep(1000);		//Delay time to see the faces that are detected before choosing the closest.
					croppedImage = cropImage(image.getBufferedImage());
					canvas.showImage(croppedImage);
					cvSaveImage("ScannedImages/" + ui.nameText.getText() + "-DetectedFace.bmp", IplImage.createFrom(croppedImage));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "There are no faces detected, please rescan to try again.");
				}
			}
			Thread.sleep(100);
		}
    }
	
	private BufferedImage cropImage(BufferedImage src) {
	      return src.getSubimage(x, y, width, height);
	}
}