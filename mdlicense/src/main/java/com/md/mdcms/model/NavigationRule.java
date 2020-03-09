package com.md.mdcms.model;

import java.util.List;

import org.simpleframework.xml.ElementList;

public class NavigationRule {

	@ElementList(name = "navigation-case", inline = true)
	private List<NavigationCase> navigationCase;

	public List<NavigationCase> getNavigationCase() {
		return navigationCase;
	}

	public void setNavigationCase(List<NavigationCase> navigationCase) {
		this.navigationCase = navigationCase;
	}

}
