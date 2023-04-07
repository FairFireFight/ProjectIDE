import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class WorkArea extends JPanel{

	public JTextArea textArea;
	public JTextArea lineNumbers;
	
	public WorkArea() {
		super(new BorderLayout());
		
		//Actual TextArea
		textArea = new JTextArea();
		textArea.setFont(ProjectConstants.TEXT_AREA_FONT);
		textArea.setTabSize(2);
		textArea.setBackground(ProjectConstants.WORK_AREA_COLOR);
		textArea.setBorder(BorderFactory.createMatteBorder(3, 5, 0, 0, ProjectConstants.WORK_AREA_COLOR));
		
		
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				UpdateLineNumbers();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateLineNumbers();
			}

			@Override //unused method
			public void changedUpdate(DocumentEvent e) {}
		});
		
		//line numbering
		lineNumbers = new JTextArea("1\n2");
		lineNumbers.setSize(new Dimension(25, lineNumbers.getPreferredSize().getSize().height));
		lineNumbers.setMaximumSize(new Dimension(60, lineNumbers.getPreferredSize().getSize().height));
		lineNumbers.setFocusable(false);
		lineNumbers.setFont(ProjectConstants.TEXT_AREA_FONT);
		lineNumbers.setEditable(false);
		lineNumbers.setBackground(ProjectConstants.LINE_NUMBER_COLOR);
		lineNumbers.setBorder(BorderFactory.createMatteBorder(3, 9, 0, 4, ProjectConstants.LINE_NUMBER_COLOR));	

		add(lineNumbers, BorderLayout.WEST);
		add(textArea, BorderLayout.CENTER);
	}
	
	public String getText() {
		return textArea.getText();
	}
	
	private void UpdateLineNumbers() {
		String numbers = "";
		int numberOfLines = textArea.getLineCount() + 1;
		
		for (int i = 1; i <= numberOfLines; i++)
			numbers += i + "\n";
		
		lineNumbers.setText(numbers);
	}
}