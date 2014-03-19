package com.logpie.test;

import com.logpie.database.common.DBManager;
import com.logpie.database.common.DBManager.Database;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBManager db = new DBManager(Database.MongoDB);
	}

}
