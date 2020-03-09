package com.md.mdcms.model;

import org.simpleframework.xml.Attribute;

public class Tab {

	@Attribute
	private String id;

	@Attribute
	private String value;

	@Attribute(required = false)
	private String active;

	private boolean rendered;

	public Tab() {
		super();
	}

	public Tab(String id) {
		super();
		this.id = id;
		this.value = id;
	}

	public String getActive() {
		return active;
	}

	public String getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

}
