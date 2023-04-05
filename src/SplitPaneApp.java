import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class SplitPaneApp extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//UI elements
	public JSplitPane horizontalSplit;
	public JSplitPane verticalSplit;
	
	public JScrollPane workArea;
	public JPanel hierarchy ;
	public JPanel console;
	
	public JScrollPane textFieldScroll;
	public JTextArea textArea;
	public JLabel lineNumbers;
	public JPanel workAreaPanel;
	
	public SplitPaneApp() {
		super("Arab[B]eans IDE");
		InitializeFrame();	
		
		//setting up hierarchy
		hierarchy = new JPanel();
		hierarchy.setBackground(new Color(220, 220, 220));
		
		//setting up workArea		
		workArea = new JScrollPane(new WorkArea());
		workArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		workArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		workArea.getVerticalScrollBar().setUnitIncrement(12);
		workArea.getHorizontalScrollBar().setUnitIncrement(12);
		
		//setting up console
		console = new JPanel();
		console.setBackground(ProjectConstants.CONSOLE_COLOR);
		
		InitializeVerticalSplit(workArea, console);
		InitializeHorizontalSplit(hierarchy, verticalSplit);
		
		this.add(horizontalSplit);
	}
	
	private void InitializeFrame() {
		int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		//System.out.println("Resolution [" + screenWidth + ", " + screenHeight + "]");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(screenWidth - 100, screenHeight - 100);
		this.setLocationRelativeTo(null);
	}
	
	private void InitializeVerticalSplit(Component topComponent, Component bottomComponent) {
		verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verticalSplit.setTopComponent(topComponent);
		verticalSplit.setBottomComponent(bottomComponent);
		verticalSplit.setContinuousLayout(true);
		
		int verticalSplitLocation = 200;
		verticalSplit.setDividerLocation(this.getHeight() - verticalSplitLocation);
	}
	
	private void InitializeHorizontalSplit(Component leftComponent, Component rightComponent) {
		horizontalSplit = new JSplitPane();
		horizontalSplit.setLeftComponent(leftComponent);
		horizontalSplit.setRightComponent(rightComponent);
		horizontalSplit.setResizeWeight(0.5);
		horizontalSplit.setContinuousLayout(true);
		
		int horizontalSplitLocation = 225;
		horizontalSplit.setDividerLocation(horizontalSplitLocation);
	}
}
