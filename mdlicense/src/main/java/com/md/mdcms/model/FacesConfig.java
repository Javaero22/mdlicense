package com.md.mdcms.model;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "faces-config", strict = false)
public class FacesConfig {

	@ElementList(name = "managed-bean", inline = true)
	private List<ManagedBean> managedBean;

	@Element(name = "navigation-rule")
	private NavigationRule navigationRule;

	@Attribute
	private String version;

	@Attribute(name = "xmlns", required = false)
	private String xmlns;

	@Attribute(name = "xmlns:xi", required = false)
	private String xmlnsxi;

	@Attribute(name = "xmlns:xsi", required = false)
	private String xmlnsxsi;

	@Attribute(name = "xsi:schemaLocation", required = false)
	private String xsi;

	public List<ManagedBean> getManagedBean() {
		return managedBean;
	}

	public NavigationRule getNavigationRule() {
		return navigationRule;
	}

	public String getVersion() {
		return version;
	}

	public String getXmlns() {
		return xmlns;
	}

	public String getXmlnsxi() {
		return xmlnsxi;
	}

	public String getXmlnsxsi() {
		return xmlnsxsi;
	}

	public String getXsi() {
		return xsi;
	}

	public void setManagedBean(List<ManagedBean> managedBean) {
		this.managedBean = managedBean;
	}

	public void setNavigationRule(NavigationRule navigationRule) {
		this.navigationRule = navigationRule;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public void setXmlnsxi(String xmlnsxi) {
		this.xmlnsxi = xmlnsxi;
	}

	public void setXmlnsxsi(String xmlnsxsi) {
		this.xmlnsxsi = xmlnsxsi;
	}

	public void setXsi(String xsi) {
		this.xsi = xsi;
	}

}
