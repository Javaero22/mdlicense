package com.md.mdcms.model;

public class XmlResponseObject {

	private long middlewareRequestTime;
	private long middlewareTime;
	private long middlewareResponseTime;
	private String xmlResponseString;

	public long getMiddlewareRequestTime() {
		return middlewareRequestTime;
	}

	public long getMiddlewareResponseTime() {
		return middlewareResponseTime;
	}

	public long getMiddlewareTime() {
		return middlewareTime;
	}

	public String getXmlResponseString() {
		return xmlResponseString;
	}

	public void setMiddlewareRequestTime(long middlewareRequestTime) {
		this.middlewareRequestTime = middlewareRequestTime;
	}

	public void setMiddlewareResponseTime(long middlewareResponseTime) {
		this.middlewareResponseTime = middlewareResponseTime;
	}

	public void setMiddlewareTime(long middlewareTime) {
		this.middlewareTime = middlewareTime;
	}

	public void setXmlResponseString(String xmlResponseString) {
		this.xmlResponseString = xmlResponseString;
	}

}
