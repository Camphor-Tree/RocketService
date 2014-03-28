package com.logpie.rocket.metric;

public class MetricRecord {

	/**
	 * This is a class corresponding to the metric record in DB
	 *  {
  "requestID":"1DEASEWO-2232GDA2",
  "company":"logpie.com",
  "platform":"java",
  "application":"logpie",
  "software_version":"1.01",
  "metrics":[
     {
        "component":"loginpage",
        "action":"register",
        "time":91
     },
     {
        "component":"loginpage",
        "action":"login",
        "time":27
     }
  ],
  "mobile_device":"true",
  "OS_type":"android",
  "OS_version":"4.1",
  "device_manufacture":"Samsung"
  "device_version":"Galaxy S3"
 }
	 */

	public static final String key_RequestID = "requestID";
	public static final String key_Company = "company";
	public static final String key_Platform = "platform";
	public static final String key_Application = "application";
	public static final String key_SoftwareVersion = "software_version";
	public static final String key_Component = "component";
	public static final String key_Metrics = "metrics";
	public static final String key_Action = "action";
	public static final String key_MetricTime = "time";
	public static final String key_MobileDevice = "mobile_device";
	public static final String key_OSType = "OS_type";
	public static final String key_OSVersion = "OS_version";
	public static final String key_DeviceManufacture = "device_manufacture";
	public static final String key_DeviceVersion = "device_version";
	
	//The fields below are mandatory
	private String mRequestID;
	private String mCompany;
	private String mPlatform;
	private String mApplication;
	private Float mSoftwareVersion;
	private String mComponent;
    private String mAction;
    private Integer mMetricTime;
    
    //The files below are optional
    private Boolean mIsMobileDevice;
    private String mOSType;
    private Float mOSVersion;
    private String mDeviceManufacture;
    private String mDeviceVersion;
	public String getRequestID() {
		return mRequestID;
	}
	public void setRequestID(String requestID) {
		mRequestID = requestID;
	}
	public String getCompany() {
		return mCompany;
	}
	public void setCompany(String company) {
		mCompany = company;
	}
	public String getPlatform() {
		return mPlatform;
	}
	public void setPlatform(String platform) {
		mPlatform = platform;
	}
	public String getApplication() {
		return mApplication;
	}
	public void setApplication(String application) {
		mApplication = application;
	}
	public Float getSoftwareVersion() {
		return mSoftwareVersion;
	}
	public void setSoftwareVersion(Float softwareVersion) {
		mSoftwareVersion = softwareVersion;
	}
	public String getComponent() {
		return mComponent;
	}
	public void setComponent(String component) {
		mComponent = component;
	}
	public String getAction() {
		return mAction;
	}
	public void setAction(String action) {
		mAction = action;
	}
	public Integer getMetricTime() {
		return mMetricTime;
	}
	public void setMetricTime(Integer metricTime) {
		mMetricTime = metricTime;
	}
	public Boolean getIsMobileDevice() {
		return mIsMobileDevice;
	}
	public void setIsMobileDevice(Boolean isMobileDevice) {
		mIsMobileDevice = isMobileDevice;
	}
	public String getOSType() {
		return mOSType;
	}
	public void setOSType(String oSType) {
		mOSType = oSType;
	}
	public Float getOSVersion() {
		return mOSVersion;
	}
	public void setOSVersion(Float oSVersion) {
		mOSVersion = oSVersion;
	}
	public String getDeviceManufacture() {
		return mDeviceManufacture;
	}
	public void setDeviceManufacture(String deviceManufacture) {
		mDeviceManufacture = deviceManufacture;
	}
	public String getDeviceVersion() {
		return mDeviceVersion;
	}
	public void setDeviceVersion(String deviceVersion) {
		mDeviceVersion = deviceVersion;
	}

}
