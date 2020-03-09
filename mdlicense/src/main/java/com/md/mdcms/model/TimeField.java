package com.md.mdcms.model;

import java.text.DecimalFormat;

public class TimeField extends PageField {

	private static final long serialVersionUID = 1L;

	private static DecimalFormat decFormat = new DecimalFormat("000000");

	public String getValue() {
		String formatedValue = decFormat.format(Integer.valueOf(value));
		return formatedValue;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHtml() {
		String formatedValue = decFormat.format(Integer.valueOf(value));
		return formatedValue;
	}

	public void setHtml(String value) {
		this.value = value;
	}

}
