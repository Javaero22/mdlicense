package com.md.mdcms.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Button implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final List<String> immediateButtons = Arrays.asList("back",
			"refresh", "reset");
	private String rc;
	private String id;
	private String label;
	private boolean rendered;

	public Button() {
		super();
	}

	public Button(String id, String rc, String label) {
		super();
		this.id = id;
		this.rc = rc;
		this.label = label;
		if ("".equals(this.label)) {
			this.rendered = false;
		} else {
			this.rendered = true;
		}
	}

	public boolean getImmediate() {
		if (immediateButtons.contains(this.rc.toLowerCase())) {
			return true;
		}
		return false;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getStyleClass() {
		String styleClass = "web_button";
		if (label.length() > 14) {
			styleClass += "2";
		} else if (label.length() > 7) {
			styleClass += "1";
		}
		return styleClass;
	}

	/**
	 * @return the rc
	 */
	public String getRc() {
		return rc;
	}

	/**
	 * @param rc
	 *            the rc to set
	 */
	public void setRc(String rc) {
		this.rc = rc;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

}
