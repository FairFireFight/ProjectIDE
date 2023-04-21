import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A complete JTextArea with line numbers, ScrollBars and remembers if the file was modified in a variable.
 * @author Sulaiman
 */
@SuppressWarnings("serial")
public class NumberedTextArea extends JPanel{

	public JTextArea textArea;
	public JTextArea lineNumbers;
	public String name;
	public Path filePath;
	public boolean unsavedChanges = false;
	
	public NumberedTextArea(String name) {
		super(new BorderLayout());
		
		this.name = name;
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
				unsavedChanges = true;
				SplitPaneApp.workAreaTabs.setUnsavedChangesMark(unsavedChanges);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateLineNumbers();
				unsavedChanges = true;
				SplitPaneApp.workAreaTabs.setUnsavedChangesMark(unsavedChanges);
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
	
	public void saveText() {
		byte[] textBytes = getText().getBytes();
		
		try {
			System.out.println("Saving file " + filePath);
			
			Files.write(filePath, textBytes);
			
			unsavedChanges = false;
			SplitPaneApp.workAreaTabs.setUnsavedChangesMark(unsavedChanges);

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to save file", "Save error", JOptionPane.ERROR_MESSAGE);
			System.err.println("Failed to save " + filePath);
			e.printStackTrace();
		}
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