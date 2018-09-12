package edu.logging.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.logging.exceptions.LoggingException;
import edu.logging.models.Connection;
import edu.logging.services.ConnectionUtils;
import edu.logging.services.FileService;

public class Application {
	public static void main(String[] args) throws LoggingException {
		FileService writer = new FileService("log", "log.txt");

		List<Connection> connections = getConnectionData();

		for(int i = 0; i < 3; i++) {
			LoggerWorker thread = new LoggerWorker(connections, writer);
			thread.start();
		}		
	}

	private static List<Connection> getConnectionData() {		
		List<Connection> connectionsToWrite = new ArrayList<>();

		for(int i = 0; i < 10; i++) {
			String ip = ConnectionUtils.getIpAddress();
			String session = ConnectionUtils.getSessionNumber();
			String timeStamp = ConnectionUtils.getTimeStamp();

			connectionsToWrite.add(new Connection(timeStamp, session, ip));
		}		

		return connectionsToWrite;
	}
}
