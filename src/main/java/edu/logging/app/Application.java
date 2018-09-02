package edu.logging.app;

import java.util.ArrayList;
import java.util.List;

import edu.logging.exceptions.LoggingException;
import edu.logging.models.Connection;
import edu.logging.services.ConnectionUtils;

public class Application {
	public static void main(String[] args) throws LoggingException {
		ConnectionLogger logger = new ConnectionLogger();

		List<Connection> connectionsToWrite = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			String ip = ConnectionUtils.getIpAddress();
			String session = ConnectionUtils.getSessionNumber();
			String timeStamp = ConnectionUtils.getTimeStamp();

			connectionsToWrite.add(new Connection(timeStamp, session, ip));
		}

		logger.writeConnectionToLog(connectionsToWrite);
		
		logger.clearLogsForThreeDays();
		
		List<Connection> parsedConnections = logger.getLogsForPeriod("20/08/18 00:00", "31/08/18 00:00");
	}
}
