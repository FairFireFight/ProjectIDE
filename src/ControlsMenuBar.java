import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class ControlsMenuBar extends JMenuBar {
	//file menu
	JMenu fileMenu;
	JMenuItem saveButton;
	JMenuItem loadButton;
	JMenuItem exitButton;
	
	public ControlsMenuBar() {
		super();
		
		fileMenu = new JMenu(" File ");
		saveButton = new JMenuItem("Save");
		loadButton = new JMenuItem("Load");
		exitButton = new JMenuItem("Exit");
		
		fileMenu.add(saveButton);
		fileMenu.add(loadButton);
		
		fileMenu.addSeparator();
		
		fileMenu.add(exitButton);
		
		
		this.add(fileMenu);
		this.setBackground(ProjectConstants.CONSOLE_COLOR);
	}
}
