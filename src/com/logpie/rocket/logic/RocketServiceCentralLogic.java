/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.logic;

import com.logpie.database.common.MongoDBHelper;
import com.logpie.rocket.data.MetricRecord;
import com.logpie.rocket.tool.RocketCallback;

public class RocketServiceCentralLogic {
	private static RocketServiceCentralLogic sRocketServiceCentralLogic;
	
	private RocketServiceCentralLogic()
	{
		
	}
	
	public static synchronized RocketServiceCentralLogic getInstance(){
		if(sRocketServiceCentralLogic == null)
		{
			sRocketServiceCentralLogic = new RocketServiceCentralLogic();
		}
		return sRocketServiceCentralLogic;
	}
	
	public void insertRecordIntoMongoDB (MetricRecord metricRecord, RocketCallback callback){

	}

}
