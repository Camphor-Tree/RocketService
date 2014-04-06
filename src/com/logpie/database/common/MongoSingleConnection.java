package com.logpie.database.common;

import java.net.UnknownHostException;

import com.logpie.rocket.tool.RocketLog;
import com.mongodb.MongoClient;

public class MongoSingleConnection {
	private final String TAG = MongoSingleConnection.class.getName();
	private static MongoClient sMongoClient;
	
	private MongoSingleConnection()
	{
		/*
		 * Or to connect to a replica set, with auto-discovery of the primary, 
		 * supply a seed list of members
		 * MongoClient mongoClient = new MongoClient(Arrays.asList(
		  							 new ServerAddress("localhost", 27017),
                                     new ServerAddress("localhost", 27018),
                                     new ServerAddress("localhost", 27019)));
		 */
		try {
			sMongoClient = new MongoClient("localhost");
		} catch (UnknownHostException e) {
			RocketLog.e(this.TAG, "The Host doesnot exist!");
			e.printStackTrace();
		}
	}
	
	public static synchronized MongoClient getMongoClient(){
		if(sMongoClient != null )
			return sMongoClient;
		else {
			new MongoSingleConnection();
			return MongoSingleConnection.sMongoClient;
		}
	}

}
