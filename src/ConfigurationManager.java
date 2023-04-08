import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ConfigurationManager {	
	public static File openDirectory;
	
	public static String documentsFolderPath = System.getProperty("user.home") +"\\Documents";
	
	private static Path configFilePath = Paths.get(documentsFolderPath + "\\ArabBeans\\config.cfg");
	private static File configFile = configFilePath.toFile();
	
	private static Path configsFolderPath = Paths.get(documentsFolderPath + "\\ArabBeans");
	private static File configsFolder = configsFolderPath.toFile();
	
	public ConfigurationManager() {
		
		//check if config file exists
		if (configsFolder.exists() || configFile.exists()) {
			openDirectory = getSavedDirectory();
			
			// if saved directory is invalid
			if (!openDirectory.exists())
				displayLoadingDirectoryError();
		}
	}

	public static void updateSavedOpenDirectory() {
		List<String> data = new ArrayList<>();
		data.add(openDirectory.toString());
		
		try {
			// if the configs folder doesn't exist then create one
			if (!configsFolder.exists()) {
				System.out.println("Configs folder does not exist, creating...");
				Files.createDirectory(configsFolderPath);
			}
			
			// if the config file doesn't exist then create one
			if (!configFile.exists()) {
				System.out.println("Configs file does not exist, creating...");
				Files.createFile(configFilePath);
			}
			
			// update config
			Files.write(configFilePath, data, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File getSavedDirectory() {
		try {
			List<String> readLines = Files.readAllLines(configFilePath);
			
			if (!readLines.isEmpty()) {
				System.out.println(readLines.get(0));
				return new File(readLines.get(0));
			}
		} catch (IOException e) {
			System.err.println("Failed to get saved directory");
			e.printStackTrace();
		}
		
		// something went wrong
		return new File(documentsFolderPath);
	}
	
	private static void displayLoadingDirectoryError() {
		openDirectory = new File(documentsFolderPath);
		JOptionPane.showMessageDialog(null, "Could not find loaded project", "Loading error", JOptionPane.ERROR_MESSAGE);
	}
}
