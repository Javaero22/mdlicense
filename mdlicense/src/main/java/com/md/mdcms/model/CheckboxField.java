package com.md.mdcms.model;

public class CheckboxField extends PageField {

	private static final long serialVersionUID = 1L;

	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getValue() {
		return String.valueOf(selected);
	}

	public void setValue(boolean value) {
		this.selected = value;
	}

	public void setValue(String value) {
		if (value != null) {
			this.selected = Boolean.valueOf(value.toString());
		} else {
			this.selected = false;
		}
	}

	public void setValue(Object value) {
		this.selected = Boolean.valueOf(value.toString());
	}

}
