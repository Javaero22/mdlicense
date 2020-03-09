package com.md.mdcms.rest.model;

public class StringField {

	private String value;

	private String id;

	private String label;

	private String editable;

	private String tooltip;

	private String visible;

	private boolean browserendered;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public boolean isBrowserendered() {
		return browserendered;
	}

	public void setBrowserendered(boolean browserendered) {
		this.browserendered = browserendered;
	}

}
