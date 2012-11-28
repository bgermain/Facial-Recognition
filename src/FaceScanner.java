import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

import javax.imageio.ImageIO;

public class FaceScanner{
    
    IplImage image;
    static String filename;
    
    public FaceScanner(String name) {
    	filename = name;
    }
    
    public static BufferedImage scanFrame(CanvasFrame canvas) {
        final OpenCVFrameGrabber frameGrabber = new OpenCVFrameGrabber(0);
        try {
            canvas.setSize(640, 480);
            frameGrabber.start();
            IplImage img = frameGrabber.grab();
            if (img != null) {
                cvSaveImage("ScannedImages/" + filename + ".bmp", img);
                canvas.showImage(img);
                return img.getBufferedImage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return new IplImage().getBufferedImage();
    }
    
	public void runScan(CanvasFrame canvas) throws InterruptedException {
		Boolean scanIn = false;
		Image img = null;
		BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = image.getGraphics();
		while(true){
			while(canvas.isEnabled()){
            	img = scanFrame(canvas); 
            	Thread.sleep(100);
            	scanIn = true;
        	}
			if(scanIn && !(canvas.isEnabled())){
				scanIn = false;
				//Scan image in
				canvas.showImage(getGrayScaleImages("ScannedImages/" + filename + ".bmp"));
			}
			Thread.sleep(100);
		}
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