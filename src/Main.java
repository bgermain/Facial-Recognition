import javax.swing.JOptionPane;

public class Main {
    
	public static void main(String[] args) throws InterruptedException {
		String name;
		do{
			name = JOptionPane.showInputDialog("Please enter the name for the image scanned: ", "Enter your name here");
			//Possibly read in filenames from ScannedImages folder to see if there are any matches on the name entered. If there are, then
			//change the name to <Name>-02 and increment for every entry
			if(name == null){
				JOptionPane.showMessageDialog(null, "Please enter a valid entry");
			}else{
				FaceScanner scan = new FaceScanner(name);
				UserInterface ui = new UserInterface();
				ui.setVisible(true);
	        	scan.runScan(ui.canvas);
			}
		}while(name == null);
    } 
}
