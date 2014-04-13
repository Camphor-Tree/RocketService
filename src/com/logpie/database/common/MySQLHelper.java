/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.database.common;

import com.logpie.rocket.exception.DBNotFoundException;
import com.mongodb.DB;

public class MySQLHelper extends DatabaseHelper{
	DB mDB;

	public MySQLHelper(){
		super(DBenum.MySQL);
	}
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	public void createTable(String table){
		
	}
	@Override
	public Object createTable(String dbName, String tableName)
			throws DBNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
