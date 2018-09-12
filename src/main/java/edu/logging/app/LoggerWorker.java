package edu.logging.app;


import java.util.List;
import java.util.logging.Logger;

import edu.logging.exceptions.LoggingException;
import edu.logging.models.Connection;
import edu.logging.services.FileService;

public class LoggerWorker extends Thread {
	private List<Connection> connections;
	private ConnectionLogger connectionLogger;
	private Logger logger = Logger.getLogger("LoggerWorker"); 

	public LoggerWorker(List<Connection> connections, FileService writer) throws LoggingException {
		this.connections = connections;
		this.connectionLogger = new ConnectionLogger(writer);
	}


	@Override
	public void run() {
		for(Connection connection : connections) {

			writeConnectionData(connection);

			logger.info(this.getId() + " finished working");
		}
	}

	private void writeConnectionData(Connection connection) {
		for(int i = 0; i < 3; i++) {
			synchronized(connectionLogger) {
				try {
					connectionLogger.writeConnectionToLog(connection);
					logger.info("Logged by: " + this.getId() + ". connection: " + connection.getConnectionData());
					Thread.sleep(60000);
				} catch (LoggingException | InterruptedException e) {
					logger.severe(e.getMessage());
					Thread.currentThread().interrupt();
				} 
			}
		}
	}


}
