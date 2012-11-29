import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

import javax.imageio.ImageIO;

import org.neuroph.imgrec.ColorMode;
import org.neuroph.imgrec.ImageRecognitionHelper;

public class FaceScanner{
    
    IplImage image;
    static String filename;
    static int width, height, x, y;
    ImageRecognitionHelper helper;
    Dimension canvasDim;
    FaceDetection faceDetect;
    
    public FaceScanner(String name) {
    	filename = name;
    }
    
    public static BufferedImage scanFrame(UserInterface ui, CanvasFrame canvas) {
        final OpenCVFrameGrabber frameGrabber = new OpenCVFrameGrabber(0);
        try {
            canvas.setSize(640, 480);
            frameGrabber.start();
            IplImage img = frameGrabber.grab();
            if (img != null) {
                cvSaveImage("ScannedImages/" + ui.nameText.getText() + ".bmp", img);
                canvas.showImage(img);
                return img.getBufferedImage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return new IplImage().getBufferedImage();
    }
    
	public void runScan(UserInterface ui) throws InterruptedException {
		Boolean scanIn = false;
		CanvasFrame canvas = ui.canvas;
		canvasDim = new Dimension(640, 480);
		faceDetect = new FaceDetection();
		
		while(true){
			while(canvas.isEnabled()){
            	scanFrame(ui, canvas);
            	Thread.sleep(10);
            	scanIn = true;
        	}
			if(scanIn && !(canvas.isEnabled())){
				scanIn = false;
				//Scan image in
				try {
					image = faceDetect.DetectFaces("ScannedImages/" + ui.nameText.getText() + ".bmp");
				} catch (Exception e) {
					e.printStackTrace();
				}
				canvas.showImage(image);
				canvas.showImage(cropImage(image.getBufferedImage()));
				//canvas.showImage(getGrayScaleImages("ScannedImages/" + filename + ".bmp"));
				//helper = new ImageRecognitionHelper();
				//helper.createNewNeuralNetwork("ann", canvasDim, ColorMode.FULL_COLOR, arg3, arg4, arg5);
			}
			Thread.sleep(100);
		}
    }
	
	private BufferedImage cropImage(BufferedImage src) {
	      return src.getSubimage(x, y, width, height);
	}
	
	public BufferedImage getGrayScaleImages(String filename) {
		BufferedImage b = null;	
		File f = new File(filename);
		if (f.isFile()){
			try{
				b = ImageIO.read(new File(filename));
			}catch(IOException ioe){
				System.out.println("Error with reading the images.");
			}
			if(b != null){
				b = convertToGray(b);								
			}
		}
		return b;			
	}
	
	private BufferedImage convertToGray(BufferedImage img){
		BufferedImage gray = null;
		try{
		
			gray = new BufferedImage(img.getWidth(),img.getHeight(),
		              BufferedImage.TYPE_BYTE_GRAY);
			ColorConvertOp op = new ColorConvertOp(
		              img.getColorModel().getColorSpace(),
		              gray.getColorModel().getColorSpace(),null);
		    op.filter(img,gray); 
			return gray;
		}catch(Exception e){
			System.out.println("Error with changing the color of the image.");
		}
		return img;		
	}
}