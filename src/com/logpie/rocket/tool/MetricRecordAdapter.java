/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.tool;

import com.logpie.database.common.MongoDBHelper;
import com.logpie.rocket.data.CollectionNames;
import com.logpie.rocket.data.MetricRecord;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;


public final class MetricRecordAdapter {
	public static MongoDBHelper sMongoDBHelper = MongoDBHelper.getInstance();
	
	public static DB toDB(MetricRecord metricRecord)
	{
		//In our design, compnayName match one DB name
		String companyName = metricRecord.getCompany();
		if(companyName!=null)
			return sMongoDBHelper.getDB(companyName);
		else 
			return null;
	}
	
	public static BasicDBObject toTitleBasicDBObject(MetricRecord metricRecord)
	{
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.append(MetricRecord.key_Application, metricRecord.getApplication());
		dbObject.append(MetricRecord.key_Component, metricRecord.getComponent());
		dbObject.append(MetricRecord.key_Action, metricRecord.getAction());
		dbObject.append(MetricRecord.key_Platform, metricRecord.getPlatform());
		dbObject.append(MetricRecord.key_SoftwareVersion, metricRecord.getSoftwareVersion());
		dbObject.append(MetricRecord.key_Environment, metricRecord.getEnvironment());
		return dbObject;
	};
	

	public static BasicDBObject toRecordBasicDBObject(MetricRecord metricRecord)
	{	
		BasicDBObject titleObject = MetricRecordAdapter.toTitleBasicDBObject(metricRecord);
		sMongoDBHelper.querySingleRecord(sMongoDBHelper.getDB(metricRecord.getCompany()).getCollection(CollectionNames.TITLE.getName()),titleObject);
		
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.append(MetricRecord.key_RequestID, metricRecord.getRequestID());
		dbObject.append(MetricRecord.key_MetricTime, metricRecord.getMetricTime());
		dbObject.append(MetricRecord.key_MetricTimeStamp, metricRecord.getmMetricTimeStamp());
		
		return dbObject;
	};

}
