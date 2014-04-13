/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.database.common;

import com.logpie.rocket.exception.DBNotFoundException;

public abstract class DatabaseHelper {
	
	public enum DBenum {
		MongoDB ("MongoDB","2.4"),
		MySQL ("MySQL","11.1");
			
		private final String dbName;
		private final String dbVersion;
		
		private DBenum(String n, String v) {
			this.dbName = n;
			this.dbVersion = v;
		}
			
		public String getName() {
			return dbName;
		}

		public String getVersion() {
			return dbVersion;
		}
	}

	private String mName;
	private String mVersion;
	
	public DatabaseHelper(DBenum db){
		this.setName(db.getName());
		this.setVersion(db.getVersion());
	}
	
	protected abstract void connect();
	
	public abstract Object createTable(String dbName,String tableName) throws DBNotFoundException;
	
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



