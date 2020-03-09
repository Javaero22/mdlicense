package com.md.mdcms.model;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;

public class Caption implements Serializable {

	private static final long serialVersionUID = 1892488087953838132L;

	@Attribute
	private String value;

	public Caption() {
		super();
	}

	public Caption(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
