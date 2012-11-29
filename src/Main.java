import javax.swing.JOptionPane;

public class Main {
    
	public static void main(String[] args) throws Exception {
		String name;
		do{
			name = JOptionPane.showInputDialog("Please enter the name for the image scanned: ", "Enter your name here");
			
			if(name == null || name.equalsIgnoreCase("Enter your name here")){
				JOptionPane.showMessageDialog(null, "Please enter a valid entry");
			}else{
				FaceScanner scan = new FaceScanner(name);
				UserInterface ui = new UserInterface();
				ui.nameText.setText(name);
				ui.setVisible(true);
	        	scan.runScan(ui);
			}
		}while(name == null || name.equalsIgnoreCase("Enter your name here"));
    } 
}
