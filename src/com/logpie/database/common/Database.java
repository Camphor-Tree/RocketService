package com.logpie.database.common;

import java.net.UnknownHostException;

public abstract class Database {

	private String mName;
	private String mVersion;
	
	public Database(DBenum db){
		this.setName(db.getName());
		this.setVersion(db.getVersion());
	}
	
	public void connect() throws UnknownHostException{  
		// connect to database
	}
	
	public void createTable(String table){
		// create table or collection
	}
	
	public void insert(){
		
	}

	public String getVersion() {
		return mVersion;
	}

	private void setVersion(String mVersion) {
		this.mVersion = mVersion;
	}

	public String getName() {
		return mName;
	}

	private void setName(String mName) {
		this.mName = mName;
	}
	
}



