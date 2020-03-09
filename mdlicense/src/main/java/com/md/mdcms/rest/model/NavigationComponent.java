package com.md.mdcms.rest.model;

import java.util.Vector;

import com.md.mdcms.nav.INavigation;

public class NavigationComponent {

	private Vector<INavigation> requestcode;
	private Vector<INavigation> linklist;

	private String linkTitle;
	private String linklistTitle;

	public boolean getRequestcodeRendered() {
		if (requestcode != null && !requestcode.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public Vector<INavigation> getRequestcode() {
		if (requestcode == null)
			requestcode = new Vector<INavigation>();
		return requestcode;
	}

	public void setRequestcode(Vector<INavigation> requestcode) {
		this.requestcode = requestcode;
	}

	public boolean getLinklistRendered() {
		if (linklist != null && !linklist.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the requestLinklistNavigation
	 */
	public Vector<INavigation> getLinklist() {
		if (linklist == null)
			linklist = new Vector<INavigation>();
		return linklist;
	}

	/**
	 * @param requestLinklistNavigation
	 *            the requestLinklistNavigation to set
	 */
	public void setLinklist(Vector<INavigation> linklist) {
		this.linklist = linklist;
	}

	public String getLinkTitle() {
		return linkTitle;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public String getLinklistTitle() {
		return linklistTitle;
	}

	public void setLinklistTitle(String linklistTitle) {
		this.linklistTitle = linklistTitle;
	}

	public void clear() {
		this.requestcode = null;
		this.linklist = null;
	}

}
