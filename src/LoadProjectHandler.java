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
			ConfigurationManager.openDirectory = fileChooser.getSelectedFile();
			loadProjectTree(ConfigurationManager.openDirectory);
			ConfigurationManager.updateSavedOpenDirectory();
		}
	}
	
	public static void loadProjectTree(File file) {
			if (file == null) {
				loadProjectTree(new File(ConfigurationManager.documentsFolderPath));
				return;
			}
		
			DefaultMutableTreeNode root;
			root = new DefaultMutableTreeNode(file.getName());
			
			buildFileStructureTree(root, file);
			HeirarchyExplorer.treeModel.setRoot(root);
			
			MainClass.updateTitle(file + " - Arab[B]eans IDE");
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
		
		if (contents == null) {
			System.err.println("failed to open directory " + directory);
			return;
		}
		
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
	
	//FIXME might be bugged in the future
	public static void rebuildTree() {
		loadProjectTree(ConfigurationManager.openDirectory);
		System.out.println("Rebuilt the tree");
	}
}