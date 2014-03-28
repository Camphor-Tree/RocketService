package com.logpie.test;

import java.net.UnknownHostException;

import com.logpie.database.common.Database;
import com.logpie.database.common.MongoDBHelper;

public class Test {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		Database db = new MongoDBHelper();
		db.connect();
		
	}

}
