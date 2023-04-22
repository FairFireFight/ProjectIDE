import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class Console extends JPanel{
	public static JTextArea messageArea; //no write textarea
	public static JTextArea textArea;
	
	public static JPanel textPanel;
	
	public static JScrollPane scrollPane;
	
	private static DocumentListener listener ;
	
	public Console() {
		super(new GridLayout(1,1));
		
		messageArea = new JTextArea();
		messageArea.setEditable(false);
		messageArea.setBackground(ProjectConstants.CONSOLE_COLOR);
		messageArea.setFont(ProjectConstants.CONSOLE_FONT);
		
		listener = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (newLineAdded()) {
					executeCommand();
				}
			}

			// unused methods
			public void removeUpdate(DocumentEvent e) {
			}

			public void changedUpdate(DocumentEvent e) {
			}
		};
		
		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(getPreferredSize().width, 30));
		textArea.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.WHITE));
		textArea.setBackground(ProjectConstants.CONSOLE_COLOR);
		textArea.setFont(ProjectConstants.CONSOLE_FONT);
		textArea.getDocument().addDocumentListener(listener);
		
		textPanel = new JPanel(new BorderLayout());
		
		
		scrollPane = new JScrollPane(messageArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		textPanel.add(scrollPane, BorderLayout.CENTER);
		textPanel.add(textArea, BorderLayout.SOUTH);

		this.add(textPanel);
	}
	
	
	
	
	private static boolean  newLineAdded() {
		if (textArea.getLineCount() > 1) {
			return true;
		}
		return false;
	}
	
	
	// TODO make this functional, nothing worky worky :(
	private static void executeCommand(){
		String command = textArea.getText();
		
		messageArea.append(command);
		
		//clear text area after
		textArea.getDocument().removeDocumentListener(listener);
		SwingUtilities.invokeLater(() -> {
			textArea.setText("");
			textArea.getDocument().addDocumentListener(listener);
		});
		
		//special case
				if (command.equals("cls\n")) {
					messageArea.setText("");
					return;
				}
		
		Process process;
		try {
			ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
			process = processBuilder.start();

			//*
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				messageArea.append(line + "\n");
				System.out.print(".");
			}
			
			/**/
		} catch (IOException e1) {
			messageArea.append(e1.toString());
		}
	}
}
