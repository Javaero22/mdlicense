package com.md.mdcms.model;

import com.md.mdcms.base.ApplicationHelper;

public class StringField extends PageField {

	private static final long serialVersionUID = 1L;

	public String getText() {
		return ApplicationHelper.getTextForListCode(this.getId(), this
				.getValue().toString());
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValue(Object object) {
		this.value = object.toString();
	}

	public String getHtml() {
		return value;
	}

	public void setHtml(String value) {
		this.value = value;
	}

	public String getPw() {
		return "**********";
	}

}
