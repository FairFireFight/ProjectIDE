import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

@SuppressWarnings("serial")
public class HeirarchyMainPanel extends JPanel {
	JScrollPane treeScrollPane;

	// the tree
	public static JTree fileTree;
	public static DefaultTreeModel treeModel;
	public static DefaultMutableTreeNode root;

	// header
	JPanel header;
	JLabel label;

	public HeirarchyMainPanel() {
		super(new BorderLayout());

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
		treeModel = new DefaultTreeModel(rootNode);
		fileTree = new JTree(treeModel);
		fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		fileTree.setShowsRootHandles(true);

		treeScrollPane = new JScrollPane(fileTree);
		treeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		header = new JPanel(new BorderLayout());
		label = new JLabel(" Project Explorer");
		label.setFont(ProjectConstants.GENERAL_FONT);

		header.add(label, BorderLayout.WEST);

		add(header, BorderLayout.NORTH);
		add(treeScrollPane, BorderLayout.CENTER);
	}
}

class loadProjectHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		loadProjectTree();
	}

	private static void loadProjectTree() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();

			DefaultMutableTreeNode root = new DefaultMutableTreeNode(fileChooser.getSelectedFile().getName());
			buildFileStructureTree(root, file);
			HeirarchyMainPanel.treeModel.setRoot(root);

		}
	}

	private static void buildFileStructureTree(DefaultMutableTreeNode parentNode, File directory) {
		// confirmed this works
		File[] contents = directory.listFiles();

		for (File file : contents) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(file.getName());

			parentNode.add(node);

			if (file.isDirectory()) {
				buildFileStructureTree(node, file);
			}
		}
	}
}
