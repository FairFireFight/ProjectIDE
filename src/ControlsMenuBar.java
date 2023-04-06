import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
		
		fileMenu.add(saveButton);
		fileMenu.add(loadButton);
		
		fileMenu.addSeparator();
		
		fileMenu.add(exitButton);
		
		
		this.add(fileMenu);
		this.setBackground(ProjectConstants.CONSOLE_COLOR);
	}
}
