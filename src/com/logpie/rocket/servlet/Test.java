/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;

import com.logpie.database.common.MongoDBHelper;
import com.logpie.rocket.tool.RocketLog;
import com.mongodb.DB;

public class Test {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		/*
		 * MongoDBHelper db = MongoDBHelper.getInstance();
		System.out.println(db.getVersion());
		DB rocketDB = db.getDB("Rocket");
		Set<String> set = rocketDB.getCollectionNames();
		for(String a : set)
		{
			System.out.println(a);
		}
		//System.out.println(rocketDB.getStats());
		//rocketDB.
		 * 
		 */
		testRocketWriteLog();
		
	}
	private static void testRocketWriteLog(){
		RocketLog.writeFile("testTAG","This is just a test1!");
		RocketLog.writeFile("testTAG","This is just a test2!");
		RocketLog.writeFile("testTAG","This is just a test3!");
		RocketLog.writeFile("testTAG","This is just a test4!");
	}
	
	private void testConnection(){
		Socket socket;
		try{
			socket = new Socket("localhost",8080);
			OutputStream os;
			os = socket.getOutputStream();
			
			StringBuffer stringbuffer = new StringBuffer();  
            stringbuffer.append("POST /RocketService/hello HTTP/1.1\r\n"); 
            stringbuffer.append("Accept-Language: zh-cn\r\n");  
            stringbuffer.append("Connection: Keep-Alive\r\n");  
            stringbuffer.append("Host:localhost\r\n");  

			String str = "{\"requestID\":\"1DEASEWO-2232GDA2\","
					   + "\"company\":\"logpie.com\",\"platform\":\"java\","
					   + "\"application\":\"logpie\","
					   + "\"software_version\":\"1.01\","
					   + "\"metrics\":[{"
					        + "\"component\":\"loginpage\","
					        + "\"action\":\"register\","
					        + "\"timestamp\":9800284756345,"
					        + "\"time\":91},"
					        + "{\"component\":\"loginpage\","
					        + "\"action\":\"login\","
					        + "\"timestamp\":9800284756389,"
					        + "\"time\":27}],"
					  + "\"mobile_device\":\"true\","
					  + "\"OS_type\":\"android\","
					  + "\"OS_version\":\"4.1\","
					  + "\"device_manufacture\":\"Samsung\","
					  + "\"device_version\":\"Galaxy S3\"}";
			
			stringbuffer.append("Content-Length:"+str.length()+"\r\n");  
            stringbuffer.append("\r\n");
			stringbuffer.append(str);
			stringbuffer.append("\r\n");
		
			os.write(stringbuffer.toString().getBytes());
			os.flush();
		}
		catch (UnknownHostException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
