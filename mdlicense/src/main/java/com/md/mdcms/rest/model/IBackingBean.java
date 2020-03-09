package com.md.mdcms.rest.model;

public interface IBackingBean {

	String getXlspth();

	void setXlspth(String value);

	String getJobNumber();

	void setJobNumber(String jobNumber);

	String getScreenTitle();

	void setScreenTitle(String value);

	State getRequestState();

	void setRequestState(com.md.mdcms.rest.model.State state);

	State getResponseState();

	void setResponseState(com.md.mdcms.rest.model.State state);

	ButtonBackingBean getButtonBackingBean();

	void setButtonBackingBean(ButtonBackingBean bbb);

	String getNextRequestCode();

	void setNextRequestCode(String nextRequestCode);

}
