package com.md.mdcms.cms.backingbean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.ApplUserBean;
import com.md.mdcms.base.ApplicationHelper;
import com.md.mdcms.exception.MiddlewareException;
import com.md.mdcms.iseries.ISeries;
import com.md.mdcms.model.LoginInformation;
import com.md.mdcms.model.State;
import com.md.mdcms.util.ResourceHandler;

public class PasswordChangeBackingBeanManager extends LoginInitBackingBeanManager {

	/** Log instance for this class. */
	private static final Log LOG = LogFactory.getLog(PasswordChangeBackingBeanManager.class);

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "passwordChangeBackingBean";

	private String userId;
	private String oldpassword;
	private String newpassword;
	private String newpasswordconfirm;

	private boolean backButtonRendered;

	private String jobNumber;

	public PasswordChangeBackingBeanManager() {
		super();
		ApplUserBean applUserBean = ApplicationHelper.getUserBean();

		if (applUserBean != null) {
			this.jobNumber = applUserBean.getCurrentJobNumber();

			ISeries iSeries = null;
			if (this.jobNumber != null) {
				iSeries = applUserBean.getISeriesSession(this.jobNumber);
				if (iSeries != null && iSeries.getAs400() != null) {
					this.backButtonRendered = true;
				}
			}
		}
	}

	public String back() {
		State responseState = getResponseState();
		if (responseState != null) {
			return responseState.getScreenThread();
		}

		return "doLoginInit";
	}

	public String getUserId() {
		if (userId == null || userId.equals("")) {
			userId = ApplicationHelper.getUserBean().getUserId();
		}
		return userId;
	}

	// public void setUserId(String userId) {
	// this.userId = userId;
	// }

	public String getNewpassword() {
		return newpassword;
	}

	public String getNewpasswordconfirm() {
		return newpasswordconfirm;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public void setNewpasswordconfirm(String newpasswordconfirm) {
		this.newpasswordconfirm = newpasswordconfirm;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String resetChange() {
		setOldpassword("");
		setNewpassword("");
		setNewpasswordconfirm("");
		return null;
	}

	public String change() {
		boolean error = false;
		if (oldpassword == null || oldpassword.equals("")) {
			// FacesMessage message = new FacesMessage(
			// FacesMessage.SEVERITY_ERROR,
			// ResourceHandler.getMessageResourceString("oldPassEmpty"),
			// ResourceHandler.getMessageResourceString("oldPassEmpty"));
			// FacesContext.getCurrentInstance()
			// .addMessage("oldpassword", message);
			error = true;
		}
		if (newpassword == null || newpassword.equals("")) {
			// FacesMessage message = new FacesMessage(
			// FacesMessage.SEVERITY_ERROR,
			// ResourceHandler.getMessageResourceString("newPassEmpty"),
			// ResourceHandler.getMessageResourceString("newPassEmpty"));
			// FacesContext.getCurrentInstance()
			// .addMessage("newpassword", message);
			error = true;
		}
		if (newpasswordconfirm == null || newpasswordconfirm.equals("")) {
			// FacesMessage message = new FacesMessage(
			// FacesMessage.SEVERITY_ERROR,
			// ResourceHandler
			// .getMessageResourceString("newPassConfirmEmpty"),
			// ResourceHandler
			// .getMessageResourceString("newPassConfirmEmpty"));
			// FacesContext.getCurrentInstance().addMessage("newpasswordconfirm",
			// message);
			error = true;
		}

		if (error) {
			return null;
		}

		if (newpassword.equals(newpasswordconfirm)) {
			ApplUserBean userBean = ApplicationHelper.getUserBean();
			String as400Message = null;

			as400Message = ApplicationHelper.getUserBean().changePassword(this.jobNumber, oldpassword, newpassword);

			if (!as400Message.equals("")) {
				// FacesMessage message = new FacesMessage(
				// FacesMessage.SEVERITY_ERROR, as400Message, as400Message);
				// FacesContext.getCurrentInstance().addMessage("", message);
				return null;
			} else {
				ApplicationHelper.getUserBean().addException(new MiddlewareException("", "10",
						ResourceHandler.getMessageResourceString("txtPassChangedSuccess")));

				userBean.setPassword(newpassword);
				// userBean.setUserId(userId);

				if (isBackButtonRendered()) {
					return null;
				} else {
					userBean.getUserSessions().remove(PASSWORD_EXPIRED);

					LoginInformation loginInformation = userBean.loginFromChange();
					as400Message = loginInformation.getLoginMessage();
					if (as400Message != null && !as400Message.equals("")) {
						userBean.setPassword("");
						userBean.setUserId("");
						return null;
					}

					setJobNumber(loginInformation.getJobNumber());

					// FacesContext facesContext = FacesContext
					// .getCurrentInstance();
					// ExternalContext extContext = facesContext
					// .getExternalContext();
					// HttpSession session = (HttpSession) extContext
					// .getSession(false);
					// ApplicationConfigurationBean.getInstance().getSessionIds()
					// .add(session.getId());

					// return doFirstRequest();
					return null;
				}
			}
			// } else {
			// FacesMessage message = new FacesMessage(
			// FacesMessage.SEVERITY_ERROR,
			// ResourceHandler
			// .getMessageResourceString("txtNoSession"),
			// ResourceHandler
			// .getMessageResourceString("txtNoSession"));
			// FacesContext.getCurrentInstance().addMessage("",
			// message);
			// FacesContext.getCurrentInstance().addMessage(
			// "", message);
			// return null;
			// }
		} else {
			// FacesMessage message = new
			// FacesMessage(FacesMessage.SEVERITY_ERROR,
			// ResourceHandler.getMessageResourceString("txtPassNotPassConfirm"),
			// ResourceHandler.getMessageResourceString("txtPassNotPassConfirm"));
			// FacesContext.getCurrentInstance().addMessage("newpassword",
			// message);
			// FacesContext.getCurrentInstance().addMessage("newpasswordconfirm",
			// message);
			return null;
		}
	}

	/**
	 * @return the backButtonRendered
	 */
	public boolean isBackButtonRendered() {
		return backButtonRendered;
	}

	/**
	 * @param backButtonRendered
	 *            the backButtonRendered to set
	 */
	public void setBackButtonRendered(boolean backButtonRendered) {
		this.backButtonRendered = backButtonRendered;
	}

}
