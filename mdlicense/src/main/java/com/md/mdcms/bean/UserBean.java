package com.md.mdcms.bean;

import java.io.Serializable;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class UserBean implements Serializable {

	private static final String emptyString = "";

	/** private logger instance */
	private static final Log LOG = LogFactory.getLog(UserBean.class);

	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 7155630454799180680L;

	public static final String BEAN_NAME = "userBean";

	public static final String LOGON_NAV_ID = "logon";

	public static final String LOGOFF_NAV_ID = "logoff";

	private boolean loggedOn = false;

	private Locale locale;

	private String expandedNavigation;

	public UserBean() {
	}

	public static UserBean getUserBean() {
		// return (UserBean) JsfSupporter.findManagedBean(BEAN_NAME);
		return null;
	}

	public void setLoggedOn(boolean loggedOn) {
		if (loggedOn == false) {
			// Object session = FacesContext.getCurrentInstance()
			// .getExternalContext().getSession(false);
			Object session = null;
			if (session != null && session instanceof HttpSession) {
				HttpSession httpSession = (HttpSession) session;
				if (!httpSession.isNew()) {
					httpSession.invalidate();
				}
			}
			// JsfSupporter.invalidateView();
		}
		this.loggedOn = loggedOn;
	}

	public boolean isLoggedOn() {
		return loggedOn;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getExpandedNavigation() {
		return expandedNavigation;
	}

	public void setExpandedNavigation(String expandedNavigation) {
		this.expandedNavigation = expandedNavigation;
	}

	public String done() {
		// FacesContext.getCurrentInstance().responseComplete();
		return null;
	}

}
