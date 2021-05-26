package engine.io;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
	static Calendar cal;
	static SimpleDateFormat sdf;
	
	public static final int INFO = 0, WARNING = 1, ERROR = 2;
	
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	
	public static void startLogger() {
        cal= Calendar.getInstance();
        sdf = new SimpleDateFormat("HH:mm:ss");
	}
	
	public static void println(String prefix, String p, int state) {
        cal = Calendar.getInstance();
		String threadInfo = Thread.currentThread().getName() + " thread";
		//threadInfo = String.format("%-13s", threadInfo);
		String messageInfo = "";
		String color = "";
		switch (state) {
		case 0:
			messageInfo = "INFO";
			break;
		case 1:
			messageInfo = "WARNING";
			color = Logger.ANSI_YELLOW;
			break;
		case 2:
			messageInfo = "ERROR";
			color = Logger.ANSI_RED;
			break;
		}
		System.out.println("[" + sdf.format(cal.getTime()) + "][" + threadInfo + "/" + color + messageInfo + Logger.ANSI_RESET +"][" + prefix + "]: " + p);
	}
}