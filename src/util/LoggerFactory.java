package util;

public class LoggerFactory {
	
	public static void createInfoLog(String s) {
		System.out.println("[INFO] " + s);
	}
	
	public static void createDebugLog(String s) {
		System.out.println("[DEBUG] " + s);
	}
	
	public static void createErrorLog(String s) {
		System.err.println("[ERROR] " + s);
	}
	
}
