import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.googlecode.javacv.CanvasFrame;

public class UserInterface extends JFrame{

	static CanvasFrame canvas = new CanvasFrame("Recognition Scanner");
	JButton scanButton = new JButton("Scan Image");
	JButton detectButton = new JButton("Detect");
	JButton closeButton = new JButton("Close");
	Container container;
	FlowLayout layout = new FlowLayout();
    
	public UserInterface(){
		//Initialize Control Panel
		super("Control Panel");
		super.setLayout(layout);
		super.add(scanButton);
		super.add(detectButton);
		super.add(closeButton);
		super.setSize(160, 150);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Set Layout
		//container = getContentPane();
		//layout.setAlignment(FlowLayout.CENTER);
		//layout.layoutContainer(container);
		
		//Initialize Canvas for Webcam
		//super.add(canvas);
		canvas.setSize(640, 480);
		canvas.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //On click: Scan the image in and store it as the entered name
        scanButton.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				if(canvas.isEnabled()){
        					canvas.setEnabled(false);
        					scanButton.setText("Rescan");
        					JOptionPane.showMessageDialog(null, "Face Scanned!");
        				} else{
        					canvas.setEnabled(true);
        					scanButton.setText("Scan Image");
        				}
        			}
        		}
        );
        
        //On click: Close the program
        closeButton.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				System.exit(0);
        			}
        		}
        );
        
        //On click: Scan the face and recognize who the image is
        detectButton.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				JOptionPane.showMessageDialog(null, "Face Recognized! Welcome Back, Brad!");
        			}
        		}
        );
	}
}
