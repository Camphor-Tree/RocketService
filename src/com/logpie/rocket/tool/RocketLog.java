package com.logpie.rocket.tool;

public final class RocketLog {
	private static boolean debug = false;
	
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
	
	
}
