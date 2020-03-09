package com.md.mdcms.backingbean;

import java.io.File;

import com.md.mdcms.base.IConstants;
import com.md.mdcms.model.PageField;
import com.md.mdcms.model.State;

public class StartConfigurationBean implements IConstants {

	private static StartConfigurationBean instance = null;

	public static final String BEAN_NAME = "startConfiguration";
	private static final String midrangeAddress = "http://www.midrangedynamics.com";
	private static final String userGuidePath = "/downloads/MDWorkflow_User_Guide_";

	private String version;
	private String buildDate;
	private String profil;
	private String xmlfilepath;
	private String initstateaction;
	private String initstatelangid;
	private String initstatefunction;
	private String initstaterequestcode;
	private String initstatescreen;
	private String logoff;
	private String threads;

	private State initialState;
	private String userGuideLink;

	public StartConfigurationBean() {
		super();
		if (!ApplicationConfigurationBean.getInstance().isExternalUser()) {
			// logoff = FacesContext.getCurrentInstance().getExternalContext()
			// .getRequestContextPath();
		} else {
			this.logoff = ApplicationConfigurationBean.getInstance().getLogoff();
			if (this.logoff == null || "".equals(this.logoff)) {
				this.logoff = midrangeAddress;
			}
		}

		this.version = "LIC";
		this.buildDate = "20160322";
		this.profil = "test";
		this.xmlfilepath = "xml";
		this.threads = "01";
		this.initstateaction = "";
		this.initstatelangid = "E";
		this.initstatefunction = "W.LIC.SEL"; // off.lst -> efl
		this.initstaterequestcode = "";
		this.initstatescreen = "";
	}

	public static StartConfigurationBean getInstance() {
		if (instance == null) {
			synchronized (StartConfigurationBean.class) {
				if (instance == null) {
					instance = new StartConfigurationBean();
				}
			}
		}
		return instance;
	}

	/*
	 * http://www.midrangedynamics.com/downloads/MDWorkflow_User_Guide_v6.8.pdf
	 */
	public String getUserGuideLink() {
		if (this.userGuideLink == null) {
			this.userGuideLink = midrangeAddress + userGuidePath + getVersion() + ".pdf";
		}
		return this.userGuideLink;
	}

	public String getLogoff() {
		return logoff;
	}

	/**
	 * @return
	 */
	public boolean isTest() {
		boolean test = "test".equals(getProfil()) ? true : false;
		PageField.TEST = test;
		return test;
	}

	public State getInitialState() {
		if (initialState == null) {
			initialState = new State(getInitstateaction(), getInitstatefunction(), getInitstatelangid(),
					getInitstaterequestcode(), getInitstatescreen(), "");
		}
		return initialState;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		if (version != null)
			return version;
		return "";
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the buildDate
	 */
	public String getBuildDate() {
		if (buildDate != null)
			return buildDate;
		return "";
	}

	/**
	 * @param buildDate
	 *            the buildDate to set
	 */
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	/**
	 * @return the threads
	 */
	public String getThreads() {
		return threads;
	}

	/**
	 * @param threads
	 *            the threads to set
	 */
	public void setThreads(String threads) {
		this.threads = threads;
	}

	public String getInitstateaction() {
		if (initstateaction != null && !"null".equals(initstateaction))
			return initstateaction;
		return "";
	}

	public String getInitstatefunction() {
		if (initstatefunction != null)
			return initstatefunction;
		return "";
	}

	public String getInitstaterequestcode() {
		if (initstaterequestcode != null && !"null".equals(initstaterequestcode))
			return initstaterequestcode;
		return "";
	}

	public String getInitstatescreen() {
		if (initstatescreen != null && !"null".equals(initstatescreen))
			return initstatescreen;
		return "";
	}

	public String getInitstatelangid() {
		return initstatelangid;
	}

	public String getProfil() {
		return profil;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public void setInitstateaction(String initstateaction) {
		this.initstateaction = initstateaction;
	}

	public void setInitstatefunction(String initstatefunction) {
		this.initstatefunction = initstatefunction;
	}

	public void setInitstatelangid(String initstatelangid) {
		this.initstatelangid = initstatelangid;
	}

	public void setInitstaterequestcode(String initstaterequestcode) {
		this.initstaterequestcode = initstaterequestcode;
	}

	public void setInitstatescreen(String initstatescreen) {
		this.initstatescreen = initstatescreen;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}

	public String getXmlfilepath() {
		return xmlfilepath;
	}

	public void setXmlfilepath(String xmlfilepath) {
		if (this.xmlfilepath == null) {
			String filler = "";
			if (!"".equals(ApplicationConfigurationBean.getInstance().getHostEnv())) {
				filler = "_";
			}
			this.xmlfilepath = xmlfilepath + filler + ApplicationConfigurationBean.getInstance().getHostEnv()
					+ File.separatorChar;
		}
	}

}
