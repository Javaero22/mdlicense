package com.md.mdcms.cms.backingbean;

import java.security.Principal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.ApplicationConfigurationBean;
import com.md.mdcms.backingbean.StartConfigurationBean;
import com.md.mdcms.base.ApplicationHelper;
import com.md.mdcms.base.ILoginHelper;
import com.md.mdcms.exception.MiddlewareException;
import com.md.mdcms.model.LoginInformation;
import com.md.mdcms.model.State;
import com.md.mdcms.model.User;
import com.md.mdcms.rest.model.ApplUserBean;
import com.md.mdcms.util.ResourceHandler;

public class LoginInitBackingBeanManager extends MdBackingBean {

	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(LoginInitBackingBeanManager.class);

	private static final String NOSYSUSERPW = "System user/password not available.";
	private static final String LOGINNOTPOSSIBLE = "login not possible.";
	private static final String EXTERNALLOGINNOTSUCCESSFUL = "External login not successful.";
	private static final String SWITCHEDTOISERIES = "Switched to iseries user login.";
	private static final String NOSYSUSERPWISERIESLOGIN = "System user/password not available. Please contact administrator.";
	private static final String UNABLETOCONNECTSYSTEM = "Unable to connect to AS/400 using provided systemUser and systemPw property values";

	public static final String BEAN_NAME = "loginInitBackingBean";

	private static String FORMNAME = "md:";

	private String link;
	private String screen;
	private String location;
	private String projectid;
	private String taskid;
	private String subtaskid;
	private String application;
	private String rfp;

	private boolean fromLoginBtn;

	private User mdcmsUser;

	private HttpServletRequest request;

	private ApplUserBean userBean;

