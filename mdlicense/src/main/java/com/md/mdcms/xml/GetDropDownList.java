package com.md.mdcms.xml;

import com.md.mdcms.base.IConstants;

public class GetDropDownList implements IConstants {

	public GetDropDownList() {
		super();
	}

	// public Vector<List<SelectItem>> getDropDownList(String jobNumber,
	// State ddRequestState) throws IOException, XMLStreamException,
	// IllegalArgumentException, IllegalAccessException,
	// InvocationTargetException {
	// DropDownListRequestXml ddlrx = new DropDownListRequestXml();
	// String xmlRequestString = ddlrx.generateRequestXML(ddRequestState);
	//
	// String xmlResponseString = null;
	//
	// xmlResponseString = ((ApplUserBean) UserBean.getUserBean())
	// .runMiddleware(jobNumber, ddRequestState, xmlRequestString,
	// true).getXmlResponseString();
	//
	// if (xmlResponseString.startsWith(EXCEPTION)) {
	// MiddlewareException exc = new MiddlewareException("",
	// FacesMessage.SEVERITY_ERROR.toString(), xmlResponseString);
	// ((ApplUserBean) UserBean.getUserBean()).addException(exc);
	// return null;
	// }
	//
	// DropDownListResponseXml ddlresxml = new DropDownListResponseXml(
	// xmlResponseString);
	//
	// return ddlresxml.getLists();
	// }
}
