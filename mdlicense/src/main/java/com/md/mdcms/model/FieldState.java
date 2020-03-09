package com.md.mdcms.model;

public class FieldState {

	private boolean editable;
	private boolean visible;

	public FieldState(boolean editable, boolean visible) {
		super();
		this.editable = editable;
		this.visible = visible;
	}

	public boolean isEditable() {
		return editable;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
