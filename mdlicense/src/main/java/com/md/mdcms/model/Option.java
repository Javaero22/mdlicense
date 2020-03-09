package com.md.mdcms.model;

import org.simpleframework.xml.Attribute;

public class Option {

	@Attribute
	private String id;

	@Attribute
	private String value;

	public String getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
