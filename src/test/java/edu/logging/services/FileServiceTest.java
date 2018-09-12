package edu.logging.services;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.logging.exceptions.LoggingException;
import edu.logging.models.Connection;

public class FileServiceTest {
	private static FileService fileService;
	
	@Before
	public void prepareLogFile() throws LoggingException {
		fileService = new FileService("log", "testLog.txt");
				
		List<Connection> connections = new ArrayList<>();
		
		Connection connectionOne = new Connection("1535916497", "556476474", "255.255.255.0"); // 09/02/2018
		connections.add(connectionOne);
		Connection connectionTwo = new Connection("1535587200", "556476474", "255.255.255.0"); // 08/30/2018	
		connections.add(connectionTwo);
		Connection connectionThree = new Connection("1535500800", "556476474", "255.255.255.0"); // 08/29/2018	
		connections.add(connectionThree);
		Connection connectionFour = new Connection("1535155200", "556476474", "255.255.255.0"); // 08/25/2018
		connections.add(connectionFour);
		Connection connectionFive = new Connection("1534636800", "556476474", "255.255.255.0"); // 08/19/2018
		connections.add(connectionFive);
		
		fileService.appendMultipleLinesToExistingFile(connections.stream().map(Connection::getConnectionData).collect(Collectors.toList()));
	}
	
	@After
	public void removeLogFile() {
		try {
			Files.deleteIfExists(fileService.getFilePath());
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenFile_WhenParsed_ShouldReturnValidConnectionCountTest() throws LoggingException {
		List<String> selected = fileService.getAllDataInFile();

		assertEquals(5, selected.size());
	}
	
	@Test
	public void givenConnections_WhenSelectedForPeriod_ShouldReturnValidTest() throws LoggingException {
		List<String> selected = fileService.getDataForPeriod("20/08/18 00:00", "31/08/18 00:00");

		assertEquals(3, selected.size());
	}
	
	
	@Test
	public void givenConnections_WhenRemovedForPeriod_ShouldReturnValidTest() throws LoggingException {
		fileService.removeDataForPeriod("27/08/18 00:00", "31/08/18 00:00");
		
		List<String> remaining = fileService.getAllDataInFile();
		
		assertEquals(3, remaining.size());
	}
}
