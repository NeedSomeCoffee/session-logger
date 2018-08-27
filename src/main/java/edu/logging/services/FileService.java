package edu.logging.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FileService {
	private Logger logger = Logger.getLogger("FileService");
	private Path filePath;
		
	public FileService(String filePath) {
		try {
			Path declaredPath = Paths.get(".", filePath);
			
			if(Files.notExists(declaredPath)) {
				//TODO create directory!!!!
				Files.createDirectories(declaredPath);
				Files.createFile(declaredPath);
			}
			
			this.filePath = declaredPath;
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Can't create  file: ", e);
		} 
	}
	
	public static void main(String[] args) {
		FileService srv = new FileService("./log/logger.txt");
	}
}
