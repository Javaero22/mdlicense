package com.md.mdcms.cms.backingbean;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.ApplUserBean;
import com.md.mdcms.backingbean.BaseBackingBean;
import com.md.mdcms.base.ApplicationHelper;
import com.md.mdcms.model.OutcomeObject;

public abstract class MdBackingBean extends BaseBackingBean {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(MdBackingBean.class);

	private static final long serialVersionUID = 1L;

	protected String xlspth = null;

	protected String errorOutcome = null;

	public MdBackingBean() {
		super();
	}

	public MdBackingBean(boolean create) {
		super(create);
	}

	public MdBackingBean(HttpServletRequest request, String jobNumber) {
		super(request, jobNumber);
	}

	protected boolean loginCheckSuccessfull() {
		if (userBean.isLoggedOn()) {
			if (this.responseOperation != null) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Start create: " + this.getClass().getName() + " create Start");
				}
				return true;
			} else {
				userBean.addToLoginStatus("noResponseOperation");
				LOG.error("BackingBean - no responseOperation for : " + this.getClass().getName());
				errorOutcome = "contactAdmin.jsf";

				// String jobNumber = (String) ((HttpServletRequest)
				// FacesContext.getCurrentInstance().getExternalContext()
				// .getRequest()).getAttribute("jobNumber");

				// if (jobNumber == null) {
				// jobNumber = (String) ((HttpServletRequest)
				// FacesContext.getCurrentInstance().getExternalContext()
				// .getRequest()).getParameter("jobNumber");
				// }
				//
				// if (jobNumber != null) {
				// ApplicationHelper.getUserBean().doLogoff(jobNumber);
				// }
			}
		} else {
			LOG.error("BackingBean needs login: " + this.getClass().getName() + "/ loggedOn? "
					+ userBean.isLoggedOn());
			errorOutcome = "userLoginInit.jsf";
		}
		return false;
	}

	protected void gotoLogin() {
		// FacesContext facesContext = FacesContext.getCurrentInstance();
		// ExternalContext extContext = facesContext.getExternalContext();
		// HttpServletResponse httpResponse = (HttpServletResponse)
		// extContext.getResponse();
		//
		// try {
		// httpResponse.sendRedirect(errorOutcome);
		// } catch (IOException e) {
		// LOG.fatal("sendRedirect error", e);
		// }
	}

	protected void redirect(String outcome) {
		// FacesContext facesContext = FacesContext.getCurrentInstance();
		// ExternalContext extContext = facesContext.getExternalContext();
		// HttpServletResponse httpResponse = (HttpServletResponse)
		// extContext.getResponse();
		//
		// try {
		// httpResponse.sendRedirect(outcome + ".jsf");
		// } catch (IOException e) {
		// LOG.fatal("sendRedirect error", e);
		// }
	}

	public String handleFunction() {
		startTime = System.currentTimeMillis();
		// String currentOutcome = getResponseState().getScreenThread();

		// String scrolling =
		// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
		// .get("md:scrollToXY");

		String scrolling = null;

		if (scrolling != null) {
			// System.out.println("Scrolling from request: " + scrolling);
			String requestCode = getRequestState().getRequestCode();
			if (rcToSaveScrollPositions.contains(requestCode)) {
				setScrollToXY(scrolling);
				saveScrollPosition();
			}
		}

		// neu
		ApplUserBean userBean = ApplicationHelper.getUserBean();
		userBean.setRequestBackingBean(this);
		OutcomeObject outcomeObject = userBean.processMdFunction();

		if (outcomeObject != null) {
			String outcome = outcomeObject.getOutcome();
			String xlspth = outcomeObject.getXlspth();

			if (xlspth != null && !"".equals(xlspth)) {
				openReport(xlspth);
				return null;
			}

			// initialize
			setBtnName(null);

			if (this.getClass().getSimpleName().startsWith(outcome)) {
				this.responseOperation = userBean.getResponseOperation(this.getClass().getSimpleName(),
						this.getJobNumber(), true);
				this.responseState = this.responseOperation.getState();
				boolean ok = create();
				this.responseOperation.setTotalTime(System.currentTimeMillis() - startTime);
				if (LOG.isInfoEnabled()) {
					LOG.info("null, same bean: " + this.getClass().getSimpleName() + ".");
				}
				return null;
			}

			if (LOG.isInfoEnabled()) {
				LOG.info("Call new outcome: " + outcome + " - jobNumber: " + getJobNumber());
			}

			return outcome;
		} else {
			LOG.error("outcomeObject was null, something went really wrong");
		}

		return null;
	}

	public String getFooterInfo() {
		StringBuffer buf = new StringBuffer();
		try {
			buf.append("<br/>");
			buf.append("<br/>");
			buf.append("Times middleware / overall:");
			buf.append(this.responseOperation.getMiddlewareRequestTime());
			buf.append(":");
			buf.append(this.responseOperation.getMiddlewareResponseTime());
			buf.append("/");
			buf.append(this.responseOperation.getTotalTime());
			buf.append("<br/>SessionId: ");
			buf.append(ApplicationHelper.getUserBean().getSessionId());
			buf.append("<br/>JobNumber: ");
			buf.append(ApplicationHelper.getUserBean().getISeriesSession(getJobNumber()).getJobNumber());
			return buf.toString();
		} catch (Exception e) {
			LOG.error(buf.toString(), e);
			return buf.toString();
		}
	}

	/**
	 * @return the xlspth
	 */
	public String getXlspth() {
		return xlspth;
	}

	/**
	 * @param xlspth
	 *            the xlspth to set
	 */
	public void setXlspth(String xlspth) {
		this.xlspth = xlspth;
	}

}
