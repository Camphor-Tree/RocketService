/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.logic;

import org.json.JSONException;
import org.json.JSONObject;

import com.logpie.database.common.MongoDBHelper;
import com.logpie.rocket.data.CollectionNames;
import com.logpie.rocket.data.MetricRecord;
import com.logpie.rocket.exception.DBNotFoundException;
import com.logpie.rocket.tool.MetricRecordAdapter;
import com.logpie.rocket.tool.RocketCallback;
import com.logpie.rocket.tool.RocketLog;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class RocketServiceCentralLogic {
	private static final String TAG = RocketServiceCentralLogic.class.getName();
	private static RocketServiceCentralLogic sRocketServiceCentralLogic;
	private static MongoDBHelper sMongoDBHelper = MongoDBHelper.getInstance();
	
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
	public void insertRecordIntoMongoDB (MetricRecord metricRecord, RocketCallback callback){
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
		handleIndexInitialization(db);
	
		
		//get titleId from the title collection
		//check whether the title exist, if so, find the _id, if not, insert new title then return the id
		String titleId = handleTitleCollection(db, metricRecord);
		handleRecord(db, titleId, metricRecord, callback);
		handleInsertSuccess(callback);

	}
	
	//if the collection just created, then create the index for it.
	private static synchronized void handleIndexInitialization(DB db)
	{
		//create
		if(!sMongoDBHelper.isCollectionExist(db,CollectionNames.TITLE.getName()))
		{
			sMongoDBHelper.createTitleIndex(db.getCollection(CollectionNames.TITLE.getName()));
		}
		if(!sMongoDBHelper.isCollectionExist(db,CollectionNames.RECORD.getName()))
		{
			sMongoDBHelper.createRecordIndex(db.getCollection(CollectionNames.RECORD.getName()));
		}
	}
	
	private static synchronized String handleTitleCollection(DB db, MetricRecord metricRecord)
	{
		DBCollection titleCollection = db.getCollection(CollectionNames.TITLE.getName());
		sMongoDBHelper.createTitleIndex(titleCollection);
		//Try to find Title's ID  if not found, create one.
		BasicDBObject titleObject = MetricRecordAdapter.toTitleBasicDBObject(metricRecord);
		BasicDBObject queryResult = sMongoDBHelper.searchTitleObject(titleCollection, titleObject);
		String titleId;
		if(queryResult==null)
		{
			//if it is a new title, then insert the new titleObject, then try to get its _id
			try {
				sMongoDBHelper.insert(db, titleCollection, titleObject);
			} catch (DBNotFoundException e) {
				e.printStackTrace();
				RocketLog.e(TAG,e.getMessage());
			}
			queryResult = sMongoDBHelper.searchTitleObject(titleCollection, titleObject);
			titleId = queryResult.getString("_id");
		}
		else
		{
			titleId = queryResult.getString("_id");
		}
		return titleId;
	}
	
	private static void handleRecord(DB db, String titleId, MetricRecord metricRecord, RocketCallback callback)
	{
		try 
		{
			BasicDBObject recordObject = MetricRecordAdapter.toRecordBasicDBObject(metricRecord);
			//ecord add title id, then insert into mongoDB.
			recordObject.append("titleID",titleId);
			//insert Record
			sMongoDBHelper.insert(db, db.getCollection(CollectionNames.RECORD.getName()), recordObject);
		} catch (DBNotFoundException e) {
			handleInsertError(callback);
			RocketLog.e(TAG,e.getMessage());
		}
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
