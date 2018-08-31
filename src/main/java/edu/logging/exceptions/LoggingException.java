package edu.logging.exceptions;

public class LoggingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4345845833502813531L;
	private String message;
	private Throwable cause;
	
	
	public LoggingException(String message) {
		this.message = message;
	}


	public LoggingException(String message, Throwable cause) {
		this.message = message;
		this.cause = cause;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}	
}
