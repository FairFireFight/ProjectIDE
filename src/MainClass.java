import javax.swing.UIManager;

public class MainClass {
	private static SplitPaneApp splitPaneApp;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception weTried) { }

		splitPaneApp = new SplitPaneApp();	
		splitPaneApp.setVisible(true);
		new ConfigurationManager();

		LoadProjectHandler.loadProjectTree(ConfigurationManager.openDirectory);
	}

	public static void updateTitle(String title) {
		splitPaneApp.setTitle(title);
	}
}