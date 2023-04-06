import javax.swing.UIManager;

public class MainClass {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception weTried) { }
		
		SplitPaneApp splitPaneApp = new SplitPaneApp();	
		
		splitPaneApp.setVisible(true);
	}
}