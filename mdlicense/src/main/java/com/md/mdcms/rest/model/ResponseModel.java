package com.md.mdcms.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseModel {

	private String jobNumber;
	private String screen;

	private IBackingBean backingBean;

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public IBackingBean getBackingBean() {
		return backingBean;
	}

	public void setBackingBean(IBackingBean backingBean) {
		this.backingBean = backingBean;
	}

}
