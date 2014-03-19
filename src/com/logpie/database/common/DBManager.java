package com.logpie.database.common;

import java.net.UnknownHostException;

public class DBManager {

	public enum Database{
		MySQL("MySQL","5.0"), MongoDB("MongoDB","2.4");
		
		private final String mName;
		private final String mVersion;
		
		private Database(String name, String version){
			this.mName = name;
			this.mVersion = version;
		}
	}
	
	private Database mDB;
	
	public DBManager(Database database){
		this.mDB = database;
	}
	
	public String getVersion(){
		return mDB.mVersion;
	}

	public void connect() throws UnknownHostException{  // connect to database
		DBConnection mDBConnection;
		switch(mDB.mName){
			case "MySQL":
				mDBConnection = new MySQLHelper();
				mDBConnection.connect();
			case "MongoDB":
				mDBConnection = new MongoDBHelper();
				mDBConnection.connect();
			default:
				System.out.println("Cannot find this kind of dabatase.");
		}
	}
	
}


