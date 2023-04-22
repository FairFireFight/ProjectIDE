import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ControlsMenuBar extends JMenuBar {
	//file menu
	public static JMenu fileMenu;
	public static JMenuItem saveButton;
	public static JMenuItem saveAllButton;
	public static JMenuItem loadButton;
	
	//file menu -> new menu
	public static JMenu newMenu;
	public static JMenuItem newProject;
	public static JMenuItem newClass;
	public static JMenuItem newFolder;

	public static JMenuItem exitButton;
	
	public ControlsMenuBar() {
		super();
		
		fileMenu = new JMenu(" File ");
		saveButton = new JMenuItem("Save");
		saveButton.addActionListener(e -> WorkAreaTabs.saveSelectedTab());
		
		saveAllButton = new JMenuItem("Save all");
		saveAllButton.addActionListener(e -> SplitPaneApp.workAreaTabs.saveAllTabs());
		
		loadButton = new JMenuItem("Load");
		exitButton = new JMenuItem("Exit");

		loadButton.addActionListener(new LoadProjectHandler());
		//exit handler
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION) 
						== JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});
		
		newMenu = new JMenu("New");
		
		newProject = new JMenuItem("Java Project");
		newProject.addActionListener(new CreateNewHandler());
		
		newClass = new JMenuItem("Java Class");
		newClass.addActionListener(new CreateNewHandler());
		
		newFolder = new JMenuItem("Folder");
		newFolder.addActionListener(new CreateNewHandler());
		
		//new menu additions
		newMenu.add(newProject);
		newMenu.addSeparator();
		newMenu.add(newClass);
		newMenu.add(newFolder);
		
		//file additions
		fileMenu.add(saveButton);
		fileMenu.add(saveAllButton);
		fileMenu.add(loadButton);
		fileMenu.addSeparator();
		fileMenu.add(newMenu);
		fileMenu.addSeparator();
		fileMenu.add(exitButton);

		this.add(fileMenu);
		this.setBackground(ProjectConstants.CONSOLE_COLOR);
	}
}
