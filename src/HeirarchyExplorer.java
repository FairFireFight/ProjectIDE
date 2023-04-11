import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
	public static JPanel header;
	public static JLabel label;

	// drop down menu
	public static JPopupMenu dropDownMenu;

	// drop down -> new menu
	public static JMenu newMenu;
	public static JMenuItem newClassButton;
	public static JMenuItem newFolder;
	public static JMenuItem openInExplorer;
	public static JMenuItem renameFile;
	
	public static JMenuItem refreshButton;
	public static JMenuItem removeButton;
	
	public static TreePath path;

	public HeirarchyExplorer() {
		super(new BorderLayout());

		// tree setup
		treeModel = new DefaultTreeModel(new DefaultMutableTreeNode());
		fileTree = new JTree(treeModel);
		fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		fileTree.setShowsRootHandles(false);
		
		// tree controls
		fileTree.addMouseListener(new MouseAdapter() {
			
			// get reference to clicked node
			@Override
			public void mouseClicked(MouseEvent e) {
				if (fileTree.getPathForLocation(e.getX(), e.getY()) != null) {
					path = fileTree.getPathForLocation(e.getX(), e.getY());
				}
				
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					SplitPaneApp.workAreaTabs.openFile(new File(treePathToAbsoluteURI(path)));
				}
				
				// handle selection, make sure it is not null
				if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) {
					fileTree.setSelectionPath(path);
				}

				// drop down menu handler
				if (e.getButton() == MouseEvent.BUTTON3) {
					if (fileTree.getSelectionPath() == null)
						return;

					dropDownMenu.show(e.getComponent(), e.getX(), e.getY());
				}
				
			}
		});

		// scroll pane
		treeScrollPane = new JScrollPane(fileTree);
		treeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// header stuff
		label = new JLabel(" Project Explorer");
		label.setFont(ProjectConstants.GENERAL_FONT);

		header = new JPanel(new BorderLayout());
		header.add(label, BorderLayout.WEST);
		
		//drop down menu stuff
		openInExplorer = new JMenuItem("Show in explorer");
		openInExplorer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					File fileToOpen = new File(treePathToAbsoluteURI(path));				
					
					// if the selected node is not a folder 
					// then open the parent folder
					if (!fileToOpen.isDirectory()) {
						fileToOpen = new File(getParentDirectory(treePathToAbsoluteURI(path)));
					}
						
		            Desktop.getDesktop().open(fileToOpen);
		        } catch (IOException ex) {
		            JOptionPane.showMessageDialog(null,
		            		"An error occured trying to open this file (are you sure it exists?)",
		            		"Error!",
		            		JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
		        }
			}
		});
		
		CreateNewHandler newHandler = new CreateNewHandler();
		
		removeButton = new JMenuItem("Delete");
		removeButton.addActionListener(new DeleteHandler());
		
		renameFile = new JMenuItem("Rename");
		renameFile.addActionListener(newHandler);
		
		refreshButton = new JMenuItem("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadProjectHandler.rebuildTree();
			}
		});
		
		newClassButton = new JMenuItem("Java Class");
		newClassButton.addActionListener(newHandler);
		
		newFolder = new JMenuItem("Folder");
		newFolder.addActionListener(newHandler);
		
		newMenu = new JMenu("New");
		newMenu.add(newFolder);
		newMenu.add(newClassButton);
		
		dropDownMenu = new JPopupMenu();
		dropDownMenu.add(newMenu);
		dropDownMenu.addSeparator();
		dropDownMenu.add(openInExplorer);
		dropDownMenu.add(refreshButton);
		dropDownMenu.addSeparator();
		dropDownMenu.add(renameFile);
		dropDownMenu.add(removeButton);
		
		// adds
		add(header, BorderLayout.NORTH);
		add(treeScrollPane, BorderLayout.CENTER);
	}

	/**
	 * converts the given path into an absolute URI
	 */
	public static String treePathToAbsoluteURI(TreePath path) {
		String URI = ConfigurationManager.openDirectory.toString();
		Object[] pathArray = path.getPath();

		for (int i = 1; i < pathArray.length; i++)
			URI += "\\" + pathArray[i];

		return URI;
	}
	
	/**
	 * Returns a new String of the specified path but excluding the last directory
	 */
	public static String getParentDirectory(String path) {
		int indexToLast = path.lastIndexOf("\\");
		String newPath = path.substring(0, indexToLast);
		return newPath;
	}
	
	public static String getSelectedNodeDirectory() {
		if (path == null) {
			return null;
		} else {
			return treePathToAbsoluteURI(path);
		}
	}
	
	public static void removeSelectedNode() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
        if (selectedNode != null) {
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
            if (parentNode != null) {
                parentNode.remove(selectedNode);
                fileTree.updateUI();
                path = null;
            }
        }
    }
}