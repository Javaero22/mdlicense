package com.md.mdcms.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.md.mdcms.base.IConstants;

public class Model implements IConstants, Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = 8329809218589566880L;

	// protected Object createBackingBeanManager(String screen)
	// throws InstantiationException, IllegalAccessException {
	// try {
	// return Class.forName(
	// ApplicationConfigurationBean.getAppConf()
	// .getJavaBackingBeanManagerPackage()
	// + screen + BACKINGBEANMANAGER).newInstance();
	// } catch (ClassNotFoundException e) {
	// LOG.fatal("createBackingBeanManager -> ClassNotFoundException ", e);
	// }
	// return null;
	// }

	protected void setValueInBean(Object obj, String variableName, Object value) {
		try {
			BeanUtils.setProperty(obj, variableName, value);
		} catch (IllegalAccessException e) {
			// LOG.warn("IllegalAccessException / Object: " +
			// obj.getClass().getName() + " / Field: " + variableName, e);
		} catch (InvocationTargetException e) {
			// LOG.warn("InvocationTargetException / Object: " +
			// obj.getClass().getName() + " / Field: " + variableName, e);
		} catch (NullPointerException e) {
			// LOG.error("NullPointerException / Object: " +
			// obj.getClass().getName() + " / Field: " + variableName, e);
		}
	}

	// protected List<SelectItem> createSelectionList(String list) {
	// String[] listEntry = list.split(",");
	// List<SelectItem> selectionList = new ArrayList<SelectItem>();
	// for (int i = 0; i < listEntry.length; i++) {
	// String[] codeText = listEntry[i].split("=");
	// selectionList.add(new SelectItem(codeText[0], codeText[1]));
	// }
	// return selectionList;
	// }

}
