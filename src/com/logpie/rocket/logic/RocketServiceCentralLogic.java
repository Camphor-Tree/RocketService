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
	
	public void insertRecordIntoMongoDB (MetricRecord metricRecord, RocketCallback callback){
		//get DB name based on the company's name
		DB db = MetricRecordAdapter.toDB(metricRecord);
		if(db==null)
		{
			//TODO: We should add check here, to verify whether the company is valid.
			try {
				callback.onError(new JSONObject().put("error", "company name not found or not supported"));
			} catch (JSONException e) {
				RocketLog.e(TAG,"JSONException");
				e.printStackTrace();
				return;
			}
		}

		try {
			//TODO: Find Title's ID  if not found, create one.
			//return titleID
			BasicDBObject titleObject = MetricRecordAdapter.toTitleBasicDBObject(metricRecord);
			MongoDBHelper.getInstance().insert(db, db.getCollection(CollectionNames.TITLE.getName()), titleObject);
			
			//TODO:Record add title id, then insert into mongoDB.
			//insert Record
			BasicDBObject recordObject = MetricRecordAdapter.toRecordBasicDBObject(metricRecord);
			MongoDBHelper.getInstance().insert(db, db.getCollection(CollectionNames.RECORD.getName()), recordObject);
		} catch (DBNotFoundException e) {
			try {
				callback.onError(new JSONObject().put("error", "company name not found or not supported"));
			} catch (JSONException jsonException) {
				RocketLog.e(TAG,"JSONException when executing onError callback");
				jsonException.printStackTrace();
				return;
			}
			e.printStackTrace();
		}
		
	}

}
