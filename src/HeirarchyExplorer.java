import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

@SuppressWarnings("serial")
public class HeirarchyExplorer extends JPanel {
	JScrollPane treeScrollPane;

	// the tree
	public static JTree fileTree;
	public static DefaultTreeModel treeModel;

	// header
	JPanel header;
	JLabel label;
	
	//drop down menu
	JPopupMenu dropDownMenu;
	
	// drop down -> new menu
	JMenu newMenu;
	JMenuItem newClassButton;
	JMenuItem newFolder;
	JMenuItem openInExplorer;
	
	JMenuItem removeButton;
	
	//temporary to automatically open this directory
	public static File openDirectory = new File("C:\\Users\\sulai\\Desktop\\FakeProject");

	public HeirarchyExplorer() {
		super(new BorderLayout());

		// tree setup
		treeModel = new DefaultTreeModel(new DefaultMutableTreeNode());
		fileTree = new JTree(treeModel);
		fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		fileTree.setShowsRootHandles(false);
		fileTree.setEditable(getFocusTraversalKeysEnabled());

		// tree controls
		fileTree.addMouseListener(new MouseAdapter() {
			// get reference to clicked node
			public void mouseClicked(MouseEvent e) {
				TreePath path = fileTree.getPathForLocation(e.getX(), e.getY());
				
				@SuppressWarnings("unused")
				DefaultMutableTreeNode node;
				
				if (path != null)
					node = (DefaultMutableTreeNode) path.getLastPathComponent();

				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					if (path != null) {
						System.out.println(treePathToURI(path));
					}
				}
				
				if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) {
					fileTree.setSelectionPath(path);
				}
				
				// TODO dropdown menu for selected node
				if (e.getButton() == MouseEvent.BUTTON3) {
					fileTree.setSelectionPath(path);
				}
			}
		});

		// scroll pane
		treeScrollPane = new JScrollPane(fileTree);
		treeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		//header stuff
		label = new JLabel(" Project Explorer");
		label.setFont(ProjectConstants.GENERAL_FONT);
		
		header = new JPanel(new BorderLayout());
		header.add(label, BorderLayout.WEST);

		//adds
		add(header, BorderLayout.NORTH);
		add(treeScrollPane, BorderLayout.CENTER);
	}
	
	private static String treePathToURI(TreePath path) {
		String URI = "";
		Object[] pathArray = path.getPath();
		
		for(Object p : pathArray) {
			URI += "\\" + p;
		}
		return URI;
	}
	
	private void collapseAllNodes(JTree tree) {
	    int rowCount = tree.getRowCount();
	    for (int i = rowCount - 1; i >= 1; i--) {
	        tree.collapseRow(i);
	    }
	}
}