import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class ToolBar extends JPanel{
	JToggleButton[] buttons; //temp test values
	
	ToolBar(){
		super(new FlowLayout(FlowLayout.LEADING));
		
		//this.setPreferredSize(new Dimension(getPreferredSize().getSize().width, 50));
		
		//TESTING
		buttons = new JToggleButton[24];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JToggleButton("" + (i+1));
			buttons[i].setPreferredSize(new Dimension(50, 40));
			buttons[i].setFocusable(false);
			this.add(buttons[i]);
		}
	}
}
