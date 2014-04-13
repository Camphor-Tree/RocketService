/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.exception;

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
