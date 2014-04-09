/*
 * Copyright (c) 2014 logpie.com
 * All rights reserved.
*/
package com.logpie.rocket.tool;

import org.json.JSONObject;

public interface RocketCallback {
	//callback used in RocketService
	abstract void onSuccess(JSONObject result);
	abstract void onError(JSONObject error);
}
