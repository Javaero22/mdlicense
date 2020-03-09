package com.md.mdcms.rest;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.ApplUserBean;
import com.md.mdcms.backingbean.ApplicationConfigurationBean;
import com.md.mdcms.base.ApplicationHelper;
import com.md.mdcms.base.IConstants;
import com.md.mdcms.base.ILoginHelper;
import com.md.mdcms.exception.MiddlewareException;
import com.md.mdcms.model.Container;
import com.md.mdcms.model.Grid;
import com.md.mdcms.model.Operation;
import com.md.mdcms.model.State;
import com.md.mdcms.model.User;
import com.md.mdcms.rest.model.IBackingBean;
import com.md.mdcms.util.Resource;
import com.md.mdcms.util.ResourceHandler;
import com.md.mdcms.xml.Xml;

public class LoginHandler extends BaseHandler {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(LoginHandler.class);

	private User mdcmsUser;

	public LoginHandler() {
		super();
	}

	public IBackingBean handleLogin(SecurityContext securityContext, HttpServletRequest request) {
		try {
			this.request = request;

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
				mdcmsUser.setUserType(ResourceHandler.getConfigurationResourceString(Resource.USER_TYPE));

				// String sessionUserTypeValue = (String)
				// session.getAttribute("userType");
				String sessionUserTypeValue = null;
				if (sessionUserTypeValue != null) {
					mdcmsUser.setUserType(sessionUserTypeValue);
				}

				/*
				 * no system user and/or password available -> switch to iseries userType
				 */
				if (!mdcmsUser.getUserType().equals(Resource.ISERIES)) {

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

//								outcome = "userLoginInit";
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
			 * try { HttpServletResponse httpResponse = (HttpServletResponse) extContext
			 * .getResponse();
			 * 
			 * httpResponse .sendRedirect("error/multipleBrowser.jsf" + sb.toString()); }
			 * catch (IOException e) { if (LOG.isInfoEnabled()) {
			 * LOG.fatal("sendRedirect error", e); } } }
			 */

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return backingBeanManager;
	}

}
