package com.md.mdcms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.md.mdcms.nav.INavigation;
import com.md.mdcms.rest.model.Navigation;

public class NavigationComponent {

	private List<Navigation> requestcodes;
	private List<Navigation> linklists;

	private String linkTitle;
	private String linklistTitle;

	public List<Navigation> getRequestcodes() {
		if (requestcodes == null) {
			requestcodes = new ArrayList<Navigation>();
		}
		return requestcodes;
	}

	public void setRequestcodes(List<Navigation> requestcodes) {
		this.requestcodes = requestcodes;
	}

	public List<Navigation> getLinklists() {
		if (linklists == null) {
			linklists = new ArrayList<Navigation>();
		}
		return linklists;
	}

	public void setLinklists(List<Navigation> linklists) {
		this.linklists = linklists;
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

}
