package com.md.mdcms.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.md.mdcms.model.State;

@XmlRootElement
public class RW7000ResponseModel {

	public String jobNumber;
	public State responseState;
	public RW7000 backingBean;

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public State getResponseState() {
		return responseState;
	}

	public void setResponseState(State responseState) {
		this.responseState = responseState;
	}

	public RW7000 getBackingBean() {
		return backingBean;
	}

	public void setBackingBean(RW7000 backingBean) {
		this.backingBean = backingBean;
	}

}
