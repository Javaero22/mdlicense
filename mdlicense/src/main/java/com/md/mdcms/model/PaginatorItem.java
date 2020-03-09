package com.md.mdcms.model;

public class PaginatorItem {

	private String pageNr;
	private boolean currentSelected;

	public PaginatorItem(String pageNr, boolean currentSelected) {
		super();
		this.pageNr = pageNr;
		this.currentSelected = currentSelected;
	}

	public boolean isCurrentSelected() {
		return currentSelected;
	}

	public boolean isNotCurrentSelected() {
		return !currentSelected;
	}

	public String getPageNr() {
		return pageNr;
	}

}
