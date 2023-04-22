import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

public class CreateNewHandler implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ControlsMenuBar.newProject) {
			//TODO creating new project folder goes here
			System.out.println("Pretend I made a new project!");
		}
		
		// creating new .java files handler
		if (e.getSource() == ControlsMenuBar.newClass || e.getSource() == HeirarchyExplorer.newClassButton) {
			createNewJavaFile();
		}
		
		if (e.getSource() == ControlsMenuBar.newFolder || e.getSource() == HeirarchyExplorer.newFolder) {
			createNewFolder();
		}
		
		if (e.getSource() == HeirarchyExplorer.renameFile) {
			renameSelectedFile();
		}
	}
	
	private static String findFreeName(File directory, String fileName, String extention) {
		int num = 1;
        String availableName;
        File file;

        do {
            availableName = fileName + num + extention;
            file = new File(directory.toString() + "\\" + availableName);
            num++;
        } while (file.exists());

        return "\\" + availableName;
	}
	
	public  static void createNewJavaFile() {
		File selectedFile;
		
		// validation
		if (HeirarchyExplorer.getSelectedNodeDirectory() == null) {
			JOptionPane.showMessageDialog(null, "Please select a folder", "No Folder Selected", JOptionPane.ERROR_MESSAGE);
		} else {
			
			// everything about creating new file starts here
			selectedFile = new File(HeirarchyExplorer.getSelectedNodeDirectory());
			DefaultMutableTreeNode lastComponent = (DefaultMutableTreeNode) HeirarchyExplorer.path.getLastPathComponent();
			
			// if not a directory then get the parent node
			if (!selectedFile.isDirectory()) {
				selectedFile = new File(HeirarchyExplorer.getParentDirectory(HeirarchyExplorer.getSelectedNodeDirectory()));
				lastComponent = (DefaultMutableTreeNode) lastComponent.getParent();
			}
			
			//if the file already exists then find a new name for it
			String className = JOptionPane.showInputDialog(null, "Class name: ", "Create new Java Class", JOptionPane.PLAIN_MESSAGE);
			
			if (new File(selectedFile.toString() + "\\" + className + ".java").exists()) {
				selectedFile = new File(selectedFile.toString() + findFreeName(selectedFile, className, ".java"));
			}
			else {
				selectedFile = new File(selectedFile.toString() + "\\" + className +".java");
			}
			
			Path newFile = Paths.get(selectedFile.toString());
			
			// default text
			List<String> defaultText = new ArrayList<>();
			defaultText.add("\npublic class " + className + " {\n\n}\n");
			
			try {
				selectedFile.createNewFile();
				
				Files.write(newFile, defaultText, StandardCharsets.UTF_8);
				System.out.println("Created new Java Class: " + selectedFile);
				
				SplitPaneApp.workAreaTabs.openFile(newFile.toFile());
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				lastComponent.add(new DefaultMutableTreeNode(selectedFile.getName()));
				HeirarchyExplorer.fileTree.updateUI();
			}
		}
	}

	public static void createNewFolder() {
		File selectedFile;
		
		// validation
		if (HeirarchyExplorer.getSelectedNodeDirectory() == null) {
			JOptionPane.showMessageDialog(null, "Please select a folder", "No Folder Selected", JOptionPane.ERROR_MESSAGE);
		} else {
			// everything about creating new file starts here
					
			selectedFile = new File(HeirarchyExplorer.getSelectedNodeDirectory());
			DefaultMutableTreeNode lastComponent = (DefaultMutableTreeNode) HeirarchyExplorer.path.getLastPathComponent();
			
			// if not a directory then get the parent node
			if (!selectedFile.isDirectory()) {
				selectedFile = new File(HeirarchyExplorer.getParentDirectory(HeirarchyExplorer.getSelectedNodeDirectory()));
				lastComponent = (DefaultMutableTreeNode) lastComponent.getParent();
			}
					
			String folderName = JOptionPane.showInputDialog(null, "Folder name: ", "Create new Folder", JOptionPane.PLAIN_MESSAGE);
			
			//if the file already exists then find a new name for it
			if (new File(selectedFile.toString() + "\\" + folderName).exists()) {
				selectedFile = new File(selectedFile.toString() + findFreeName(selectedFile, folderName, ""));
			}
			else {
				selectedFile = new File(selectedFile.toString() + "\\" + folderName);
			}
			
			Path newFile = Paths.get(selectedFile.toString());
		
			try {
				Files.createDirectory(newFile);
				System.out.println("Created new folder: " + selectedFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				lastComponent.add(new DefaultMutableTreeNode(selectedFile.getName()));
				HeirarchyExplorer.fileTree.updateUI();
			}
		}
	}
	
	
	/**
	 *  FIXME 
	 *  1 - open a file in the editor
	 *  2 - rename the file
	 *  3 - save open file in editor
	 *  4 - it will create a new file rather than writing to the new name
	 *  
	 *  fix this unintended behaviour
	 */
	public static void renameSelectedFile() {
		String selectedFileURI = HeirarchyExplorer.getSelectedNodeDirectory();
		String selectedFileParentURI = HeirarchyExplorer.getParentDirectory(selectedFileURI);
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) HeirarchyExplorer.path.getLastPathComponent();
		
		// checking if it is the root folder
		if (selectedFileURI.toString().equals(ConfigurationManager.getSavedDirectory().toString())) {
				JOptionPane.showMessageDialog(null, "Renaming the project folder is not allowed", "Can't rename project folder", JOptionPane.ERROR_MESSAGE);
				return;
		}
		
		String newName = JOptionPane.showInputDialog(null, "Rename file to:", "Rename", JOptionPane.PLAIN_MESSAGE);
		
		if (newName == null)
			return;
		
		File selectedFile = new File(selectedFileURI);
		String fileExtention;
		if (selectedFile.isDirectory()) {
			fileExtention = "";
		} 
		else {
			fileExtention = selectedFileURI.substring(selectedFileURI.indexOf('.'));
		}
		selectedFile.renameTo(new File(selectedFileParentURI + "\\" + newName + fileExtention));
		
		// update node
		selectedNode.setUserObject(newName+ fileExtention);
		HeirarchyExplorer.fileTree.updateUI();
	}
}
