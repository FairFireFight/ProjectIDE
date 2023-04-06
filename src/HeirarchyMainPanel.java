import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class HeirarchyMainPanel extends JPanel{
	JScrollPane treeScrollPane;
	
	JTree tree;
	DefaultMutableTreeNode treeRoot;
	
	public HeirarchyMainPanel(){
		super(new BorderLayout());
		
		
		treeRoot = new DefaultMutableTreeNode("Project");
		for (int i =1; i <= 8; i++) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode("folder" + i);
			for (int j =1; j <= 5; j++) {
				node.add(new DefaultMutableTreeNode("file" + j));
			}
			treeRoot.add(node);
		}
		
		tree = new JTree(treeRoot);
		treeScrollPane = new JScrollPane(tree);
		add(treeScrollPane, BorderLayout.CENTER);
	}
		
}
