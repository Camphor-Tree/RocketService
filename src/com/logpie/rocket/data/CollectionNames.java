/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.data;

// Common collection names in one Mongo Database for one company
public enum CollectionNames {
	
	TITLE("title"),
	RECORD("record");
	
	private String mName;
	CollectionNames(String name)
	{
		this.mName = name;
	}
	
	public String getName(){
		return this.mName;
	}

}
