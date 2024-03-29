/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.tool;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.logpie.rocket.data.MetricRecord;

public class RocketRequestJSONParser {
	private static final String TAG = RocketRequestJSONParser.class.getName(); 
	
	String mRequestID;
	String mCompany;
	String mPlatform;
	String mApplication;
	String mSoftwareVersion;
	String mEnvironment;
	JSONArray mMetrics;
	Boolean mIsMobileDevice;
	
	public List<MetricRecord> parseRocketRequestJSON(JSONObject requestJSON)
	{
		/*
		 * Parse the requestJSON into LinkedList
		 *That is because the client may send multiple MetricRecord into one Request
		*/
		List<MetricRecord> metricRecords = new LinkedList<MetricRecord>();
		
		// These are mandatory fields. If we are missing one value, just fail the parse by returning null.
		try {
			mRequestID = requestJSON.getString(MetricRecord.key_RequestID);
			mCompany = requestJSON.getString(MetricRecord.key_Company);
			mPlatform = requestJSON.getString(MetricRecord.key_Platform);
			mApplication = requestJSON.getString(MetricRecord.key_Application);
			mSoftwareVersion = requestJSON.getString(MetricRecord.key_SoftwareVersion);
			mEnvironment = requestJSON.getString(MetricRecord.key_Environment);
			mMetrics = requestJSON.getJSONArray(MetricRecord.key_Metrics);
			mIsMobileDevice = Boolean.getBoolean((requestJSON.getString(MetricRecord.key_MobileDevice)));
		} catch (JSONException e) {
			RocketLog.e(TAG, "Request JSON is malformed");
			e.printStackTrace();
			return metricRecords;
		}
		
		//the client may send multiple actions' metric record to Rocket
		//mMetrics must have at least one record, or we will just fail the parse process by returning null
		int countRecord = 0;
		if(mMetrics!=null)
		  countRecord = mMetrics.length();
		if(countRecord == 0)
		{
			RocketLog.e(TAG, "No metric record found.");
			return metricRecords;
		}
		else
		{
			for(int i=0;i<countRecord;i++)
			{
				MetricRecord metricRecord = new MetricRecord();
				metricRecord.setRequestID(mRequestID);
				metricRecord.setCompany(mCompany);
				metricRecord.setPlatform(mPlatform);
				metricRecord.setApplication(mApplication);
				metricRecord.setSoftwareVersion(mSoftwareVersion);
				metricRecord.setEnvironment(mEnvironment);
				metricRecord.setIsMobileDevice(mIsMobileDevice);
				try {
					//If there are mobile device info, then add it to the metricRecord
					if(mIsMobileDevice)
					{
						setMobileDeviceInfo(metricRecord, requestJSON);
					}
					metricRecord.setComponent(mMetrics.getJSONObject(i).getString(MetricRecord.key_Component));
					metricRecord.setAction(mMetrics.getJSONObject(i).getString(MetricRecord.key_Action));
					metricRecord.setMetricTime(mMetrics.getJSONObject(i).getString(MetricRecord.key_MetricTime));
				} catch (JSONException e) {
					RocketLog.e(TAG, "Request JSON is malformed");
					e.printStackTrace();
					return metricRecords;
				}
				catch(NumberFormatException e)
				{
					RocketLog.e(TAG, "The metricTime cannot be parsed as Integer");
					e.printStackTrace();
					continue;
				}
				
				metricRecords.add(metricRecord);				
			}
		
		}
		return metricRecords;
	}
	
	private void setMobileDeviceInfo(MetricRecord metricRecord, JSONObject requestJSON) throws JSONException
	{
		metricRecord.setOSType(requestJSON.getString(MetricRecord.key_OSType));
		metricRecord.setOSVersion(requestJSON.getString(MetricRecord.key_OSVersion));
		metricRecord.setDeviceManufacture((requestJSON.getString(MetricRecord.key_DeviceManufacture)));
		metricRecord.setDeviceVersion(requestJSON.getString(MetricRecord.key_DeviceVersion));
	}
}
