package com.logpie.rocket.tool;


import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.logpie.rocket.exception.HttpRequestIsNullException;

public class RocketHttpRequestParser {
	private static final String TAG = RocketHttpRequestParser.class.getName();
	
	// Check JsonObject is null or not. Null means parser fails.
	public static JSONObject httpRequestParser(HttpServletRequest request) throws HttpRequestIsNullException{
		if(request==null)
			throw new HttpRequestIsNullException();
		
		JSONObject jsonObj = new JSONObject();
		try {
			StringBuilder stringBuilder = new StringBuilder();
		
			BufferedReader reader  = new BufferedReader(request.getReader());
			String line;
			while((line=reader.readLine())!=null){
				stringBuilder.append(line);
			}
			jsonObj = new JSONObject(stringBuilder.toString());
	
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RocketLog.i(TAG, "JSON convert exception. Cannot convert String to JSONObject");
			// Return null if find IO exception
			return null; 
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RocketLog.i(TAG, "HttpRequest IO exception. Cannot get httprequest inputstream.");
			// Return null if find IO exception
			return null; 
		} 
		return jsonObj;
	}
}
