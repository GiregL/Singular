package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ResourceLoader {
	
	public static String getResource(String path) {
		
		StringBuilder b = new StringBuilder();
		
		try (Scanner scan = new Scanner(new File(path))) {
			scan.useDelimiter("\n|\0");
			while (scan.hasNext()) {
				b.append(scan.next()).append("\n");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return b.toString();
	}
	
}
