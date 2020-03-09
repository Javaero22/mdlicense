package com.md.mdcms.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogoffModel {

	private String logoffAddress;

	public String getLogoffAddress() {
		return logoffAddress;
	}

	public void setLogoffAddress(String logoffAddress) {
		this.logoffAddress = logoffAddress;
	}

}
