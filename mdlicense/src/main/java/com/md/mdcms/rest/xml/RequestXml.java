package com.md.mdcms.rest.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.md.mdcms.exception.MiddlewareException;
import com.md.mdcms.rest.model.Operation;
import com.md.mdcms.xml.Xml;

public class RequestXml implements Xml {

	public static String generateXmlRequestString(Operation operation) throws XMLStreamException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Serializer serializer = new Persister();
		try {
			serializer.write(operation, baos);
			String xmlRequestString = baos.toString("UTF-8");
			return xmlRequestString;
		} catch (Exception e) {
			MiddlewareException ex = new MiddlewareException("", "40",
					"Fatal Error in serializer.write " + e.getLocalizedMessage() + "/" + e.getCause());
			// ApplicationHelper.getUserBean().addException(ex);
		}
		return null;
	}

}
