package com.md.mdcms.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ApplicationConfiguration {

	private String environment;
	private Boolean ldapconverttocn;
	private String ldapserver;
	private String ldapprinciple;
	private String ldappassword;
	private String ldapsearchbase;
	private String dateFormat;
	private String timeSeparator;
	private String gmtOffsetForTS;
	private String locationForTS;
	private String firstDayOfWeek;
	private String ireflink;
	private String iref;
	private String logoff;
	private String contactLabel;
	private String contactLink;
	private String contactPhone;
	private String networkUserProviderClass;
	private String corporateIdentity;
	private String ldapDomainController;

	private String contactText;
	private String contactUs;

	private String buildDate;
	private String version;

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Boolean getLdapconverttocn() {
		return ldapconverttocn;
	}

	public void setLdapconverttocn(Boolean ldapconverttocn) {
		this.ldapconverttocn = ldapconverttocn;
	}

	public String getLdapserver() {
		return ldapserver;
	}

	public void setLdapserver(String ldapserver) {
		this.ldapserver = ldapserver;
	}

	public String getLdapprinciple() {
		return ldapprinciple;
	}

	public void setLdapprinciple(String ldapprinciple) {
		this.ldapprinciple = ldapprinciple;
	}

	public String getLdappassword() {
		return ldappassword;
	}

	public void setLdappassword(String ldappassword) {
		this.ldappassword = ldappassword;
	}

	public String getLdapsearchbase() {
		return ldapsearchbase;
	}

	public void setLdapsearchbase(String ldapsearchbase) {
		this.ldapsearchbase = ldapsearchbase;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getTimeSeparator() {
		return timeSeparator;
	}

	public void setTimeSeparator(String timeSeparator) {
		this.timeSeparator = timeSeparator;
	}

	public String getGmtOffsetForTS() {
		return gmtOffsetForTS;
	}

	public void setGmtOffsetForTS(String gmtOffsetForTS) {
		this.gmtOffsetForTS = gmtOffsetForTS;
	}

	public String getLocationForTS() {
		return locationForTS;
	}

	public void setLocationForTS(String locationForTS) {
		this.locationForTS = locationForTS;
	}

	public String getFirstDayOfWeek() {
		return firstDayOfWeek;
	}

	public void setFirstDayOfWeek(String firstDayOfWeek) {
		this.firstDayOfWeek = firstDayOfWeek;
	}

	public String getIreflink() {
		return ireflink;
	}

	public void setIreflink(String ireflink) {
		this.ireflink = ireflink;
	}

	public String getIref() {
		return iref;
	}

	public void setIref(String iref) {
		this.iref = iref;
	}

	public String getLogoff() {
		return logoff;
	}

	public void setLogoff(String logoff) {
		this.logoff = logoff;
	}

	public String getContactLabel() {
		return contactLabel;
	}

	public void setContactLabel(String contactLabel) {
		this.contactLabel = contactLabel;
	}

	public String getContactLink() {
		return contactLink;
	}

	public void setContactLink(String contactLink) {
		this.contactLink = contactLink;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getNetworkUserProviderClass() {
		return networkUserProviderClass;
	}

	public void setNetworkUserProviderClass(String networkUserProviderClass) {
		this.networkUserProviderClass = networkUserProviderClass;
	}

	public String getCorporateIdentity() {
		return corporateIdentity;
	}

	public void setCorporateIdentity(String corporateIdentity) {
		this.corporateIdentity = corporateIdentity;
	}

	public String getLdapDomainController() {
		return ldapDomainController;
	}

	public void setLdapDomainController(String ldapDomainController) {
		this.ldapDomainController = ldapDomainController;
	}

}
