package com.logpie.database.common;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import com.logpie.rocket.tool.RocketLog;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDBHelper extends DatabaseHelper{
	//this should be Singleton, the one and the only.
	private static MongoDBHelper sMongoDBHelper;
	//http://api.mongodb.org/java/current/
	//A MongoDB client with internal connection pooling. For most applications, you should have one MongoClient instance for the entire JVM.
	private MongoClient mMongoClient;
	private String TAG = MongoDBHelper.class.getName();
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
		try {
			/*
			 * Or to connect to a replica set, with auto-discovery of the primary, 
			 * supply a seed list of members
			 * MongoClient mongoClient = new MongoClient(Arrays.asList(
			  							 new ServerAddress("localhost", 27017),
	                                     new ServerAddress("localhost", 27018),
	                                     new ServerAddress("localhost", 27019)));
			 */
			mMongoClient = new MongoClient("localhost");
			
		} catch (UnknownHostException e) {
			RocketLog.e(this.TAG, "The Host doesnot exist!");
			e.printStackTrace();
		}
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

	public void createTable(String table){
		
	}
	
}
