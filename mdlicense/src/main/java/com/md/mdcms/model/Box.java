package com.md.mdcms.model;

import org.simpleframework.xml.Attribute;

public class Box {

	@Attribute
	private String id;

	@Attribute
	private String label;

	@Attribute(required = false)
	private String visible;

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getVisible() {
		return visible;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

}
