import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

import javax.swing.JOptionPane;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

import org.neuroph.imgrec.ImageRecognitionHelper;

public class FaceScanner{
    
	IplImage image;
	static String filename;
	static String recFilename;
	static int width, height, x, y;
	static boolean recognize;
	ImageRecognitionHelper helper;
	Dimension canvasDim;
	FaceDetector faceDetect;
	FacialRecognizer faceRecognize;
	
	public FaceScanner() {
		filename = "Users/temp.png";
		recFilename = "UserAttempts/temp.png";
		image = new IplImage();
		width = 0;
		height = 0;
		x = 0;
		y = 0;
		recognize = false;
		
		//helper = new ImageRecognitionHelper();
		canvasDim = new Dimension();
		faceDetect = new FaceDetector();
		faceRecognize = new FacialRecognizer();
	}
	
	/* scanFrame
	 * Scans each frame through the webcam outputting video feedback.
	 */
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
	
	/* runScan
	 * Runs the face scanner and based on user input adjusts the number of frames.
	 */
	public void runScan(UserInterface ui) throws Exception {
		CanvasFrame canvas = ui.canvas;
		BufferedImage croppedImage;
		canvasDim = new Dimension(640, 480);
		faceDetect = new FaceDetector();
		
		while(true){
			while(canvas.isEnabled()){
	        	image = scanFrame(ui, canvas);
	        	Thread.sleep(10);
	    	}
			if(!recognize && !(canvas.isEnabled())){	//RUNS WHEN SCAN BUTTON IS PRESSED
				try{
					faceDetect.DetectFaces(image);		//Used primarily to populate the width, height, and location of the face to be detected.
					
					/*Uncomment to display the detection lines and uncomment the rectangle drawing in FaceDetector class*/
					//canvas.showImage(image);
					//Thread.sleep(1000);		//Delay time to see the faces that are detected before choosing the closest.
					//image = cvLoadImage(filename, 1); 	//Reload image to remove rectangle
					/*----------------------------------------*/
					
					croppedImage = cropImage(image.getBufferedImage());
					
					/*Uncomment to zoom and show the closest face*/
					//canvas.showImage(croppedImage);
					/*-------------------------------------------*/
					
					cvSaveImage(filename, IplImage.createFrom(croppedImage));
					//helper.createNewNeuralNetwork("ann", canvasDim, ColorMode.FULL_COLOR, arg3, arg4, arg5);
					JOptionPane.showMessageDialog(null, "User " + filename.replace(".png", "").replace("Users/", "") + " was added to the system.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "There are no faces detected, please rescan to try again.");
				}
				canvas.setEnabled(true);
			}else if(recognize && !(canvas.isEnabled())){	//RUNS WHEN RECOGNIZE BUTTON PRESSED
				String user = "";
				try{
					faceDetect.DetectFaces(image);		//Used primarily to populate the width, height, and location of the face to be detected.
					croppedImage = cropImage(image.getBufferedImage());
					cvSaveImage(recFilename, IplImage.createFrom(croppedImage));
					
					// Train the neural network with the images saved in Users and then 
					// recognize the face by finding the user with the highest accuracy
					// in comparing the two images.
					//user = faceRecognize.callNetwork("Users/", recFilename, "temp");
					
					JOptionPane.showMessageDialog(null, "User " + user + " was detected with a " + 10 +  "% accuracy level.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Unknown user.");
				}
				canvas.setEnabled(true);
			}
				
			Thread.sleep(100);	//Pause so the user can see the scanned image.
		}
	}
	
	/* cropImage
	 * Crops the passed image (detected face) for image processing through the recognition section.
	 */
	private BufferedImage cropImage(BufferedImage src) {
	      return src.getSubimage(x, y, width, height);
	}
	
	/* changeFilename
	 * Changes the filename to the name inputted by the user.
	 */
	public static void changeFilename(String name){
		filename = filename.replace("temp.png", name + ".png");
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