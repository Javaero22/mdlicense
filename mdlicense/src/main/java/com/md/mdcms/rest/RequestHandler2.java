package com.md.mdcms.rest;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.base.IConstants;
import com.md.mdcms.model.OutcomeObject;
import com.md.mdcms.rest.model.ApplUserBean;
import com.md.mdcms.rest.model.IBackingBean;
import com.md.mdcms.rest.model.Operation;
import com.md.mdcms.rest.model.RW7000BackingBeanManager;
import com.md.mdcms.rest.model.State;
import com.md.mdcms.xml.Xml;

public class RequestHandler2 implements IConstants, Xml {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(RequestHandler2.class);

	private ApplUserBean userBean;
	private Operation responseOperation;
	private String jobNumber;
	private State responseState;

	HttpServletRequest request;

	private IBackingBean backingBean;

	public RequestHandler2() {
		super();
	}

	public IBackingBean handleRequest(HttpServletRequest request, IBackingBean backingBean) {
		IBackingBean returnBackingBean = null;

		try {
			this.request = request;
			this.backingBean = backingBean;
			this.jobNumber = backingBean.getJobNumber();

			HttpSession session = request.getSession(false);
			userBean = (ApplUserBean) session.getAttribute(ApplUserBean.BEAN_NAME);

			if (loginCheckSuccessfull()) {
				returnBackingBean = handleFunction();
			} else {
				// gotoLogin();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnBackingBean;
	}

	public IBackingBean handleFunction() {
		long startTime = System.currentTimeMillis();
		// String currentOutcome = getResponseState().getScreenThread();

		// neu
		userBean.setRequestBackingBean(backingBean);
		OutcomeObject outcomeObject = userBean.processFunction();

		if (outcomeObject != null) {
			String outcome = outcomeObject.getOutcome();
			String xlspth = outcomeObject.getXlspth();

			if (xlspth != null && !"".equals(xlspth)) {
				// openReport(xlspth);
				return null;
			}

			// initialize
			backingBean.setNextRequestCode(null);

			if (this.getClass().getSimpleName().startsWith(outcome)) {
				this.responseOperation = userBean.getResponseOperation(this.getClass().getSimpleName(), this.jobNumber,
						true);

				this.responseState = this.responseOperation.getState();

				RequestHandler requestHandler = new RequestHandler();
				IBackingBean returnBackingBean = (RW7000BackingBeanManager) requestHandler.handleRequest(request,
						jobNumber, backingBean.getClass());

				// boolean ok = create();
				this.responseOperation.setTotalTime(System.currentTimeMillis() - startTime);
				return returnBackingBean;
			}

			return null;
		} else {
			LOG.error("outcomeObject was null, something went really wrong");
		}

		return null;
	}

	protected boolean loginCheckSuccessfull() {
		if (userBean.isLoggedOn()) {
			if (this.responseOperation != null) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Start create: " + this.getClass().getName() + " create Start");
				}

				com.md.mdcms.rest.model.State state = new com.md.mdcms.rest.model.State();
				try {
					BeanUtils.copyProperties(state, this.responseState);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				backingBean.setResponseState(state);

				return true;
			} else {
				userBean.addToLoginStatus("noResponseOperation");
				LOG.error("BackingBean - no responseOperation for : " + this.getClass().getName());
				// errorOutcome = "contactAdmin.jsf";

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
			LOG.error("BackingBean needs login: " + this.getClass().getName() + "/ loggedOn? " + userBean.isLoggedOn());
			// errorOutcome = "userLoginInit.jsf";
		}
		return false;
	}

}
