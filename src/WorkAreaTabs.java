import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class WorkAreaTabs extends JTabbedPane{
	public static List<NumberedTextArea> openTabs;
	public static int selectedIndex;
	
	// right click menu
	public static JPopupMenu menu;
	public static JMenuItem saveButton;
	public static JMenuItem closeButton;
	public static JMenuItem closeAllButton;
	
	WorkAreaTabs(){
		super(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		openTabs = new ArrayList<>();
		
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedIndex = WorkAreaTabs.this.getSelectedIndex();
				if (e.getButton() == MouseEvent.BUTTON3 && selectedIndex != -1) {
					selectedIndex = WorkAreaTabs.this.getSelectedIndex();
					
					menu.show(WorkAreaTabs.this, e.getX(), e.getY());
				}
				
				if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON2 ) && selectedIndex != -1) {
					selectedIndex = WorkAreaTabs.this.getSelectedIndex();
				}
			}
		});
		
		saveButton = new JMenuItem("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NumberedTextArea openTextArea = openTabs.get(selectedIndex);
				
				openTextArea.saveText();
			}
		});
		
		// TODO close functionality
		closeButton = new JMenuItem("Close");
		
		closeAllButton = new JMenuItem("Close All");
		
		menu = new JPopupMenu();
		menu.add(saveButton);
		menu.addSeparator();
		menu.add(closeButton);
		menu.add(closeAllButton);
	}
	
	public void openFile(File file) {
		// ignore folders
		if (file.isDirectory())
			return;
		
		// if tab is already open, switch to it
		int openIndex = findFileInTabs(file.toPath());		
		if (openIndex != -1) {
			this.setSelectedIndex(openIndex);
		} else { // otherwise load file from disk
			NumberedTextArea createdArea = new NumberedTextArea(file.getName());
			createdArea.filePath = file.toPath();
			this.addTab(file.getName(), createdArea);
			openTabs.add(createdArea);
			
			selectedIndex = this.getSelectedIndex();
			// reading file
			try {
				List<String> readLines;
				readLines = Files.readAllLines(file.toPath());
				
				for (String line : readLines) {
					//System.out.println(line);
					createdArea.textArea.append(line + "\n");
				}
			} catch (IOException e) {
				System.err.println("An error occured trying to load " + file.getPath());
				e.printStackTrace();
			} finally {
				this.setSelectedComponent(createdArea);
			}
		}
	}
	
	/**
	 *  Checks if the file to open is already opened in a tab
	 * @param path File to find in open tabs
	 * @return Index of the file in open tabs, if it doesnt exist the function will return -1
	 */
	private int findFileInTabs(Path path) {
		for (int i = 0; i < openTabs.size(); i++) {
			if (openTabs.get(i).filePath.toString().equals(path.toString())) {
				return i;
			}
		}
		return -1;
	}
	
	public void setUnsavedChangesMark(boolean set) {
		//NumberedTextArea n = openTabs.get(selectedIndex);
		String originalTitle = this.getTitleAt(selectedIndex);
		
		if (set) { //add marker
			if (originalTitle.indexOf("*") == -1)
				this.setTitleAt(selectedIndex, "*" + originalTitle);
		}
		else { // remove marker
			if (originalTitle.indexOf("*") != -1)
				this.setTitleAt(selectedIndex, originalTitle.substring(1));
		}
	}
}
