package com.md.mdcms.model;

import java.util.List;

import org.simpleframework.xml.ElementList;

public class Linklist {

	@ElementList(inline = true, required = false)
	private List<Section> sections;

	@ElementList(inline = true, required = false)
	private List<Option> options;

	public List<Option> getOptions() {
		return options;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

}
