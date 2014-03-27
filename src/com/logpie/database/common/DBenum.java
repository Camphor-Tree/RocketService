package com.logpie.database.common;

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
