package com.md.mdcms.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.ApplUserBean;
import com.md.mdcms.base.ApplicationHelper;
import com.md.mdcms.model.State;

public class MdErrorDisplay {

	/** Log instance for this class. */
	private static final Log LOG = LogFactory.getLog(MdErrorDisplay.class);

	private static final long serialVersionUID = 3123969847287207137L;
	private static final String BEAN_NAME = ErrorDisplay.class.getName();

	private String statusCode;
	private String detailMessage;
	private Throwable ex;
	private String exType;
	private String userid;
	private State responseState;
	private State requestState;
	private String randomNumber;

	public MdErrorDisplay() {
		super();
		setVariablesAndLogoff();
	}

	private void setVariablesAndLogoff() {
		ApplUserBean userBean = ApplicationHelper.getUserBean();
		userBean.removeExceptions();
		// FacesContext context = FacesContext.getCurrentInstance();
		// Map requestMap = context.getExternalContext().getRequestMap();
		try {
			// ex = (Throwable) requestMap.get("javax.servlet.error.exception");
			// statusCode = requestMap.get("javax.servlet.error.status_code")
			// .toString();
			// Object exTypeObject = requestMap
			// .get("javax.servlet.error.exception_type");
			// if (exTypeObject != null)
			// exType = exTypeObject.toString();
			detailMessage = ex.getLocalizedMessage();

			if (userBean.isLoggedOn()) {
				userid = userBean.getUserId();
			} else {
				userid = "user was not logged on -> no userid";
			}
			// responseState = userBean.getResponseState();
			// requestState = userBean.getRequestState();
		} catch (Exception e) {
		}
		int finalNr = (int) Math.round(Math.random() * 10000);
		randomNumber = String.valueOf(finalNr);

		LOG.fatal("=========================================================");
		LOG.fatal("START OFF EXCEPTION FOR USER " + userid);
		LOG.fatal("Exception Number : " + String.valueOf(randomNumber));
		LOG.fatal("Exception        : " + statusCode + " / type :" + exType);
		LOG.fatal("Exception           : ", ex);
		LOG.fatal("-----------------------");

		LOG.fatal("User : " + userid + " loggedOff du to an exception ");

		userBean.setLoggedOn(false);

		// if (userBean.getISeriesSession() != null) {
		// userBean.getISeriesSession().close();
		// }
	}

	private void log(Object obj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map props = null;
		props = BeanUtils.describe(obj);
		if (props != null) {
			for (Iterator<String> iter = props.keySet().iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				LOG.fatal("id " + element + " / value = " + props.get(element));
			}
		}
	}

	public String getStackTrace() {
		// FacesContext context = FacesContext.getCurrentInstance();
		// Map requestMap = context.getExternalContext().getRequestMap();

		// Throwable ex = (Throwable) requestMap
		// .get("javax.servlet.error.exception");

		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		fillStackTrace(ex, pw);

		return writer.toString();
	}

	private void fillStackTrace(Throwable ex, PrintWriter pw) {
		if (null == ex) {
			return;
		}

		ex.printStackTrace(pw);

		if (ex instanceof ServletException) {
			Throwable cause = ((ServletException) ex).getRootCause();

			if (null != cause) {
				pw.println("Root Cause:");
				fillStackTrace(cause, pw);
			}
		} else {
			Throwable cause = ex.getCause();

			if (null != cause) {
				pw.println("Cause:");
				fillStackTrace(cause, pw);
			}
		}
	}

	public String getDateTime() {
		Calendar cal = GregorianCalendar.getInstance();
		Date date = cal.getTime();
		return DateFormat.getDateTimeInstance().format(date);
	}

	public Throwable getEx() {
		return ex;
	}

	public String getExType() {
		return exType;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public String getUserid() {
		return userid;
	}

	public String getScreen() {
		return responseState.getScreen();
	}

	public String getRandomNumber() {
		return randomNumber;
	}

}
