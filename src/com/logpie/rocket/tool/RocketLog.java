/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class RocketLog {
	private static boolean sDebug = false;
	private static String sPath = "/Users/xujiahang/Documents/";
	private static final String TAG = RocketLog.class.getName();
	
	public static void openLog()
	{
		sDebug = true;
	}
	
	public static void setDebugMode(boolean isDebuging)
	{
		sDebug = isDebuging;	
	}
	
	public static void i(String TAG, String info)
	{
		if(sDebug)
		{
			System.out.println(TAG+" : "+info);
		}
	}
	
	public static void d(String TAG, String info)
	{
		if(sDebug)
		{
			System.out.println(TAG+" : "+info);
		}
	}
	
	public static void e(String TAG, String info)
	{
		if(sDebug)
		{
			System.out.println(TAG+" : "+info);
		}
	}
	
	public synchronized static void writeFile(String TAG, String info)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss:SSS");
	       //get current date time with Date()
	    Date date = new Date();
	    FileWriter writer = null;
		try {
			File file = new File(sPath,dateFormat.format(date)+".txt");
			if(!file.exists())
				file.createNewFile();
			writer = new FileWriter(file.getAbsoluteFile(),true);
			writer.write(timeFormat.format(date)+"-->");
			writer.write(TAG+": ");
			writer.write(info);
			writer.write("\n");
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			RocketLog.e(TAG,"IOException: When writing log file.");
			e.printStackTrace();
		}
	}
	
}
