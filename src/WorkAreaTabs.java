import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class WorkAreaTabs extends JTabbedPane{
	List<NumberedTextArea> openTabs;
	
	WorkAreaTabs(){
		super(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		openTabs = new ArrayList<>();
	}
	
	public void openFile(File file) {
		
		// ignore folders
		if (file.isDirectory())
			return;
		
		// if tab is already open, switch to it
		int openIndex = fileIsOpenInTab(file.toPath());		
		if (openIndex != -1) {
			this.setSelectedIndex(openIndex);
		} else { // otherwise load file from disk
			NumberedTextArea createdArea = new NumberedTextArea(file.getName());
			createdArea.filePath = file.toPath();
			this.addTab(file.getName(), createdArea);
			openTabs.add(createdArea);
	
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
	
	// checks if the file to open is already opened in a tab
	private int fileIsOpenInTab(Path path) {
		for (int i = 0; i < openTabs.size(); i++) {
			if (openTabs.get(i).filePath.toString().equals(path.toString())) {
				return i;
			}
		}
		return -1;
	}
}
