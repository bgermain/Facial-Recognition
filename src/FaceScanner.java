import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class FaceScanner {
    
    IplImage image;
    static CanvasFrame canvas = new CanvasFrame("Facial Recognition Scan");
    
    public FaceScanner() {
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }
    
    public static void scanFrame() {
        final OpenCVFrameGrabber frameGrabber = new OpenCVFrameGrabber(0);
        try {
        	//frameGrabber.setImageHeight(480);
        	//frameGrabber.setImageWidth(640);
            frameGrabber.start();
            IplImage img = frameGrabber.grab();
            if (img != null) {
                //cvSaveImage(name, img);
                //cvSaveImage("Image",img);
                canvas.showImage(img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public void runScan() throws InterruptedException {
        while(true){
            scanFrame(); 
            Thread.sleep(100);
        }
    }     
}