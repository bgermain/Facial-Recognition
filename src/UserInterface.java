import java.awt.FlowLayout;
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
	FlowLayout layout = new FlowLayout();
    
	public UserInterface(){
		//Initialize Control Panel
		super("Control Panel");
		super.setLayout(layout);
		super.add(addUserButton);
		super.add(recognizeButton);
		super.add(closeButton);
		super.setSize(330, 100);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initialize Canvas for Webcam
		canvas.setSize(640, 480);
		canvas.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // On click: Scan the image in and store it as the entered name.
		addUserButton.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				String name;
        				do{
        					name = JOptionPane.showInputDialog("Please enter the name of the new user: ", "Enter your name here");
        					
        					if(name == null || name.equalsIgnoreCase("Enter your name here") || name.isEmpty()){
        						JOptionPane.showMessageDialog(null, "Please enter a valid entry");
        					}else{
        						if(canvas.isEnabled()){
		        					canvas.setEnabled(false);	//Change status to initialize trigger to scan image, save, detect, and crop
		        					FaceScanner.changeFilename(name);
		        				}
        					}
        				}while(name == null || name.equalsIgnoreCase("Enter your name here") || name.isEmpty());
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
