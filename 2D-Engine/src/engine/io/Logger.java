package engine.io;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
	static Calendar cal;
	static SimpleDateFormat sdf;
	
	public static void startLogger() {
        cal= Calendar.getInstance();
        sdf = new SimpleDateFormat("HH:mm:ss");
	}
	
	public static void println(String suffix, String p) {
		String superSuffix = Thread.currentThread().getName();
		superSuffix = String.format("%-6s", superSuffix);
		System.out.println("[" + sdf.format(cal.getTime()) + "] [Thread/" + superSuffix + "] [" + suffix + "] " + p);
	}

}