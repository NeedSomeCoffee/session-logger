package edu.logging.services;

import java.util.Date;
import java.util.Random;

public class ConnectionUtils {
		
	public static String getTimeStamp() {
		return String.valueOf(new Date().getTime());
	}
	
	public static String getSessionNumber() {
		Random rnd = new Random();
		return String.valueOf(100000000 + rnd.nextInt(900000000));
	}
	
	public static String getIpAddress() {
		Random rnd = new Random();
		final String delimeter = ".";
		
		String rangeOne = String.valueOf(rnd.nextInt(256));
		String rangeTwo = String.valueOf(rnd.nextInt(256));
		String rangeThree = String.valueOf(rnd.nextInt(256));
		String rangeFour = String.valueOf(rnd.nextInt(256));

		
		return new StringBuilder().append(rangeOne).append(delimeter)
					.append(rangeTwo).append(delimeter)
					.append(rangeThree).append(delimeter)
					.append(rangeFour).toString();
	}
}
