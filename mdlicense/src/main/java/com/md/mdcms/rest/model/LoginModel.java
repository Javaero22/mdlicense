package com.md.mdcms.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.md.mdcms.model.State;

@XmlRootElement
public class LoginModel {

	public String jobNumber;
	public State responseState;

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

}
