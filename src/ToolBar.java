import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ToolBar extends JPanel{
	JButton[] buttons; //temp test values
	
	ToolBar(){
		super(new FlowLayout(FlowLayout.LEADING));
		
		this.setPreferredSize(new Dimension(getPreferredSize().getSize().width, 50));
		
		//TESTING
		buttons = new JButton[16];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton("" + (i+1));
			buttons[i].setPreferredSize(new Dimension(50, 40));
			buttons[i].setFocusable(false);
			this.add(buttons[i]);
		}
		//END TESTING
	}
}
