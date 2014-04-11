/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public final class RocketLog {
	private static boolean debug = false;
	private static File file;
	private static String path = "";
	
	public static void openLog()
	{
		debug = true;
	}
	
	public static void setDebugMode(boolean isDebuging)
	{
	  debug = isDebuging;	
	}
	
	public static void i(String TAG, String info)
	{
		if(debug)
		{
			System.out.println(TAG+" : "+info);
		}
	}
	
	public static void d(String TAG, String info)
	{
		if(debug)
		{
			System.out.println(TAG+" : "+info);
		}
	}
	
	public static void e(String TAG, String info)
	{
		if(debug)
		{
			System.out.println(TAG+" : "+info);
		}
	}
	
	public static void writeFile(Date date, String s)
	{
		try {
			file = new File(date.toString(),path);
			if(!file.exists())
				file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(s);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
