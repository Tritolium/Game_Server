package dataManagement;

import java.util.HashMap;

public class Data {
	
	public static HashMap<String, String> parseParameters(String toParse){
		HashMap<String, String> parameters = new HashMap<String, String>();
		String[] split = toParse.split("&");
		for(int i = 0; i < split.length; i++){
			String[] param = split[i].split("=");
			parameters.put(param[0], param[1]);
		}
		return parameters;
	}
}
