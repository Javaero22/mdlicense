package com.md.mdcms.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.ElementList;

public class Header implements Serializable {

	private static final long serialVersionUID = -5126497925568151205L;

	@ElementList(inline = true, required = false)
	private List<Item> headerItems;

	public Header() {
		super();
	}

	public Header(List<Item> headerItems) {
		super();
		this.headerItems = headerItems;
	}

	public List<Item> getHeaderItems() {
		return headerItems;
	}

	public void setHeaderItems(List<Item> headerItems) {
		this.headerItems = headerItems;
	}

}
