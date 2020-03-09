package com.md.mdcms.model;

import com.md.mdcms.backingbean.IBackingBean;

public class RestResponseObject {

	IBackingBean backingBeanManager = null;
	String jobNumber = null;

	public IBackingBean getBackingBeanManager() {
		return backingBeanManager;
	}

	public void setBackingBeanManager(IBackingBean backingBeanManager) {
		this.backingBeanManager = backingBeanManager;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

}
