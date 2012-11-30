/* Facial Recognition
 * Objective: Create a program that recognizes a user through facial recognition.
 * The program inputs user image through the webcam and stores the image for processing
 * later. This process is essentially adding a user to the system. After the user is added
 * he/she can come back and use the recognize operation to scan the face in and compare it
 * to previously saved images. The image closest matched with the scanned image will be the
 * recognized user.
 * 
 * @author Brad Germain, Jeff Tran
 * @since 11.30.2012
 */

public class Main {
    
	public static void main(String[] args) throws Exception {
		FaceScanner scan = new FaceScanner();
		UserInterface ui = new UserInterface();
		ui.setVisible(true);
		scan.runScan(ui);
    } 
}
