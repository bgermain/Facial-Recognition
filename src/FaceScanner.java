import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

public class FaceScanner{
    
    IplImage image;
    static String filename;
    
    public FaceScanner(String name) {
    	filename = name;
    }
    
    public static void scanFrame(CanvasFrame canvas) {
        final OpenCVFrameGrabber frameGrabber = new OpenCVFrameGrabber(0);
        try {
            canvas.setSize(640, 480);
            frameGrabber.start();
            IplImage img = frameGrabber.grab();
            if (img != null) {
                cvSaveImage("ScannedImages/" + filename + ".jpg", img);
                canvas.showImage(img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public void runScan(CanvasFrame canvas) throws InterruptedException {
		Boolean scanIn = false;
		while(true){
			while(canvas.isEnabled()){
            	scanFrame(canvas); 
            	Thread.sleep(100);
            	scanIn = true;
        	}
			if(scanIn && !(canvas.isEnabled())){
				scanIn = false;
				//Scan image in
			}
			Thread.sleep(100);
		}
    }     
}