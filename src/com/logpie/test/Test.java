/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.test;

import java.net.UnknownHostException;
import java.util.Set;

import com.logpie.database.common.DatabaseHelper;
import com.logpie.database.common.MongoDBHelper;
import com.mongodb.DB;

public class Test {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		MongoDBHelper db = MongoDBHelper.getInstance();
		System.out.println(db.getVersion());
		DB rocketDB = db.getDB("Rocket");
		Set<String> set = rocketDB.getCollectionNames();
		for(String a : set)
		{
			System.out.println(a);
		}
		//System.out.println(rocketDB.getStats());
		//rocketDB.
	}

}
