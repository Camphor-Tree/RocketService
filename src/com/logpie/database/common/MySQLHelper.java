package com.logpie.database.common;

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
}
