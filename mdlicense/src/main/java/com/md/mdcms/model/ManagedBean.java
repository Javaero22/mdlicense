package com.md.mdcms.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "managed-bean")
public class ManagedBean {

	@Element(required = false)
	private String description;

	@Element(name = "managed-bean-name")
	private String managedBeanName;

	@Element(name = "managed-bean-class")
	private String managedBeanClass;

	@Element(name = "managed-bean-scope")
	private String managedBeanScope;

	@ElementList(name = "managed-property", required = false, inline = true)
	private List<ManagedProperty> managedProperty;

	public String getDescription() {
		return description;
	}

	public String getManagedBeanClass() {
		return managedBeanClass;
	}

	public String getManagedBeanName() {
		return managedBeanName;
	}

	public String getManagedBeanScope() {
		return managedBeanScope;
	}

	public List<ManagedProperty> getManagedProperty() {
		return managedProperty;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setManagedBeanClass(String managedBeanClass) {
		this.managedBeanClass = managedBeanClass;
	}

	public void setManagedBeanName(String managedBeanName) {
		this.managedBeanName = managedBeanName;
	}

	public void setManagedBeanScope(String managedBeanScope) {
		this.managedBeanScope = managedBeanScope;
	}

	public void setManagedProperty(List<ManagedProperty> managedProperty) {
		this.managedProperty = managedProperty;
	}

}
