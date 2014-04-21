/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.logpie.rocket.data.MetricRecord;
import com.logpie.rocket.exception.HttpRequestIsNullException;
import com.logpie.rocket.logic.RocketServiceCentralLogic;
import com.logpie.rocket.tool.InsertCallback;
import com.logpie.rocket.tool.RocketHttpRequestParser;
import com.logpie.rocket.tool.RocketLog;
import com.logpie.rocket.tool.RocketRequestJSONParser;

public class RocketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TAG = RocketServlet.class.getName();
	
	public enum RequestType {
		INSERT,
		SEARCH;
		public static RequestType matchType(String type)
		{
			for(RequestType requestType : RequestType.values())
			{
				if(requestType.toString().equals(type))
					return requestType;
			}
			return null;
		}
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		//TODO: we should remove in the future
		//Currently we can just keep it.
		doPost(request,response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		RocketLog.i(TAG,"Start parsing request");
		try {
			// Convert Request to JSONObject
			JSONObject json = RocketHttpRequestParser.httpRequestParser(request);
			// Get the instance of Logic
			RocketServiceCentralLogic logic = RocketServiceCentralLogic.getInstance();
		
			String type = json.getString("type");
			if(type.toString().equals(RequestType.INSERT.toString()))
			{
			 	// convert JSONObject to Metric Record list
				RocketRequestJSONParser paser = new RocketRequestJSONParser();
				List<MetricRecord> list = paser.parseRocketRequestJSON(json);
				for(MetricRecord record : list)
				{
					logic.insertRecordIntoMongoDB(record, new InsertCallback());
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
			RocketLog.i(TAG, "Servlet cannot find the correct type from HttpRequestJSONObject");
		} catch (HttpRequestIsNullException e){
			e.printStackTrace();
			RocketLog.i(TAG, "HttpRequest is null.");
		}
	}
	
}
