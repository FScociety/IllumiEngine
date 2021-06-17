package engine.io;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import engine.game.GameContainer;

public class Logger {
	
	static Calendar cal;
	static SimpleDateFormat sdf;
	
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_GREEN = "\u001b[32m";
	
	private static String lastPrefix = "null";
	
	public static void startLogger() {
        cal= Calendar.getInstance();
        sdf = new SimpleDateFormat("HH:mm:ss");
	}
	
	public static void debug(String message) {
		if (GameContainer.debug) {
			Logger.log(message, Logger.ANSI_RESET, "DEBUG");
		}
	}
	
	public static void warn(String message) {
		Logger.log(message, Logger.ANSI_YELLOW, "WARN ");
	}
	
	public static void error(String message) {
		Logger.log(message, Logger.ANSI_RED, "ERROR");
	}
	
	public static void fine(String message) {
		Logger.log(message, Logger.ANSI_GREEN, "FINE ");
	}
	
	public static void log(String message) {
		Logger.log(message, Logger.ANSI_RESET, "LOG  ");
	}
	
	public static void log(String message, String colorCode, String suffix) {
        cal = Calendar.getInstance();
		String threadInfo = Thread.currentThread().getName() + " thread";
		String prefix = Thread.currentThread().getStackTrace()[3].getClassName();
		
		if (!Logger.lastPrefix.equals(prefix)) { 
			System.out.println();
			System.out.println("from [" + prefix + "] in [" + Thread.currentThread().getName() +"] : ");
		}
		System.out.println("[" + sdf.format(cal.getTime()) + "]" + colorCode + " [" + suffix + "] ---> " + message + Logger.ANSI_RESET);
	
		Logger.lastPrefix = prefix;
	}
}