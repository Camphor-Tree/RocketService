package com.logpie.database.common;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;

import com.logpie.database.exception.DBNotFoundException;
import com.logpie.rocket.tool.RocketLog;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;

public class MongoDBHelper extends DatabaseHelper{
	private final String TAG = MongoDBHelper.class.getName();
	
	//this should be Singleton, the one and the only.
	private static MongoDBHelper sMongoDBHelper;
	//http://api.mongodb.org/java/current/
	//A MongoDB client with internal connection pooling. For most applications, you should have one MongoClient instance for the entire JVM.
	private MongoClient mMongoClient;
	
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

	
}
