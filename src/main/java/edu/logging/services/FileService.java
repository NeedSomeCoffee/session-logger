package edu.logging.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

	public void appendLinesToExistingFile(List<String> lines) {
		try {
			Files.write(filePath, lines, StandardOpenOption.APPEND);

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error writing data to file: ", e);
		}
	}

	public List<String> getDataForPeriod(String from, String to) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		long dateFromInMillis = LocalDateTime.parse(from, formatter).toInstant(ZoneOffset.UTC).toEpochMilli();
		long dateToInMillis = LocalDateTime.parse(to, formatter).toInstant(ZoneOffset.UTC).toEpochMilli();

		List<String> lines = null;

		try {
			lines = Files.readAllLines(filePath);
			lines = lines.stream().filter(L -> {
				long time = Long.parseLong(L.substring(0, L.indexOf(' ') + 1));

				return (time > dateFromInMillis && time < dateToInMillis);

			}).collect(Collectors.toList());

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error reading from file: ", e);
		}

		return lines;
	}
	
	public void removeDataForPeriod(String from, String to) {
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(filePath);
			List<String> linesToExclude = getDataForPeriod(from, to);
			
			lines.removeAll(linesToExclude);
			
			Files.write(filePath, lines, StandardOpenOption.WRITE);
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error reading from file: ", e);
		}		
	}

	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		LocalDateTime date = LocalDateTime.parse("20/10/17 12:42", formatter);

		System.out.println(date.toInstant(ZoneOffset.UTC).toEpochMilli());
	}
}
