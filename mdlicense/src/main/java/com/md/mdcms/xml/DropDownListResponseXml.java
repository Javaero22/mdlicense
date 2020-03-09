package com.md.mdcms.xml;

import java.util.Iterator;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.md.mdcms.model.Container;
import com.md.mdcms.model.Item;
import com.md.mdcms.model.Operation;

public class DropDownListResponseXml implements Xml {

	// private Vector<List<SelectItem>> ddls = null;
	private Operation operation;

	public DropDownListResponseXml(String responseXml) {
		super();
		// ddls = new Vector<List<SelectItem>>();
		// for (int i = 0; i < 2; i++) {
		// ddls.add(new ArrayList<SelectItem>());
		// }
		init(responseXml);
		makeDdlList();
	}

	private void init(String responseXml) {
		Serializer serializer = new Persister();
		try {
			operation = serializer.read(Operation.class, responseXml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void makeDdlList() {
		List<Container> containerList = operation.getContainers();
		for (Iterator iter = containerList.iterator(); iter.hasNext();) {
			Container cont = (Container) iter.next();
			if ("messageList".equals(cont.getType())) {
				handleMessage(cont.getArrays().get(0).getArrayItems().get(0));
			}
			if ("dropDownList".equals(cont.getType())) {
				// ddls.get(0).add(new SelectItem("", ""));
				// ddls.get(1).add(new SelectItem("", ""));
				if (cont.getArrays().get(0).getArrayItems() != null) {
					List<Item> items = cont.getArrays().get(0).getArrayItems();
					for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
						Item item = (Item) iterator.next();
						handleItem(item);
					}
				}
			}
		}
	}

	private void handleItem(Item item) {
		String code = item.getCode();
		String value = item.getValue();
		boolean edit = true;
		if (item.getEdit() != null)
			edit = Boolean.valueOf(item.getEdit());
		if (edit) {
			// ddls.get(0).add(new SelectItem(code, value));
		}
		// ddls.get(1).add(new SelectItem(code, value));
		// list.add(new SelectItem(code,EscapeChars.forHTML(value)));
	}

	private void handleMessage(Item item) {
		String value = item.getValue();
		// ddls.get(0).add(new SelectItem(null, value));
		// ddls.get(1).add(new SelectItem(null, value));
	}

	// public Vector<List<SelectItem>> getLists() {
	// return ddls;
	// }

}
