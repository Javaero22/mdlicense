package com.md.mdcms.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "managed-property")
public class ManagedProperty {

	@Element(name = "property-name")
	private String propertyName;

	@Element(name = "property-class")
	private String propertyClass;

	@Element(name = "list-entries", required = false)
	private String listEntries;

	@Element(required = false)
	private String value;

	public String getListEntries() {
		return listEntries;
	}

	public String getPropertyClass() {
		return propertyClass;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getValue() {
		return value;
	}

	public void setListEntries(String listEntries) {
		this.listEntries = listEntries;
	}

	public void setPropertyClass(String propertyClass) {
		this.propertyClass = propertyClass;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
