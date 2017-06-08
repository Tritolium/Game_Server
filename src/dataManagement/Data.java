package dataManagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Data {

	public static HashMap<String, String> parseParameters(String toParse) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		String[] split = toParse.split("&");
		for (int i = 0; i < split.length; i++) {
			String[] param = split[i].split("=");
			parameters.put(param[0], param[1]);
		}
		return parameters;
	}

	/**
	 * Reads a file line for line and returns it as a hashed map, with the first
	 * word in each line as key
	 * 
	 * @param path:
	 *            the path to the file
	 * @return the file data
	 */
	public static HashMap<String, String> readFile(String path) {
		HashMap<String, String> result = new HashMap<String, String>();

		File file = new File(path);
		FileReader fin = null;
		BufferedReader in = null;
		
		String line;
		
		try {
			fin = new FileReader(file);
			in = new BufferedReader(fin);
			
			while((line = in.readLine()) != null){
				result.put(line.split(",")[0], line);
			}
			fin.close();
			in.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void writeToLog(String toLog){
		File dir = new File("log/");
		dir.mkdirs();
		
		SimpleDateFormat s = new SimpleDateFormat("dd_MM_yyyy");
		
		File logfile = new File(dir, s.format(new Date()) + ".log");
		
		s = new SimpleDateFormat("dd:MM:yyyy_HH:mm:ss");
		
		Writer writer = null;
		
		try {
			writer = new FileWriter(logfile, true);
			writer.write(s.format(new Date()) + "::" + toLog);
			writer.write(System.lineSeparator());
		} catch (IOException e) {
			System.err.println(e);
		} finally{
			try {
				writer.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
}