	public LoginInitBackingBeanManager(SecurityContext securityContext, HttpServletRequest request) {
		super(false);
		synchronized (this.getClass()) {
			LOG.debug("Initialization");
			this.request = request;
			String outcome = null;

			// showSystem();

			// FacesContext facesContext = FacesContext.getCurrentInstance();
			// ExternalContext extContext = facesContext.getExternalContext();
			// HttpSession session = (HttpSession) extContext.getSession(false);

			/*
			 * Principal / remoteUser
			 */
			String remoteUser = null;

			Principal principal = securityContext.getUserPrincipal();
			if (principal != null) {
				remoteUser = principal.getName();
				LOG.info("RemoteUser: " + remoteUser);
			}
			// String remoteUser = extContext.getRemoteUser();

			// Principal principal = extContext.getUserPrincipal();
			// if (principal != null) {
			// String name = principal.getName();
			// if (name != null) {
			// // System.out.println("Principal name: " + name);
			// LOG.info("Principal name: " + name);
			// }
			// }
			/*
			 * Principal / remoteUser
			 */

			/*
			 * is request from a link?
			 */
			// getRequestParameter(extContext);

			// ApplicationHelper.getUserBean().clearLoginStatus();

			if (iSeriesPropertiesAvailable()) {
				mdcmsUser = new User();
				mdcmsUser.setUserId("");
				mdcmsUser.setPassword("");
				mdcmsUser.setUserType(ApplicationConfigurationBean.getInstance().getUserType());

				// String sessionUserTypeValue = (String)
				// session.getAttribute("userType");
				String sessionUserTypeValue = null;
				if (sessionUserTypeValue != null) {
					mdcmsUser.setUserType(sessionUserTypeValue);
				}

				/*
				 * no system user and/or password available -> switch to iseries
				 * userType
				 */
				if (!mdcmsUser.getUserType().equals(ApplicationConfigurationBean.ISERIES)) {

					// LoginHelper available
					if (remoteUser != null) {
						LOG.info("Remote user: " + remoteUser);

						ILoginHelper loginHelper = ApplicationHelper.getLoginHelper();

						if (loginHelper != null) {
							if (!loginHelper.isAllowedTo(remoteUser)) {
								mdcmsUser.setUserId("");
								mdcmsUser.setPassword("");

								mdcmsUser.setUserType(ApplicationConfigurationBean.ISERIES);
								// session.setAttribute("userType",
								// ApplicationConfigurationBean.ISERIES);

								outcome = "userLoginInit";
							}
						} else {
							LOG.error("LoginHelper not implemented or not existing");
						}
					}

					if (ApplicationConfigurationBean.getInstance().hasNoSystemUserPassword()) {
						MiddlewareException ex = new MiddlewareException("", "30", NOSYSUSERPW);
						ApplicationHelper.getUserBean().addException(ex);

						ex = new MiddlewareException("", "30",
								ApplicationConfigurationBean.getInstance().getUserType() + " " + LOGINNOTPOSSIBLE);
						ApplicationHelper.getUserBean().addException(ex);

						ex = new MiddlewareException("", "30", SWITCHEDTOISERIES);
						ApplicationHelper.getUserBean().addException(ex);

						// FacesMessage message = new FacesMessage(
						// FacesMessage.SEVERITY_ERROR, NOSYSUSERPW,
						// NOSYSUSERPW);
						// facesContext.addMessage("", message);

						mdcmsUser.setUserId("");
						mdcmsUser.setPassword("");

						mdcmsUser.setUserType(ApplicationConfigurationBean.ISERIES);
						// session.setAttribute("userType",
						// ApplicationConfigurationBean.ISERIES);

						outcome = "userLoginInit";
					}
				}

				/*
				 * session exists
				 */
				/*
				 * implementing own session management
				 */
				// if
				// (ApplicationConfigurationBean.getInstance().getSessionIds().contains(session.getId())
				// && ApplicationHelper.getUserBean().plausiMdcmsUser()) {
				if (mdcmsUser.isIseriesUser()) {
					if (ApplicationConfigurationBean.getInstance().getUserUser().equals("system")) {
						mdcmsUser.setUserId(ApplicationConfigurationBean.getInstance().getSystemUser());
						mdcmsUser.setEncryptedPassword(ApplicationConfigurationBean.getInstance().getSystemPw());
						outcome = login();
					}
				} else if (1 == 1) {

					// this.mdcmsUser =
					// ApplicationHelper.getUserBean().getMdcmsUser();
					outcome = login();

					// outcome = doDirectLogin();
				} else if (mdcmsUser.isExternalUser()) {
					outcome = externalUserLogin();
					if (outcome != null && outcome.equals("userLoginInit")) {
						// session.setAttribute("userType",
						// ApplicationConfigurationBean.ISERIES);
					}
				} else if (mdcmsUser.isMdsecUser()) {
					outcome = mdsecUserLogin();
					if (outcome != null && outcome.equals("userLoginInit")) {
						// session.setAttribute("userType",
						// ApplicationConfigurationBean.ISERIES);
					}
				}
			} else {
				outcome = "contactAdmin";
			}

			// HttpServletResponse httpResponse = (HttpServletResponse)
			// extContext.getResponse();

			if (outcome != null) {
				// try {
				if (outcome.startsWith("RW0001")) {
					// session.removeAttribute("userType");
				}
				if (!outcome.contains(".jsf")) {
					// httpResponse.sendRedirect(outcome + ".jsf");
				} else {
					// httpResponse.sendRedirect(outcome);
				}
				// } catch (IOException e) {
				// if (LOG.isInfoEnabled()) {
				// LOG.fatal("sendRedirect error", e);
				// }
				// }
			}
			/*
			 * try { HttpServletResponse httpResponse = (HttpServletResponse)
			 * extContext .getResponse();
			 * 
			 * httpResponse .sendRedirect("error/multipleBrowser.jsf" +
			 * sb.toString()); } catch (IOException e) { if
			 * (LOG.isInfoEnabled()) { LOG.fatal("sendRedirect error", e); } } }
			 */
		}
	}

	private boolean iSeriesPropertiesAvailable() {
		boolean noError = true;
		// ApplUserBean applUserBean = ApplicationHelper.getUserBean();

		/*
		 * host
		 */
		if (ApplicationConfigurationBean.getInstance().isNotHostAvailable()) {
			// applUserBean.addToLoginStatus("hostNotSetContactAdmin");
			noError = false;
		}

		/*
		 * userType
		 */
		if (ApplicationConfigurationBean.getInstance().isNotCorrectUserType()) {
			// applUserBean.addToLoginStatus("userTypeSetIncorrect");
			noError = false;
		}
		return noError;
	}

