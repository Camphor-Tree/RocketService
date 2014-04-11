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
import com.logpie.rocket.logic.RocketServiceCentralLogic;
import com.logpie.rocket.tool.InsertCallback;
import com.logpie.rocket.tool.RocketHttpRequestParser;
import com.logpie.rocket.tool.RocketLog;
import com.logpie.rocket.tool.RocketRequestJSONParser;

public class RocketServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TAG = RocketHttpRequestParser.class.getName();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// convert String to JSONObject
		JSONObject json = RocketHttpRequestParser.httpRequestParser(request);
		
		// get the instance of Logic
		RocketServiceCentralLogic logic = RocketServiceCentralLogic.getInstance();
		
		try {
			String type = json.getString("type");
			switch(type){
				case "insert":
					// convert JSONObject to Metric Record list
					RocketRequestJSONParser paser = new RocketRequestJSONParser();
					List<MetricRecord> list = paser.parseRocketRequestJSON(json);
					for(MetricRecord record : list)
						logic.insertRecordIntoMongoDB(record, new InsertCallback());
					break;
				case "update":
					break;
				case "find":
					break;
				default:
					break;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RocketLog.i(TAG, "Servlet cannot find the correct type from HttpRequestJSONObject");
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	
}
