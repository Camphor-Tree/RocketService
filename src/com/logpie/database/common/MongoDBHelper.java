package com.logpie.database.common;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDBHelper extends Database{
	DB mDB;

	public MongoDBHelper(){
		super(DBenum.MongoDB);
	}
	
	@Override
	public void connect() throws UnknownHostException {
		// TODO Auto-generated method stub
		MongoClient mongoClient = new MongoClient("localhost");
		/*
		 * Or to connect to a replica set, with auto-discovery of the primary, 
		 * supply a seed list of members
		 * MongoClient mongoClient = new MongoClient(Arrays.asList(
		  							 new ServerAddress("localhost", 27017),
                                     new ServerAddress("localhost", 27018),
                                     new ServerAddress("localhost", 27019)));
		 */
		mDB = mongoClient.getDB("rocket");
	}

	public void createTable(String table){
		
	}
	
}
