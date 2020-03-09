package com.md.mdcms.util;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.md.mdcms.backingbean.ApplUserBean;
import com.md.mdcms.bean.UserBean;
import com.md.mdcms.model.Container;
import com.md.mdcms.model.Item;
import com.md.mdcms.model.Operation;
import com.md.mdcms.model.SelectItemData;
import com.md.mdcms.model.State;
import com.md.mdcms.xml.Xml;

public class DropDownList implements Xml {

	private static Serializer serializer;

	// public static List<SelectItem> getDropDownList(String jobNumber,
	// State ddRequestState, boolean filtered) throws Exception {
	//
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//
	// Container globalContainer = new Container(CONTAINERTYPEGLOBAL);
	// globalContainer = ddRequestState.getAsGlobalContainer();
	//
	// Operation operation = new Operation();
	// operation.add(globalContainer);
	//
	// serializer = new Persister();
	// serializer.write(operation, baos);
	// baos.flush();
	// baos.close();
	//
	// String xmlRequestString = baos.toString();
	//
	// String xmlResponseString = ((ApplUserBean) UserBean.getUserBean())
	// .runMiddleware(jobNumber, ddRequestState, xmlRequestString,
	// true).getXmlResponseString();
	//
	// operation = serializer.read(Operation.class, xmlResponseString);
	//
	// return makeDrowDownList(operation, filtered);
	// }

	// private static List<SelectItem> makeDrowDownList(Operation operation,
	// boolean filtered) {
	// List<Container> containerList = operation.getContainers();
	// List<SelectItem> ddl = new ArrayList<SelectItem>();
	//
	// for (Iterator<Container> iter = containerList.iterator(); iter
	// .hasNext();) {
	// Container cont = (Container) iter.next();
	// if (CONTAINERMESSAGELIST.equals(cont.getType())) {
	// ddl.add(new SelectItem(null, cont.getArrays().get(0)
	// .getArrayItems().get(0).getValue()));
	// }
	// if (CONTAINERTYPEDROPDOWNLIST.equals(cont.getType())) {
	// String view = cont.getArrays().get(0).getView();
	// boolean customValue = Boolean.valueOf(cont.getArrays().get(0)
	// .getCustomValue());
	//
	// if (cont.getArrays().get(0).getArrayItems() != null) {
	// List<Item> items = cont.getArrays().get(0).getArrayItems();
	// if (filtered) {
	// boolean edit;
	// for (Iterator<Item> itemIt = items.iterator(); itemIt
	// .hasNext();) {
	// edit = true;
	// Item item = (Item) itemIt.next();
	// if (item.getEdit() != null)
	// edit = Boolean.valueOf(item.getEdit());
	// if (edit)
	// if ("code".equals(view)) {
	// ddl.add(new SelectItem(item.getCode(), item
	// .getCode()));
	// } else if ("value".equals(view)) {
	// ddl.add(new SelectItem(item.getCode(), item
	// .getValue()));
	// } else {
	// ddl.add(new SelectItem(item.getCode(), item
	// .getCode()
	// + " - "
	// + item.getValue()));
	// }
	// }
	// } else {
	// for (Iterator<Item> itemIt = items.iterator(); itemIt
	// .hasNext();) {
	// Item item = (Item) itemIt.next();
	// if ("code".equals(view)) {
	// ddl.add(new SelectItem(item.getCode(), item
	// .getCode()));
	// } else if ("value".equals(view)) {
	// ddl.add(new SelectItem(item.getCode(), item
	// .getValue()));
	// } else {
	// ddl.add(new SelectItem(item.getCode(), item
	// .getCode() + " - " + item.getValue()));
	// }
	// }
	// }
	// }
	// }
	// }
	// return ddl;
	// }

	public static SelectItemData getDropDownListsFromFile(String xmlResponseString) throws Exception {

		serializer = new Persister();

		Operation operation = serializer.read(Operation.class, xmlResponseString);

		return makeDrowDownLists(operation);
	}

	public static SelectItemData getDropDownLists(State ddRequestState) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Container globalContainer = new Container(CONTAINERTYPEGLOBAL);
		globalContainer = ddRequestState.getAsGlobalContainer();

		Operation operation = new Operation();
		operation.add(globalContainer);

		serializer = new Persister();
		serializer.write(operation, baos);
		baos.flush();
		baos.close();

		String xmlRequestString = baos.toString();

		String xmlResponseString = ((ApplUserBean) UserBean.getUserBean())
				.runMiddlewareDdl(ddRequestState, xmlRequestString, true).getXmlResponseString();

		operation = serializer.read(Operation.class, xmlResponseString);

		return makeDrowDownLists(operation);
	}

	private static SelectItemData makeDrowDownLists(Operation operation) {
		SelectItemData sid = new SelectItemData();
		String view = "code-value";
		boolean customValue = false;
		List<Container> containerList = operation.getContainers();
		// Vector<List<SelectItem>> ddls = new Vector<List<SelectItem>>();
		// for (int i = 0; i < 2; i++) {
		// ddls.add(new ArrayList<SelectItem>());
		// }

		for (Iterator<Container> iter = containerList.iterator(); iter.hasNext();) {
			Container cont = (Container) iter.next();
			if (CONTAINERMESSAGELIST.equals(cont.getType())) {
				// ddls.get(0).add(
				// new SelectItem(null, cont.getArrays().get(0)
				// .getArrayItems().get(0).getValue()));
				// ddls.get(1).add(
				// new SelectItem(null, cont.getArrays().get(0)
				// .getArrayItems().get(1).getValue()));
			}
			if (CONTAINERTYPEDROPDOWNLIST.equals(cont.getType())) {
				view = cont.getArrays().get(0).getView();
				customValue = Boolean.valueOf(cont.getArrays().get(0).getCustomValue());
				if (cont.getArrays().get(0).getArrayItems() != null) {
					List<Item> items = cont.getArrays().get(0).getArrayItems();
					boolean edit;
					for (Iterator<Item> itemIt = items.iterator(); itemIt.hasNext();) {
						edit = true;
						Item item = (Item) itemIt.next();
						if (item.getEdit() != null) {
							edit = Boolean.valueOf(item.getEdit());
						}
						// if (edit) {
						// ddls.get(0).add(
						// new SelectItem(item.getCode(), item
						// .getValue()));
						// }
						//
						// ddls.get(1)
						// .add(new SelectItem(item.getCode(), item
						// .getValue()));
					}
				}
			}
		}
		// sid = new SelectItemData(view, customValue, ddls);
		return sid;
	}

}
