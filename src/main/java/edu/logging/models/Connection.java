package edu.logging.models;


public class Connection {
	private String timeStamp;
	private String session;
	private String ipAddress;

	public Connection(String timeStamp, String session, String ipAddress) {
		this.timeStamp = timeStamp;
		this.session = session;
		this.ipAddress = ipAddress;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getSession() {
		return session;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public Connection setSession(String session) {
		this.session = session;
		return this;
	}

	public Connection setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}
	
	public String getConnectionData() {
		StringBuilder builder = new StringBuilder();
		builder.append(timeStamp);
		builder.append(" ");
		builder.append(session);
		builder.append(" ");
		builder.append(ipAddress);
		
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Connection [timestamp=");
		builder.append(timeStamp);
		builder.append(", session=");
		builder.append(session);
		builder.append(", ipAddress=");
		builder.append(ipAddress);
		builder.append("]");
		return builder.toString();
	}
	
	
}
