package com.md.mdcms.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.beanutils.BeanUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.md.mdcms.model.Container;
import com.md.mdcms.model.Field;
import com.md.mdcms.model.Operation;
import com.md.mdcms.model.State;

public class DropDownListRequestXml {

	private ByteArrayOutputStream baos = null;
	private State ddRequestState;

	public DropDownListRequestXml() throws IOException, XMLStreamException {
		super();
		baos = new ByteArrayOutputStream();
	}

	public String generateRequestXML(State ddRequestState)
			throws XMLStreamException, IOException {
		this.ddRequestState = ddRequestState;
		writeRequestXML();
		baos.flush();
		baos.close();
		return baos.toString();
	}

	private void writeRequestXML() throws XMLStreamException {
		List<Field> fieldList = new ArrayList<Field>();
		try {

			Map<String, String> stateMap = BeanUtils.describe(ddRequestState);
			for (Iterator<String> iter = stateMap.keySet().iterator(); iter
					.hasNext();) {
				String element = (String) iter.next();
				if (stateMap.get(element) != null)
					fieldList.add(new Field(element, stateMap.get(element)));
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Container> containers = new ArrayList<Container>();
		containers.add(new Container(fieldList, "global"));
		Operation operation = new Operation(containers);

		Serializer ser = new Persister();
		try {
			ser.write(operation, baos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
