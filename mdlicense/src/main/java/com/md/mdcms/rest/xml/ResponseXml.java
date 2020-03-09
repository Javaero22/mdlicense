package com.md.mdcms.rest.xml;

import java.io.ByteArrayInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.md.mdcms.base.ApplicationHelper;
import com.md.mdcms.exception.MiddlewareException;
import com.md.mdcms.rest.model.Operation;
import com.md.mdcms.xml.Xml;

public class ResponseXml implements Xml {

	protected static final Log LOG = LogFactory.getLog(ResponseXml.class);

	public static Operation generateOperation(String xmlResponseString) {
		Serializer serializer = new Persister();
		try {
			return serializer.read(Operation.class, xmlResponseString);

			// the String comes in UTF-8 therefore we read it in UTF-8
			// return serializer.read(Operation.class, new
			// ByteArrayInputStream(xmlResponseString.getBytes()));
			// return serializer.read(Operation.class, new ByteArrayInputStream(
			// xmlResponseString.getBytes()), "UTF-8");
		} catch (Exception e) {
			LOG.fatal(e);
			MiddlewareException ex = new MiddlewareException("", "40",
					"Fatal Error in serializer.read " + e.getLocalizedMessage()
							+ "/" + e.getCause());
			ApplicationHelper.getUserBean().addException(ex);
		}
		return null;
	}

	public static Operation generateOperation(
			ByteArrayInputStream xmlResponseStream) {
		Serializer serializer = new Persister();
		try {
			// the String comes in UTF-8 therefore we read it in UTF-8
			return serializer.read(Operation.class, xmlResponseStream);
		} catch (Exception e) {
			LOG.fatal(e);
		}
		return null;
	}

}
