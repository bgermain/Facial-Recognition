
public class Main {
    
	public static void main(String[] args) throws Exception {
		FaceScanner scan = new FaceScanner();
		UserInterface ui = new UserInterface();
		ui.setVisible(true);
		scan.runScan(ui);
    } 
}
