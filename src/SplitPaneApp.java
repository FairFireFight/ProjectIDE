import javax.swing.*;
import java.awt.*;

public class SplitPaneApp extends JFrame {
	private static final long serialVersionUID = 1L;
	
	
	//UI elemnts
	public JSplitPane horizontalSplit;
	public JSplitPane verticalSplit;
	
	public JPanel workArea;
	public JPanel hierarchy ;
	public JPanel console;
	
	public SplitPaneApp() {
		//setting up frame
		super("Arab[B]eans IDE");
		
		int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		System.out.println("Resolution [" + screenWidth + ", " + screenHeight + "]");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(screenWidth - 100, screenHeight - 100);
		this.setLocationRelativeTo(null);
		
		//setting up hierarchy
		hierarchy = new JPanel();
		hierarchy.setBackground(new Color(220, 220, 220));
		
		//setting up workArea
		workArea = new JPanel();
		workArea.setBackground(ProjectColors.WORK_AREA_COLOR);
		
		//setting up console
		console = new JPanel();
		console.setBackground(ProjectColors.CONSOLE_COLOR);
		
		SetUpVerticalSplit();
		SetUpHorizontalSplit();
		
		this.add(horizontalSplit);
	}
	
	private void SetUpVerticalSplit() {
		verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verticalSplit.setTopComponent(workArea);
		verticalSplit.setBottomComponent(console);
		verticalSplit.setContinuousLayout(true);
		
		int verticalSplitLocation = (this.getHeight() - 200);
		verticalSplit.setDividerLocation(verticalSplitLocation);
	}
	
	private void SetUpHorizontalSplit() {
		horizontalSplit = new JSplitPane();
		horizontalSplit.setLeftComponent(hierarchy);
		horizontalSplit.setRightComponent(verticalSplit);
		horizontalSplit.setResizeWeight(0.5);
		horizontalSplit.setContinuousLayout(true);
		
		int horizontalSplitLocation = 225;
		horizontalSplit.setDividerLocation(horizontalSplitLocation);
	}
}
