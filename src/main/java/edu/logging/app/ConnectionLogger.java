package edu.logging.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.logging.models.Connection;
import edu.logging.services.ConnectionUtils;
import edu.logging.services.FileService;

public class ConnectionLogger {
	
	public void writeConnectionToLog (List<Connection> connections) {
		FileService writer = new FileService("log", "log.txt");
		
		List<String> connectionsData = connections.stream().map(Connection::getConnectionData).collect(Collectors.toList());
		
		writer.appendLinesToExistingFile(connectionsData);
	}
	
	public static void main(String[] args) {
		ConnectionLogger logger = new ConnectionLogger();
		
		List<Connection> con = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			String ip = ConnectionUtils.getIpAddress();
			String session = ConnectionUtils.getSessionNumber();

			
			con.add(new Connection(session, ip));
		}
		
		logger.writeConnectionToLog(con);
	}
	
}
