import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

public class DeleteHandler implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		File directoryToDelete = new File(HeirarchyExplorer.getSelectedNodeDirectory()); 
		
		// this might get triggered if user deletes file from outside the app
		if (!directoryToDelete.exists()) {
			JOptionPane.showMessageDialog(null, "This file doesn't exist, please refresh the explorer", "File Doesn't Exist", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (getConfirmation()) {
			deleteDirectory(directoryToDelete);
			
			// checking if it is the root folder
			if (directoryToDelete.toString().equals(ConfigurationManager.getSavedDirectory().toString())) {
				ConfigurationManager.loadDefaultFolder();
			}
			
			HeirarchyExplorer.removeSelectedNode();
			System.out.println("Successfully deleted " + directoryToDelete);
		}
		else {
			System.err.println("User cancelled deleting");
		}
	}
	
	private static void deleteDirectory(File directory) {
		// validation
		if (directory.exists()) { 
			// if file is directory then recurse
			if (directory.isDirectory()) {
				File[] files = directory.listFiles();
				// empty directory validation
				if (files != null) { 
					for(File file : files) {
						deleteDirectory(file);
					}
				}
			}
			// finally delete the directory
			directory.delete();
		} else {
			System.err.println("Directory doesn't exist");
		}
	}
	
	private static boolean getConfirmation() {
		int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this file?", "Delete File", JOptionPane.YES_NO_OPTION);
		
		if (response == JOptionPane.OK_OPTION)
			return true;
		else
			return false;
	}
}
