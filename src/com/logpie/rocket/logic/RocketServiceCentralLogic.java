/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.logic;

import org.json.JSONException;
import org.json.JSONObject;

import com.logpie.database.common.MongoDBHelper;
import com.logpie.database.exception.DBNotFoundException;
import com.logpie.rocket.data.CollectionNames;
import com.logpie.rocket.data.MetricRecord;
import com.logpie.rocket.tool.MetricRecordAdapter;
import com.logpie.rocket.tool.RocketCallback;
import com.logpie.rocket.tool.RocketLog;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class RocketServiceCentralLogic {
	private static final String TAG = RocketServiceCentralLogic.class.getName();
	private static RocketServiceCentralLogic sRocketServiceCentralLogic;
	
	private RocketServiceCentralLogic()
	{
		
	}
	
	public static synchronized RocketServiceCentralLogic getInstance(){
		if(sRocketServiceCentralLogic == null)
		{
			sRocketServiceCentralLogic = new RocketServiceCentralLogic();
		}
		return sRocketServiceCentralLogic;
	}
	
	//Central method of RocketService
	//Insert metricRecord into MongoDB
	public static void insertRecordIntoMongoDB (MetricRecord metricRecord, RocketCallback callback){
		//TODO: We should add check here, to verify whether the company is valid.
		
		//TODO: Should add 
		
		//get DB name based on the company's name
		DB db = MetricRecordAdapter.toDB(metricRecord);
		if(db==null)
		{
			try 
			{
				callback.onError(new JSONObject().put("error", "company name not found or not supported"));
				return;
			} 
			catch (JSONException e) 
			{
				RocketLog.e(TAG,"JSONException when executing onError Callback");
				e.printStackTrace();
				return;
			}
		}

		try 
		{
			DBCollection titleCollection = db.getCollection(CollectionNames.TITLE.getName());
			//Try to find Title's ID  if not found, create one.
			BasicDBObject titleObject = MetricRecordAdapter.toTitleBasicDBObject(metricRecord);
			BasicDBObject queryResult = MongoDBHelper.getInstance().searchTitleObject(titleCollection, titleObject);
			String titleId;
			if(queryResult==null)
			{
				//if it is a new title, then insert the new titleObject, then try to get its _id
				MongoDBHelper.getInstance().insert(db, titleCollection, titleObject);
				queryResult = MongoDBHelper.getInstance().searchTitleObject(titleCollection, titleObject);
				titleId = queryResult.getString("_id");
			}
			else
			{
				titleId = queryResult.getString("_id");
			}
			
			BasicDBObject recordObject = MetricRecordAdapter.toRecordBasicDBObject(metricRecord);
			//ecord add title id, then insert into mongoDB.
			recordObject.append("titleID",titleId);
			//insert Record
			MongoDBHelper.getInstance().insert(db, db.getCollection(CollectionNames.RECORD.getName()), recordObject);
		} catch (DBNotFoundException e) {
			handleInsertError(callback);
			RocketLog.e(TAG,e.getMessage());
		}

			handleInsertSuccess(callback);

	}
	
	private static void handleInsertSuccess(RocketCallback callback)
	{
		try {
			callback.onSuccess(new JSONObject().append(TAG,"Insert MetridRecord into RocketService DataBase Success!"));
		} catch (JSONException e) {
			RocketLog.e(TAG,e.getMessage());
			RocketLog.writeFile(TAG,"JSONException when executing onError callback");
			return;
		}
	}
	
	private static void handleInsertError(RocketCallback callback)
	{
		try {
			callback.onError(new JSONObject().append(TAG," error: company name not found or not supported"));
		} catch (JSONException e) {
			RocketLog.e(TAG,e.getMessage());
			RocketLog.writeFile(TAG,"JSONException when executing onError callback");
			return;
		}
	}
}
