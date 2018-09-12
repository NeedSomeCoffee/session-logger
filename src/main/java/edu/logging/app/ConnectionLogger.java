package edu.logging.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.logging.exceptions.LoggingException;
import edu.logging.models.Connection;
import edu.logging.services.FileService;

public class ConnectionLogger {
	private FileService writer;

	public ConnectionLogger(FileService writer) throws LoggingException {
		//this.writer = new FileService("log", "log.txt");
		this.writer = writer;
	}

	public void writeConnectionToLog (Connection connection) throws LoggingException {		
		writer.appendSingleLineToExistingFile(connection.getConnectionData());
	}

	public void writeConnectionsToLog (List<Connection> connections) throws LoggingException {		
		List<String> connectionsData = connections.stream().map(Connection::getConnectionData).collect(Collectors.toList());

		writer.appendMultipleLinesToExistingFile(connectionsData);
	}

	public List<Connection> getLogsForPeriod(String from, String to) throws LoggingException {
		List<String> rawLines = writer.getDataForPeriod(from, to);
		List<Connection> parsedConnections = new ArrayList<>();

		for(String rawConnection : rawLines) {
			String[] connectionData = rawConnection.split(" ");

			String timeStamp = connectionData[0];
			String session = connectionData[1];
			String ipAddress = connectionData[2];

			parsedConnections.add(new Connection(timeStamp, session, ipAddress));
		}

		return parsedConnections;
	}

	public void clearLogsForThreeDays() throws LoggingException {
		writer.removeDataThreeDaysOld();
	}
}