	/**
	 * 
	 */
	private void showSystem() {
		Map<String, String> env = System.getenv();
		Set<String> keySet = env.keySet();
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = env.get(key);
			System.out.println("Key: " + key + " / value: " + value);
		}
	}

	/**
	 * @param extContext
	 */
	// private void getRequestParameter(ExternalContext extContext) {
	// Map<String, String> requestParameterMap =
	// extContext.getRequestParameterMap();
	//
	// setLink(getRequestValueFor(requestParameterMap, "link"));
	// setScreen(getRequestValueFor(requestParameterMap, "screen"));
	// setProjectid(getRequestValueFor(requestParameterMap, "projectId"));
	// setTaskid(getRequestValueFor(requestParameterMap, "taskId"));
	// setSubtaskid(getRequestValueFor(requestParameterMap, "subtaskId"));
	// setLocation(getRequestValueFor(requestParameterMap, "location"));
	// setApplication(getRequestValueFor(requestParameterMap, "application"));
	// setRfp(getRequestValueFor(requestParameterMap, "RFP"));
	//
	// if (LOG.isInfoEnabled()) {
	// LOG.info("link: " + getLink());
	// LOG.info("screen: " + getScreen());
	// LOG.info("projectid: " + getProjectid());
	// LOG.info("taskid: " + getTaskid());
	// LOG.info("subtaskid: " + getSubtaskid());
	// LOG.info("location: " + getLocation());
	// LOG.info("application: " + getApplication());
	// LOG.info("rfp: " + getRfp());
	// }
	//
	// if ((link != null && !"".equals(link) && screen != null &&
	// !"".equals(getScreen()))
	// && (projectid != null && !"".equals(getProjectid()))
	// // && (taskid != null && !"".equals(getTaskid()))
	// || (location != null && !"".equals(location) && application != null &&
	// !"".equals(application)
	// && rfp != null && !"".equals(rfp))) {
	// setLink("true");
	// } else {
	// setLink("false");
	// }
	// }

	/**
	 * @param requestVariable
	 * @return
	 */
	private String getRequestValueFor(Map<String, String> requestParameterMap, String parameterName) {
		String parameterValue = requestParameterMap.get(parameterName);
		if (parameterValue == null) {
			parameterName = FORMNAME + parameterName;
			parameterValue = requestParameterMap.get(parameterName);
		}
		return parameterValue;
	}

	private String doDirectLogin() {
		String outcome = null;
		StringBuffer sb = new StringBuffer();
		if (link != null && !"".equals(link)) {
			sb.append("?link=");
			sb.append(link);

			if (screen != null && !"".equals(screen)) {
				sb.append("&screen=");
				sb.append(screen);
			}
			if (projectid != null && !"".equals(projectid)) {
				sb.append("&projectId=");
				sb.append(projectid);
			}
			if (taskid != null && !"".equals(taskid)) {
				sb.append("&taskId=");
				sb.append(taskid);
			}
			if (subtaskid != null && !"".equals(subtaskid)) {
				sb.append("&subtaskId=");
				sb.append(subtaskid);
			}
			if (location != null && !"".equals(location)) {
				sb.append("&location=");
				sb.append(location);
			}
			if (application != null && !"".equals(application)) {
				sb.append("&application=");
				sb.append(application);
			}
			if (rfp != null && !"".equals(rfp)) {
				sb.append("&RFP=");
				sb.append(rfp);
			}
		}

		// ApplUserBean userBean = ApplicationHelper.getUserBean();

		// important
		outcome = doFirstRequest();

		LOG.info("outcome from doRequest: " + outcome);

		if ("threadmax".equals(outcome)) {
			outcome = "threadmax.jsf" + sb.toString();
			String loginMessage = "Multiple thread in session browser problem!";

			MiddlewareException ex = new MiddlewareException("", "30", loginMessage);
			ApplicationHelper.getUserBean().addException(ex);

			// FacesContext facesContext = FacesContext.getCurrentInstance();
			//
			// FacesMessage message = new
			// FacesMessage(FacesMessage.SEVERITY_ERROR, loginMessage,
			// loginMessage);
			// facesContext.addMessage("", message);
		}
		return outcome;
	}

	/**
	 * 
	 */
	private String externalUserLogin() {
		String systemUser = ApplicationConfigurationBean.getInstance().getSystemUser();
		String encryptedPw = ApplicationConfigurationBean.getInstance().getSystemPw();
		if (LOG.isInfoEnabled()) {
			LOG.info("External verify sysUser and sysPw : " + systemUser + "/" + encryptedPw);
		}

		mdcmsUser.setUserId(systemUser);
		mdcmsUser.setEncryptedPassword(encryptedPw);

		if (LOG.isInfoEnabled()) {
			LOG.info("Try external user login : " + mdcmsUser.getUserId());
		}

		String outcome = login();
		if (outcome == null) {
			mdcmsUser.setUserId("");
			mdcmsUser.setPassword("");
			outcome = "userLoginInit";
		}
		return outcome;
	}

	/**
	 * 
	 */
	protected String mdsecUserLogin() {
		String systemUser = ApplicationConfigurationBean.getInstance().getSystemUser();
		String encryptedPw = ApplicationConfigurationBean.getInstance().getSystemPw();
		if (LOG.isInfoEnabled()) {
			LOG.info("Mdsec verify sysUser and sysPw : " + systemUser + "/" + encryptedPw);
		}

		String outcome = null;
		LOG.info("START MDSEC PRELOGIN");
//		ApplUserBean userBean = ApplicationHelper.getUserBean();
		String openMessage = userBean.mdsecPreOpen(mdcmsUser);
		if (openMessage != null && !"".equals(openMessage)) {
			String[] exception = openMessage.split(";");
			if (exception[0].equals("pw")) {
			} else if (exception[0].equals("iseries")) {
			} else if (exception[0].equals("ex")) {
			} else {
			}

			MiddlewareException ex = new MiddlewareException("", "30", exception[0]);
			ApplicationHelper.getUserBean().addException(ex);

			// FacesMessage message = new FacesMessage(
			// FacesMessage.SEVERITY_ERROR, exception[1], exception[1]);
			// facesContext.addMessage("", message);

			ex = new MiddlewareException("", "30", SWITCHEDTOISERIES);
			ApplicationHelper.getUserBean().addException(ex);

			outcome = "userLoginInit";
		}
		return outcome;
	}

	/**
	 * 
	 */
	private String doFirstRequest() {
		LOG.info("First request | Link: " + getLink());

		prepareRequestState();

		// initial the first requestState
		setResponseState(getRequestState());

		// ApplUserBean userBean = ApplicationHelper.getUserBean();
		userBean.setUserLanguage(getRequestState().getLangId());
		userBean.setRequestBackingBean(this);

		String outcome = this.userBean.processMdFunction().getOutcome();

		this.responseState.setScreen(outcome);

		return outcome;
	}

	private void prepareRequestState() {
		State requestState = new State();

		/*
		 * if opened through a link
		 */
		if ("true".equals(getLink())) {
			boolean hasNoLinkVaues = true;
			requestState.setFunction("GETLINK");
			requestState.setLangId("E");
			requestState.setScreen(this.screen);
			requestState.setRequestCode(BLANK);

			if (this.projectid != null && !"".equals(this.projectid)) {
				requestState.setAction("EDIT");
				requestState.setAlpha1(this.projectid);
				// requestState.setRequestCode(getProjectid());
				hasNoLinkVaues = false;
			}

			if (this.taskid != null && !"".equals(this.taskid)) {
				// requestState.setThread(getTaskid());
				requestState.setNumeric1(this.taskid);
				hasNoLinkVaues = false;
			}

			if (this.subtaskid != null && !"".equals(this.subtaskid)) {
				requestState.setNumeric2(this.subtaskid);
				// requestState.setAction(getSubtaskid());
				hasNoLinkVaues = false;
			}

			if (this.application != null && !"".equals(this.application)) {
				requestState.setAlpha1(this.application);
				// requestState.setRequestCode(getApplication());
				hasNoLinkVaues = false;
			}

			if (this.location != null && !"".equals(this.location)) {
				requestState.setAlpha2(this.location);
				// requestState.setAction(getLocation());
				hasNoLinkVaues = false;
			}

			if (this.rfp != null && !"".equals(this.rfp)) {
				requestState.setNumeric1(this.rfp);
				// requestState.setThread(getRfp());
				hasNoLinkVaues = false;
			}

			if (hasNoLinkVaues) {
				LOG.info("there are no link values, get the initial state");
				/*
				 * the default init state
				 */
				State initState = StartConfigurationBean.getInstance().getInitialState();
				requestState = initState;
			}
		} else {
			/*
			 * the default init state
			 */
			LOG.info("Set the initital state:");
			State initState = StartConfigurationBean.getInstance().getInitialState();
			requestState = initState;
			LOG.info("Action      : " + requestState.getAction());
			LOG.info("Function    : " + requestState.getFunction());
			LOG.info("LangId      : " + requestState.getLangId());
			LOG.info("RequestCode : " + requestState.getRequestCode());
			LOG.info("Screen      : " + requestState.getScreen());
		}

		// requestState.setDateTime(Functions.dateTime());

		setRequestState(requestState);
	}

	public String login() {
		// synchronized (this.getClass()) {
		LOG.info("START LOGIN");
		boolean error = false;

		if (!mdcmsUser.isExternalUser()) {
			error = internalUserLogin();
		}

		if (!error) {
			LOG.info("userId / pw provided");

			this.userBean = null;

			HttpSession session = request.getSession(false);
			if (session == null) {
				session = request.getSession(true);
			} else {
				this.userBean = (ApplUserBean) session.getAttribute(ApplUserBean.BEAN_NAME);
			}

			if (this.userBean == null) {
				this.userBean = new ApplUserBean();
			}
			session.setAttribute(ApplUserBean.BEAN_NAME, this.userBean);

			// FacesContext facesContext =
			// FacesContext.getCurrentInstance();
			// ExternalContext extContext =
			// facesContext.getExternalContext();
			// HttpSession session = (HttpSession)
			// extContext.getSession(false);
			// if
			// (!ApplicationConfigurationBean.getInstance().getSessionIds()
			// .contains(session.getId())) {

			LoginInformation loginInformation = userBean.login(mdcmsUser);
			String loginMessage = loginInformation.getLoginMessage();

			if (loginMessage.startsWith("pw_exp")) {
				return passwordChange();
			}

			if (!loginMessage.equals("")) {
				if (!"userLoginInit".equals(loginMessage)) {
					addNokLoginMessage(loginMessage);
					return null;
				}
				return loginMessage;
			} else {
				LOG.info("login ok");
				setJobNumber(loginInformation.getJobNumber());

				// ApplicationConfigurationBean.getInstance().getSessionIds().add(session.getId());

				return doFirstRequest();
			}
		} else {
			return null;
		}
		// }
	}

	/**
	 * @return
	 */
	private void addNokLoginMessage(String loginMessage) {
		LOG.info("other login problem " + loginMessage);
		MiddlewareException ex = new MiddlewareException("", "30", loginMessage);
		ApplicationHelper.getUserBean().addException(ex);

		// FacesContext facesContext = FacesContext.getCurrentInstance();
		if (mdcmsUser.isExternalUser()) {
			loginMessage = EXTERNALLOGINNOTSUCCESSFUL;
			ex = new MiddlewareException("", "30", loginMessage);
			ApplicationHelper.getUserBean().addException(ex);

			// FacesMessage message = new FacesMessage(
			// FacesMessage.SEVERITY_ERROR, loginMessage, loginMessage);
			// facesContext.addMessage("", message);

			ex = new MiddlewareException("", "30", SWITCHEDTOISERIES);
			ApplicationHelper.getUserBean().addException(ex);
		} else if (mdcmsUser.isMdsecUser()) {
			ex = new MiddlewareException("", "30", loginMessage);
			ApplicationHelper.getUserBean().addException(ex);

			// FacesMessage message = new
			// FacesMessage(FacesMessage.SEVERITY_ERROR, loginMessage,
			// loginMessage);
			// facesContext.addMessage("", message);
		} else {
			// FacesMessage message = new
			// FacesMessage(FacesMessage.SEVERITY_ERROR, loginMessage,
			// loginMessage);
			if (loginMessage.startsWith("User")) {
				// facesContext.addMessage("md:userId", message);
			} else {
				// facesContext.addMessage("md:userPassword", message);
				// facesContext.addMessage("md:userId", message);
			}
		}
	}

	/**
	 * @return
	 */
	private String passwordChange() {
		LOG.info("pw expired");
		ApplicationHelper.getUserBean().addException(
				new MiddlewareException("", "30", ResourceHandler.getMessageResourceString("passwordExpired")));
		return "passwordChange";
	}

	/**
	 * 
	 */
	private boolean internalUserLogin() {
		// FacesContext facesContext = FacesContext.getCurrentInstance();
		if ("".equals(getUserId()) || getUserId() == null) {
			String errorMessage = ResourceHandler.getMessageResourceString("validationError") + " "
					+ ResourceHandler.getMessageResourceString("missingUserId");
			// FacesMessage message = new
			// FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
			// errorMessage);
			// facesContext.addMessage("md:userId", message);
			return true;
		}
		if ("".equals(getPassword()) || getPassword() == null) {
			String errorMessage = ResourceHandler.getMessageResourceString("validationError") + " "
					+ ResourceHandler.getMessageResourceString("missingPassword");
			// FacesMessage message = new
			// FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
			// errorMessage);
			// facesContext.addMessage("md:userPassword", message);
			return true;
		}
		return false;
	}

	public String reset() {
		setUserId("");
		setPassword("");
		return null;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the screen
	 */
	public String getScreen() {
		return screen;
	}

	/**
	 * @param screen
	 *            the screen to set
	 */
	public void setScreen(String screen) {
		this.screen = screen;
	}

	/**
	 * @return the projectid
	 */
	public String getProjectid() {
		return projectid;
	}

	/**
	 * @param projectid
	 *            the projectid to set
	 */
	public void setProjectid(String projectid) {
		if (projectid != null) {
			projectid = projectid.replaceAll("b", " ");
		}
		this.projectid = projectid;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}

	/**
	 * @param application
	 *            the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * @return the rfp
	 */
	public String getRfp() {
		return rfp;
	}

	/**
	 * @param rfp
	 *            the rfp to set
	 */
	public void setRfp(String rfp) {
		this.rfp = rfp;
	}

	/**
	 * @return the taskid
	 */
	public String getTaskid() {
		return taskid;
	}

	/**
	 * @param taskid
	 *            the taskid to set
	 */
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	/**
	 * @return the subtaskid
	 */
	public String getSubtaskid() {
		return subtaskid;
	}

	/**
	 * @param subtaskid
	 *            the subtaskid to set
	 */
	public void setSubtaskid(String subtaskid) {
		this.subtaskid = subtaskid;
	}

	public String getPassword() {
		if (mdcmsUser != null && mdcmsUser.getPassword() != null) {
			return mdcmsUser.getPassword();
		}
		return "";
	}

	public void setPassword(String password) {
		mdcmsUser.setPassword(password);
	}

	public String getUserId() {
		if (mdcmsUser != null && mdcmsUser.getUserId() != null) {
			return mdcmsUser.getUserId();
		}
		return "";
	}

	public void setUserId(String userId) {
		mdcmsUser.setUserId(userId);
	}

	/**
	 * @return the fromLoginBtn
	 */
	public boolean isFromLoginBtn() {
		return fromLoginBtn;
	}

	/**
	 * @param fromLoginBtn
	 *            the fromLoginBtn to set
	 */
	public void setFromLoginBtn(boolean fromLoginBtn) {
		this.fromLoginBtn = fromLoginBtn;
	}

}
