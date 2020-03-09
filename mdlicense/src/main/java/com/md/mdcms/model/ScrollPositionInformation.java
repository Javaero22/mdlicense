package com.md.mdcms.model;

public class ScrollPositionInformation {

	private String jobNumber;
	private String scrollToXY;

	/**
	 * @param jobNumber
	 * @param scrollToXY
	 */
	public ScrollPositionInformation(String jobNumber, String scrollToXY) {
		super();
		this.jobNumber = jobNumber;
		this.scrollToXY = scrollToXY;
	}

	/**
	 * @return the jobNumber
	 */
	public String getJobNumber() {
		return jobNumber;
	}

	/**
	 * @param jobNumber
	 *            the jobNumber to set
	 */
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	/**
	 * @return the scrollToXY
	 */
	public String getScrollToXY() {
		return scrollToXY;
	}

	/**
	 * @param scrollToXY
	 *            the scrollToXY to set
	 */
	public void setScrollToXY(String scrollToXY) {
		this.scrollToXY = scrollToXY;
	}

}
