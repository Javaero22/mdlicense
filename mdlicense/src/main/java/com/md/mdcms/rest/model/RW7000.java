package com.md.mdcms.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.md.mdcms.cms.backingbean.RW7000BackingBeanManager;

@XmlRootElement
public class RW7000 extends RWCommon {

	private RW7000BackingBeanManager bbm;
	private String srlnbr;
	private String srlnbrLabel;

	private List<Navigation> navigations;

	public RW7000BackingBeanManager getBbm() {
		return bbm;
	}

	public void setBbm(RW7000BackingBeanManager bbm) {
		this.bbm = bbm;
	}

	public String getSrlnbr() {
		return srlnbr;
	}

	public void setSrlnbr(String srlnbr) {
		this.srlnbr = srlnbr;
	}

	public String getSrlnbrLabel() {
		return srlnbrLabel;
	}

	public void setSrlnbrLabel(String srlnbrLabel) {
		this.srlnbrLabel = srlnbrLabel;
	}

	public List<Navigation> getNavigations() {
		return navigations;
	}

	public void setNavigations(List<Navigation> navigations) {
		this.navigations = navigations;
	}

}
