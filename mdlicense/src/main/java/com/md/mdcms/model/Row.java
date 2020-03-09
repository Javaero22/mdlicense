package com.md.mdcms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class Row implements Serializable {

	private static final long serialVersionUID = 6365790617984248283L;

	@Attribute(required = false)
	private String nr;

	@ElementList(inline = true, required = false)
	private List<Item> rowItems;

	public Row() {
		super();
	}

	public Row(List<Item> rowItems) {
		super();
		this.rowItems = rowItems;
	}

	public Row(String nr, ArrayList<Item> rowItems) {
		super();
		this.nr = nr;
		this.rowItems = rowItems;
	}

	public List<Item> getRowItems() {
		return rowItems;
	}

	public void setRowItems(List<Item> rowItems) {
		this.rowItems = rowItems;
	}

	public String getNr() {
		return nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

}
