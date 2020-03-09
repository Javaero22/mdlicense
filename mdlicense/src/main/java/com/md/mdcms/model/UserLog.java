package com.md.mdcms.model;

import java.util.Vector;

public class UserLog {

	private String userPid;
	private String jobNumber;
	private String userLastName;
	private String userFirstName;
	private String dateTimeLoggedOn;
	private String dateTimeLoggedOff;
	private long timestampLoggedOff;
	private String dateLastAccess;
	private long timestampLastAccess;
	private String exceptionText;
	private int callCount;
	private String action;
	private String function;
	private String langId;
	private String requestCode;
	private String screen;
	private String dateTime;
	private long timeUsedMiddlewareRequest;
	private long timeUsedMiddlewareResponse;
	private long timeUsedMiddleware;
	private long timeUsedMiddlewareTotal;
	private long timeUsedTotal;

	private static long TIMEOUT = 7200000; // 2h
	// private static long TIMEOUT = 60000;

	private Vector<State> responseStates;

	public String getTimeoutStyleClass() {
		if (timestampLastAccess + TIMEOUT > System.currentTimeMillis())
			return "";
		else
			return "textRedBold";
	}

	public boolean getTimeoutFlag() {
		if (timestampLastAccess + TIMEOUT > System.currentTimeMillis())
			return false;
		else
			return true;
	}

	public String getTimeoutFlagStyleClass() {
		if (timestampLastAccess + TIMEOUT > System.currentTimeMillis())
			return "";
		else
			return "textRedBold";
	}

	public Vector<State> getResponseStates() {
		if (responseStates == null)
			responseStates = new Vector<State>();
		return responseStates;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getCallCount() {
		return callCount;
	}

	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}

	public String getDateLastAccess() {
		return dateLastAccess;
	}

	public void setDateLastAccess(String dateLastAccess) {
		this.dateLastAccess = dateLastAccess;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getDateTimeLoggedOff() {
		return dateTimeLoggedOff;
	}

	public void setDateTimeLoggedOff(String dateTimeLoggedOff) {
		this.dateTimeLoggedOff = dateTimeLoggedOff;
	}

	public String getDateTimeLoggedOn() {
		return dateTimeLoggedOn;
	}

	public void setDateTimeLoggedOn(String dateTimeLoggedOn) {
		this.dateTimeLoggedOn = dateTimeLoggedOn;
	}

	public String getExceptionText() {
		return exceptionText;
	}

	public void setExceptionText(String exceptionText) {
		this.exceptionText = exceptionText;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getLangId() {
		return langId;
	}

	public void setLangId(String langId) {
		this.langId = langId;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public long getTimestampLastAccess() {
		return timestampLastAccess;
	}

	public void setTimestampLastAccess(long timestampLastAccess) {
		this.timestampLastAccess = timestampLastAccess;
	}

	public long getTimestampLoggedOff() {
		return timestampLoggedOff;
	}

	public void setTimestampLoggedOff(long timestampLoggedOff) {
		this.timestampLoggedOff = timestampLoggedOff;
	}

	public long getTimeUsedMiddleware() {
		return timeUsedMiddleware;
	}

	public void setTimeUsedMiddleware(long timeUsedMiddleware) {
		this.timeUsedMiddleware = timeUsedMiddleware;
	}

	public long getTimeUsedMiddlewareRequest() {
		return timeUsedMiddlewareRequest;
	}

	public void setTimeUsedMiddlewareRequest(long timeUsedMiddlewareRequest) {
		this.timeUsedMiddlewareRequest = timeUsedMiddlewareRequest;
	}

	public long getTimeUsedMiddlewareResponse() {
		return timeUsedMiddlewareResponse;
	}

	public void setTimeUsedMiddlewareResponse(long timeUsedMiddlewareResponse) {
		this.timeUsedMiddlewareResponse = timeUsedMiddlewareResponse;
	}

	public long getTimeUsedMiddlewareTotal() {
		return timeUsedMiddlewareTotal;
	}

	public void setTimeUsedMiddlewareTotal(long timeUsedMiddlewareTotal) {
		this.timeUsedMiddlewareTotal = timeUsedMiddlewareTotal;
	}

	public long getTimeUsedTotal() {
		return timeUsedTotal;
	}

	public void setTimeUsedTotal(long timeUsedTotal) {
		this.timeUsedTotal = timeUsedTotal;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserPid() {
		return userPid;
	}

	public void setUserPid(String userPid) {
		this.userPid = userPid;
	}

	public void setResponseStates(Vector<State> responseStates) {
		this.responseStates = responseStates;
	}

}
