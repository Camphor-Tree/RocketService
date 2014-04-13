package com.logipe.rocket.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.logpie.rocket.exception.HttpRequestIsNullException;
import com.logpie.rocket.tool.RocketHttpRequestParser;

public class RocketHttpRequestPaserTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testHttpRequestIsNull() throws HttpRequestIsNullException{
		exception.expect(HttpRequestIsNullException.class);
		RocketHttpRequestParser.httpRequestParser(null);
	}
	
}
