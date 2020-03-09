package com.md.mdcms.model;

import java.util.List;

import org.simpleframework.xml.ElementList;

public class Tablist {

	@ElementList(inline = true)
	private List<Tab> tabList;

	public List<Tab> getTabList() {
		return tabList;
	}

	public void setTabList(List<Tab> tabList) {
		this.tabList = tabList;
	}

}
