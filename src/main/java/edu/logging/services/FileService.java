package edu.logging.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileService {
	private Logger logger = Logger.getLogger("FileService");
	private Path filePath;
		
	public FileService(String path, String fileName) {
		try {			
			this.filePath = createFileInPath(path, fileName);
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Can't create  file: ", e);
		} 
	}
	
	private Path createFileInPath(String path, String fileName) throws IOException {
		Path declaredPath = Paths.get(".", path + "/"+ fileName);
		
		if(Files.notExists(declaredPath)) {
			Path directory = Paths.get(path);
			
			if(Files.notExists(directory)) {
				Files.createDirectories(directory);
			}			
			
			Files.createFile(declaredPath);
		}		
		
		return declaredPath;
	}
	
	public void appendLinesToExistingFile(List<String> lines) {
		try {
		    Files.write(filePath, lines, StandardOpenOption.APPEND);
		    
		}catch (IOException e) {
			logger.log(Level.SEVERE, "Error writing data to file: ", e);
		}
	}
	
	public List<String> readDataForPeriod() {
		List<String> lines = null;
		
		//TODO implement filtering data for period
		try {
			lines = Files.readAllLines(filePath);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error reading from file: ", e);
		}
		
		return lines;
	}
	
	/*public static void main(String[] args) {
		FileService srv = new FileService("log", "logger.txt");
		
		List<String> lines = Arrays.asList("dsfsdf", "sdfsdf", "skdfjkjsdf", "skdjfhsdkjf");
		
		srv.appendLinesToExistingFile(lines);
	}*/
}
