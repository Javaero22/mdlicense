package com.md.mdcms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.md.mdcms.nav.Navigation;

public class Navigations {

	private List<Navigation> requestcode;

	private String linkTitle;

	public List<Navigation> getRequestcode() {
		if (requestcode == null) {
			requestcode = new ArrayList<Navigation>();
		}
		return requestcode;
	}

	public String getLinkTitle() {
		return linkTitle;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

}
