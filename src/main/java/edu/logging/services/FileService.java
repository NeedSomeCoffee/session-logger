package edu.logging.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import edu.logging.exceptions.LoggingException;


public class FileService {
	private Path filePath;

	public FileService(String path, String fileName) throws LoggingException {
		try {
			this.filePath = createFileInPath(path, fileName);

		} catch (IOException e) {
			throw new LoggingException("Can't create  file: ", e);
		}
	}

	private Path createFileInPath(String path, String fileName) throws IOException {
		Path declaredPath = Paths.get(".", path + "/" + fileName);

		if (!declaredPath.toFile().exists()) {
			Path directory = Paths.get(path);

			if (!directory.toFile().exists()) {
				Files.createDirectories(directory);
			}

			Files.createFile(declaredPath);
		}

		return declaredPath;
	}

	public void appendMultipleLinesToExistingFile(List<String> lines) throws LoggingException {
		try {
			Files.write(filePath, lines, StandardOpenOption.APPEND);

		} catch (IOException e) {
			throw new LoggingException("Error writing data to file ", e);
		}
	}
	
	
	public void appendSingleLineToExistingFile(String line) throws LoggingException {
		try {
			Files.write(filePath, (line + "\n").getBytes(), StandardOpenOption.APPEND);

		} catch (IOException e) {
			throw new LoggingException("Error writing data to file ", e);
		}
	}
	
	public List<String> getAllDataInFile() throws LoggingException {
		List<String> allData = new ArrayList<>();
		
		try {
			allData = Files.readAllLines(filePath);			
		} catch (IOException e) {
			throw new LoggingException("Error reading data from file: ", e);
		}
		
		return allData;
	}

	public List<String> getDataForPeriod(String from, String to) throws LoggingException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		Instant dateFrom = LocalDateTime.parse(from, formatter).toInstant(ZoneOffset.UTC);
		Instant dateTo = LocalDateTime.parse(to, formatter).toInstant(ZoneOffset.UTC);

		List<String> filteredLines = new ArrayList<>();

		try {
			List<String> lines = Files.readAllLines(filePath);
			for(String line : lines) {
				long timeInMillis = Long.parseLong(line.substring(0, line.indexOf(' ')));
				Instant time = Instant.ofEpochSecond(timeInMillis);

				if(time.isAfter(dateFrom) && time.isBefore(dateTo)) {
					filteredLines.add(line);
				}
			}
		} catch (IOException e) {
			throw new LoggingException("Error reading from file: ", e);
		}

		return filteredLines;
	}

	public void removeDataForPeriod(String from, String to) throws LoggingException {
		List<String> lines = null;

		try {
			lines = Files.readAllLines(filePath);
			List<String> linesToExclude = getDataForPeriod(from, to);

			lines.removeAll(linesToExclude);

			Files.write(filePath, lines);

		} catch (LoggingException | IOException e) {
			throw new LoggingException("Error reading from file: ", e);
		}		
	}
	
	public void removeDataThreeDaysOld() throws LoggingException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm").withZone(ZoneId.systemDefault());
		Instant now = Instant.now();
		Instant threeDaysAgo = now.minus(3, ChronoUnit.DAYS);
		
		
		removeDataForPeriod(formatter.format(threeDaysAgo), formatter.format(now));
	}

	public Path getFilePath() {
		return filePath;
	}
	
	public static void main(String[] args) throws LoggingException {
		FileService srv = new FileService("log", "ololo.txt");
		
		srv.removeDataThreeDaysOld();
		
	}
}
