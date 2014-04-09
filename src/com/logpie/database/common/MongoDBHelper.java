/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.database.common;

import java.util.HashMap;
import java.util.List;

import com.logpie.database.exception.DBNotFoundException;
import com.logpie.rocket.data.MetricRecord;
import com.logpie.rocket.tool.RocketLog;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;


public class MongoDBHelper extends DatabaseHelper{
	private final String TAG = MongoDBHelper.class.getName();
	
	//this should be Singleton, the one and the only.
	private static MongoDBHelper sMongoDBHelper;
	//http://api.mongodb.org/java/current/
	//A MongoDB client with internal connection pooling. For most applications, you should have one MongoClient instance for the entire JVM.
	private MongoClient mMongoClient;
	//DBMap store all the db RocketService connect to. (dbName -> DB)
	private HashMap<String,DB> mDBMap;
	
	private MongoDBHelper(){
		super(DBenum.MongoDB);
		this.connect();
		mDBMap = new HashMap<String,DB>();	
	}
	
	public synchronized static MongoDBHelper getInstance()
	{
		if(sMongoDBHelper!=null)
			return sMongoDBHelper;
		else
			return new MongoDBHelper();
	}
	
	@Override
	protected void connect(){
		mMongoClient = MongoSingleConnection.getMongoClient();
	}
	
	//return all the db exist on the MongoDB server
	public List<String> getDBList()
	{
		if(mMongoClient!=null)
			return mMongoClient.getDatabaseNames();
		else
		{
			this.connect();
			return mMongoClient.getDatabaseNames();
		}
		
	}
	
	public MongoClient getMongoClient(){
		if(mMongoClient!=null)
			return mMongoClient;
		else
		{
			this.connect();
			return mMongoClient;
		}
	}
	
	//MongoClient will create a db even if the dbName doesn't exist.
	public DB getDB(String dbName)
	{
		if(mDBMap == null)
		{
			mDBMap = new HashMap<String,DB>();
			mDBMap.put(dbName,mMongoClient.getDB(dbName));
		}
		else
		{
			if(!mDBMap.containsKey(dbName))
				mDBMap.put(dbName,mMongoClient.getDB(dbName));
		}
		return mDBMap.get(dbName);
	}

	// create a collection in specific MongoDB's database
	@Override
	public DBCollection createTable(String dbName, String tableName) throws DBNotFoundException {
		if(!mDBMap.containsKey(dbName))
		{
			RocketLog.i(TAG,"The database hasn't been established");
			throw new DBNotFoundException(dbName);
		}
		else
		{	
			RocketLog.i(TAG, "Start create collection: "+tableName+" in DB:" + dbName);
			return mDBMap.get(dbName).getCollection(tableName);
		}
	}
	
	public boolean isDBExist(String dbName){
		 List<String> dbList;
		 if(mMongoClient!=null)
		 {
			dbList = mMongoClient.getDatabaseNames();
		 }
		 else
			 return false;
		 for(String db : dbList)
		 {
			 if(dbName==db)
				 return true;
		 }
		 return false;
	}
	
	//insert one single record into specific collection
	//return true, if success.
	public boolean insertSingleRecord(String dbName, String collectionName, BasicDBObject dbObject){
		return mMongoClient.getDB(dbName).getCollection(collectionName).insert(dbObject,WriteConcern.SAFE).getLastError().ok();
	}
	
	//only if you are sure about the query result only have one record or you just need one record,
	//then you should call this api, otherwise you need to call query(DBCollection collection, BasicDBObject dbObject)
	public BasicDBObject querySingleRecord(DBCollection collection, BasicDBObject dbObject)
	{
		Cursor cursor = collection.find(dbObject);
	    if(cursor.hasNext()) {
		    BasicDBObject queryResult = (BasicDBObject)cursor.next();
		    cursor.close();
		    return queryResult;
	    }
	    else
	    	return null;
	}
	
	public BasicDBList query(DBCollection collection, BasicDBObject dbObject)
	{
		Cursor cursor = collection.find(dbObject);
		BasicDBList dbObjectList = new BasicDBList();
		try {
	    while(cursor.hasNext()) {
	       dbObjectList.add((BasicDBObject)cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		return dbObjectList;
	}

	public void insert(String dbName, String tableName, MetricRecord record) throws DBNotFoundException{
		if(!mDBMap.containsKey(dbName))
		{
			RocketLog.i(TAG,"The database hasn't been established");
			throw new DBNotFoundException(dbName);
		}
		else
		{
			RocketLog.i(TAG, "insert collection: "+tableName+" in DB:" + dbName);
			DBCollection coll = mDBMap.get(dbName).getCollection(tableName);
			
		}
	}
}
