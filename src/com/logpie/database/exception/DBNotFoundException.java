package com.logpie.database.exception;

public class DBNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mDBname;
	public DBNotFoundException(String dbName){
		this.mDBname = dbName;
	}
	public DBNotFoundException(){
		
	}
	
	public String getDBname(){
		return this.mDBname;
	}
}
