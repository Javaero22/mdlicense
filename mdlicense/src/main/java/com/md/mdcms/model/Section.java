package com.md.mdcms.model;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class Section {

	@Attribute
	private String id;

	@Attribute
	private String value;

	@Attribute
	private String expand;

	@ElementList(inline = true)
	private List<Option> options;

	public String getExpand() {
		return expand;
	}

	public String getId() {
		return id;
	}

	public List<Option> getOptions() {
		return options;
	}

	public String getValue() {
		return value;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
