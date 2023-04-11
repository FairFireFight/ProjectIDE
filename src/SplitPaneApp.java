import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class SplitPaneApp extends JFrame {
	
	//UI elements
	public JSplitPane horizontalSplit;
	public JSplitPane verticalSplit;
	
	public JScrollPane workArea;
	public JPanel hierarchy ;
	public JPanel console;
	
	//containers
	public JPanel appPanel;
	
	//user extended elements
	public static WorkAreaTabs workAreaTabs;
	
	public SplitPaneApp() {
		super("Arab[B]eans IDE");
		InitializeFrame();	
		
		appPanel = new JPanel(new BorderLayout());
		
		//setting up hierarchy
		hierarchy = new JPanel(new BorderLayout());
		hierarchy.setBackground(ProjectConstants.CONSOLE_COLOR);
		
		hierarchy.add(new HeirarchyExplorer(), BorderLayout.CENTER);
		
		//setting up workArea		
		workAreaTabs = new WorkAreaTabs();
		workArea = new JScrollPane(workAreaTabs);
		workArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		workArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		workArea.getVerticalScrollBar().setUnitIncrement(12);
		workArea.getHorizontalScrollBar().setUnitIncrement(12);
		
		//setting up console
		console = new JPanel();
		console.setBackground(ProjectConstants.CONSOLE_COLOR);
		
		InitializeVerticalSplit(workArea, console);
		InitializeHorizontalSplit(hierarchy, verticalSplit);
		
		appPanel.add(new ToolBar(), BorderLayout.NORTH);
		appPanel.add(horizontalSplit, BorderLayout.CENTER);
		
		this.add(appPanel);
		this.setJMenuBar(new ControlsMenuBar());
	}
	
	private void InitializeFrame() {
		int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		//System.out.println("Resolution [" + screenWidth + ", " + screenHeight + "]");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setSize(screenWidth - 100, screenHeight - 100);
		this.setMinimumSize(new Dimension(900, 650));
		this.setLocationRelativeTo(null);
	}
	
	private void InitializeVerticalSplit(Component topComponent, Component bottomComponent) {
		verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verticalSplit.setTopComponent(topComponent);
		verticalSplit.setBottomComponent(bottomComponent);
		verticalSplit.setContinuousLayout(true);
		verticalSplit.setResizeWeight(0.5);
		
		int verticalSplitLocation = 250;
		verticalSplit.setDividerLocation(this.getHeight() - verticalSplitLocation);
	}
	
	private void InitializeHorizontalSplit(Component leftComponent, Component rightComponent) {
		horizontalSplit = new JSplitPane();
		horizontalSplit.setLeftComponent(leftComponent);
		horizontalSplit.setRightComponent(rightComponent);
		horizontalSplit.setResizeWeight(0.5);
		horizontalSplit.setContinuousLayout(true);
		
		int horizontalSplitLocation = 270;
		horizontalSplit.setDividerLocation(horizontalSplitLocation);
	}
}
