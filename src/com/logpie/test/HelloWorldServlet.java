/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse respsonse)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		respsonse.getWriter().write("<html> <head></head> <body> <h1>Hello World</h1> </body> </html>");
	}

	
}
