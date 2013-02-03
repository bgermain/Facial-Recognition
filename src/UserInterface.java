import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.googlecode.javacv.CanvasFrame;

public class UserInterface extends JFrame{

	static CanvasFrame canvas = new CanvasFrame("Recognition Scanner");
	JButton addUserButton = new JButton("Add User");
	JButton recognizeButton = new JButton("Recognize");
	JButton closeButton = new JButton("Close");
	GridLayout layout = new GridLayout(3, 1);
	String name;
	JFrame addUserFrame = new JFrame("Add User");
    
	public UserInterface(){
		// Initialize Control Panel
		super("Control Panel");
		super.setLayout(layout);
		super.add(addUserButton);
		super.add(recognizeButton);
		super.add(closeButton);
		super.setUndecorated(true);
		super.setLocation(0, 22);
		super.setSize(100, 480);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initialize Canvas for Webcam
		canvas.setSize(640, 480);
		canvas.setLocation(100, 20);
		canvas.setResizable(false);
		canvas.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // On click: Scan the image in and store it as the entered name.
		addUserButton.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				do{
        					name = JOptionPane.showInputDialog(addUserFrame, "Please enter the name of the new user: ", "Add User", JOptionPane.QUESTION_MESSAGE);
        					
        					if(name != null){
	        					if(name.isEmpty()){
	        						JOptionPane.showMessageDialog(null, "Please enter a valid entry");
	        						name = "";
	        					}else if(name != null){
	        						if(canvas.isEnabled()){
			        					canvas.setEnabled(false);	//Change status to initialize trigger to scan image, save, detect, and crop
			        					FaceScanner.changeFilename(name);
			        				}else{
			        					name = "";
			        				}
	        					}
        					}else{
        						name = "cancel";
        					}
        				}while(name.isEmpty());
        				
        			}
        		}
        );
        
        // On click: Close the program
        closeButton.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				System.exit(0);
        			}
        		}
        );
        
        // On click: Scan the face and recognize who the image is.
        recognizeButton.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				FaceScanner.recognize = true;
        				canvas.setEnabled(false);
        			}
        		}
        );
	}
}
