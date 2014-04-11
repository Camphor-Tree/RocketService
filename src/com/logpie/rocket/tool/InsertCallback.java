package com.logpie.rocket.tool;

import java.io.File;
import java.util.Date;

import org.json.JSONObject;

public class InsertCallback implements RocketCallback {
	private static final String TAG = InsertCallback.class.getName();
	Date date = new Date();
	
	@Override
	public void onSuccess(JSONObject result) {
		// TODO Auto-generated method stub
		RocketLog.writeFile(TAG, result.toString());
	}

	@Override
	public void onError(JSONObject error) {
		// TODO Auto-generated method stub
		RocketLog.writeFile(TAG, error.toString());
	}

}
