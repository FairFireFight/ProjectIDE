import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ControlsMenuBar extends JMenuBar {
	//file menu
	JMenu fileMenu;
	JMenuItem saveButton;
	JMenuItem loadButton;
	
	JMenu newButton;
	JMenuItem newProject;
	JMenuItem newClass;
	JMenuItem newFolder;

	
	JMenuItem exitButton;
	
	public ControlsMenuBar() {
		super();
		
		fileMenu = new JMenu(" File ");
		saveButton = new JMenuItem("Save");
		loadButton = new JMenuItem("Load");
		exitButton = new JMenuItem("Exit");
		
		//FIXME make an actual save handler class
		saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//TODO replace example code with properly functioning one
                String desktopPath = System.getProperty("user.home") + "/Desktop/";
                String fileName = "textfile.txt";

                try {
                    File file = new File(desktopPath + fileName);
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(SplitPaneApp.workAreaCustomPanel.getText());

                    bw.close();
                    fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });	
		
		loadButton.addActionListener(new loadProjectHandler());
		//exit handler
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", 
						JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});
		
		newButton = new JMenu("New");
		newProject = new JMenuItem("Java Project");
		newClass = new JMenuItem("Java Class");
		newFolder = new JMenuItem("Folder");
		
		newButton.add(newProject);
		newButton.addSeparator();
		newButton.add(newClass);
		newButton.add(newFolder);
		
		fileMenu.add(saveButton);
		fileMenu.add(loadButton);
		fileMenu.addSeparator();
		fileMenu.add(newButton);
		fileMenu.addSeparator();
		fileMenu.add(exitButton);
		
		this.add(fileMenu);
		this.setBackground(ProjectConstants.CONSOLE_COLOR);
	}
}
