package com.logpie.rocket.tool;

import java.io.File;
import java.util.Date;

import org.json.JSONObject;

public class InsertCallback implements RocketCallback {

	Date date = new Date();
	
	@Override
	public void onSuccess(JSONObject result) {
		// TODO Auto-generated method stub
		RocketLog.writeFile(date, result.toString());
	}

	@Override
	public void onError(JSONObject error) {
		// TODO Auto-generated method stub
		RocketLog.writeFile(date, error.toString());
	}

}
