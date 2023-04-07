import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;


public class LoadProjectHandler implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		// load class procedures
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = fileChooser.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			HeirarchyExplorer.openDirectory = fileChooser.getSelectedFile();
			loadProjectTree(HeirarchyExplorer.openDirectory);
		}
	}
	
	public static void loadProjectTree(File file) {
			DefaultMutableTreeNode root;
			root = new DefaultMutableTreeNode(file.getName());
			
			buildFileStructureTree(root, file);
			HeirarchyExplorer.treeModel.setRoot(root);
			
			MainClass.updateTitle(file + "- Arab[B]eans IDE");
	}
	
	/**
	 * Builds a file structure tree from the specified directory upon the specified node
	 * @parentNode
	 * The node to build on
	 * @directory
	 * The directory to build
	 * */
	private static void buildFileStructureTree(DefaultMutableTreeNode parentNode, File directory) {
		File[] contents = directory.listFiles();

		for (File file : contents) {
			DefaultMutableTreeNode node; 
			node = new DefaultMutableTreeNode(file.getName());
			
			if (!file.isHidden()) {
				parentNode.add(node);
			}
			if (file.isDirectory()) {
				buildFileStructureTree(node, file);
			}
		}
	}
}
