package com.md.mdcms.rest.base;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.exception.MiddlewareException;
import com.md.mdcms.model.XmlResponseObject;
import com.md.mdcms.rest.model.ApplUserBean;
import com.md.mdcms.rest.model.IBackingBean;
import com.md.mdcms.rest.xml.ResponseXml;

public class MdFunction extends Function {

	/** Log instance for this class. */
	private static final Log LOG = LogFactory.getLog(MdFunction.class);

	public MdFunction(ApplUserBean applUserBean, IBackingBean backingBean) throws XMLStreamException, IOException {
		super();
		this.userBean = applUserBean;
		this.reqBean = backingBean;
		getData(backingBean.getJobNumber());
	}

	/*
	 * this is the heart of the lifecycle from the RequestManagerBean to
	 * generate the ResponseManagerBean
	 */
	protected void getData(String jobNumber) throws XMLStreamException, IOException {

		String xmlRequestString = prepareRequestXml(this.reqBean);

		xmlRequestString = xmlRequestString.replaceAll("&amp;", "&");

		XmlResponseObject xmlResponseObject = userBean.runMiddleware(jobNumber, reqBean.getRequestState(),
				xmlRequestString, false);
		String xmlResponseString = xmlResponseObject.getXmlResponseString();

		if (xmlResponseString.startsWith(EXCEPTION)) {
			addMiddlewareException(xmlResponseString);
		} else {

			if (prepareResponseXml(xmlResponseString)) {
				this.responseOperation.setMiddlewareRequestTime(xmlResponseObject.getMiddlewareRequestTime());
				this.responseOperation.setMiddlewareResponseTime(xmlResponseObject.getMiddlewareResponseTime());
			} else {
				LOG.info("getData-1");
				return;
			}
		}

		// if (LOG.isWarnEnabled()) {
		// StringBuffer sb = new StringBuffer();
		//
		// sb.append("<transaction>");
		// sb.append(" <user>");
		// sb.append(ApplicationHelper.getUserBean().getUserId());
		// sb.append(" </user>");
		// sb.append(" <time>");
		// sb.append(Functions.dateTime());
		// sb.append(" </time>");
		// sb.append("</transaction>");
		// LOG.warn(sb.toString());
		// }
	}

	/*
	 * Everything R E S P O N S E R E S P O N S E Everything R E S P O N S E
	 */

	protected boolean prepareResponseXml(String xmlResponseString) {

		this.responseOperation = null;
		try {
			this.responseOperation = ResponseXml.generateOperation(xmlResponseString);
			if (this.responseOperation == null) {
				return false;
			}
		} catch (Exception e1) {
			LOG.fatal(e1);
			MiddlewareException exc = new MiddlewareException("", "30", e1.getMessage());
			userBean.addException(exc);

			getMiddlewareExceptions().add(exc);

			return false;
		}

		// ApplicationHelper.getUserBean().setResponseOperation(
		// this.responseOperation);

		screenName = this.responseOperation.getScreenName();
		LOG.debug("Next screen: " + screenName);

		if (!handleContainerMessageList()) {
			return false;
		}

		return true;
	}

}
