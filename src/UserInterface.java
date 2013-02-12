import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.googlecode.javacv.CanvasFrame;

public class UserInterface extends JFrame{

	static CanvasFrame canvas = new CanvasFrame("Recognition Scanner");
	JButton addUserButton = new JButton("Add User");
	JButton recognizeButton = new JButton("Recognize");
	JButton closeButton = new JButton("Close");
	JPanel buttonsPanel = new JPanel();
	JPanel descPanel = new JPanel();
	JLabel status = new JLabel();
	GridLayout layout = new GridLayout(0, 3);
	String name;
	JFrame addUserFrame = new JFrame("Add User");
    
	public UserInterface(){
		// Initialize Control Panel
		super("Control Panel");
		super.add(buttonsPanel);
		buttonsPanel.setLayout(layout);
		//super.setLayout(layout);
		buttonsPanel.add(addUserButton);
		buttonsPanel.add(recognizeButton);
		buttonsPanel.add(closeButton);
		//super.add(descPanel);
		descPanel.setLayout(new GridLayout(0, 1));
		status.setText("                                           Welcome to the Facial Recognition Application!");
		descPanel.add(status);
		super.add(buttonsPanel, BorderLayout.NORTH);
		super.add(new JSeparator(), BorderLayout.CENTER);
		super.add(descPanel, BorderLayout.SOUTH);
		super.setUndecorated(true);
		super.setLocation(350, 502);
		super.setSize(640, 65);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initialize Canvas for Webcam
		canvas.setSize(640, 480);
		canvas.setLocation(350, 22);
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
