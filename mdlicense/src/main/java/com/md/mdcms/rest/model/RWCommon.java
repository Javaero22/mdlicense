package com.md.mdcms.rest.model;

public class RWCommon implements IBackingBean {

	private ButtonBackingBean buttonBackingBean;

	private String nextRequestCode;

	private String xlspth;

	private String jobNumber;

	private String screenTitle;

	private String linkTitle;

	protected State requestState;
	protected State responseState;

	public String getXlspth() {
		return xlspth;
	}

	public void setXlspth(String xlspth) {
		this.xlspth = xlspth;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getScreenTitle() {
		return screenTitle;
	}

	public void setScreenTitle(String screenTitle) {
		this.screenTitle = screenTitle;
	}

	public String getLinkTitle() {
		return linkTitle;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public State getRequestState() {
		return requestState;
	}

	public void setRequestState(State requestState) {
		this.requestState = requestState;
	}

	public State getResponseState() {
		return responseState;
	}

	public void setResponseState(State responseState) {
		this.responseState = responseState;
	}

	public ButtonBackingBean getButtonBackingBean() {
		return buttonBackingBean;
	}

	public void setButtonBackingBean(ButtonBackingBean buttonBackingBean) {
		this.buttonBackingBean = buttonBackingBean;
	}

	public String getNextRequestCode() {
		return nextRequestCode;
	}

	public void setNextRequestCode(String nextRequestCode) {
		this.nextRequestCode = nextRequestCode;
	}

}
