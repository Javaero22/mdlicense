package com.md.mdcms.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.IBackingBean;
import com.md.mdcms.base.IConstants;
import com.md.mdcms.bean.Bean;
import com.md.mdcms.cms.backingbean.MdBackingBean;
import com.md.mdcms.model.CheckboxField;
import com.md.mdcms.model.StringField;

/*
 * Copyright 2009 Javaero-Technology GmbH. All Rights reserved.
 *
 * This software is the proprietary information of GmbH
 * Use is subject to license and non-disclosure terms.
 */

/**
 * Ren� Untern�hrer / javaero-technology GmbH 05.10.2010
 */

public class MdUtil implements IConstants {

	/** Log instance for this class. */
	private static final Log LOG = LogFactory.getLog(MdUtil.class);

	private static final String _BEAN_PACKAGE_NAME = "com.md.mdcms.cms.bean.";
	private static final String P_BACKINGBEAN = "backingbean";
	private static final String P_BEAN = "bean";
	private static final String POINT = ".";

	/**
	 * @param list
	 * @param string
	 * @param string2
	 * @return
	 */
	public static String extractText(MdBackingBean backingBean,
			String valueFieldName, String breakFieldName) {
		String line = "";
		boolean editable = false;
		boolean lineBreak = false;
		int breakcount = 0;
		StringBuffer sb = new StringBuffer();
		Bean bean;
		List<Bean> beans = (List<Bean>) backingBean.getList();
		if (beans != null && !beans.isEmpty()) {

			for (Iterator<Bean> iterator = beans.iterator(); iterator.hasNext();) {
				bean = (Bean) iterator.next();

				StringField lineField = (StringField) bean.get(valueFieldName);
				CheckboxField breakField = (CheckboxField) bean
						.get(breakFieldName);

				line = lineField.getHtml();
				editable = Boolean.valueOf(lineField.getEditable());
				lineBreak = breakField.isSelected();

				if (!"".equals(line)) {
					for (int i = 0; i < breakcount; i++) {
						if (editable) {
							sb.append(NEWLINE);
						} else {
							sb.append(LINEBREAK);
						}
					}
					breakcount = 0;

					// sb.append(UnicodeConverter.forHtml(line));
					sb.append(line);
					if (Boolean.valueOf(lineBreak)) {
						if (editable) {
							sb.append(NEWLINE);
						} else {
							sb.append(LINEBREAK);
						}
					}
				} else {
					// if (Boolean.valueOf(lineBreak)) {
					// if (editable) {
					// sb.append(NEWLINE);
					// } else {
					// sb.append(LINEBREAK);
					// }
					// }
					breakcount++;
				}
			}
		}

		return sb.toString();
	}

	public static List<Bean> injectText(String text, String className,
			String valueFieldName, String breakFieldName) {
		List<Bean> beans = new ArrayList<Bean>();

		String availableText = text;
		int restlength = availableText.length();
		String currentText = "";
		int newlinePosition = 0;
		boolean hasNewline = false;

		StringField lineField = new StringField();
		CheckboxField breakField = new CheckboxField();

		int rowCounter = 1;

		do {

			if (restlength > 78) {
				currentText = availableText.substring(0, 79);
			} else if (restlength == 0) {
				currentText = "";
			} else {
				currentText = availableText
						.substring(0, availableText.length());
			}

			if (currentText.contains(NEWLINE)) {
				hasNewline = true;
				newlinePosition = currentText.indexOf(NEWLINE);
				currentText = availableText.substring(0, newlinePosition);
				availableText = availableText.substring(newlinePosition + 1);
			} else {
				hasNewline = false;
				if (availableText.length() > 78) {
					availableText = availableText.substring(79);
				} else {
					availableText = "";
					hasNewline = true;
				}
			}

			if (currentText.endsWith(" ")) {
				availableText = " " + availableText;
			}

			if (currentText.length() == 79 && availableText.startsWith(RETURN)) {
				hasNewline = true;
				availableText = availableText.substring(2);
			}

			Bean bean;
			try {
				bean = (Bean) Class.forName(className).newInstance();
				bean.setRownr(rowCounter);

				lineField = new StringField();
				lineField.setId(valueFieldName);
				lineField.setEditable("true");
				// lineField.setHtml(UnicodeConverter.forXml(currentText));
				lineField.setHtml(UnicodeString.convert(currentText));
				BeanUtils.setProperty(bean, valueFieldName, lineField);

				breakField = new CheckboxField();
				breakField.setId(breakFieldName);
				breakField.setSelected(hasNewline);
				breakField.setVisible("false");
				breakField.setEditable("false");
				BeanUtils.setProperty(bean, breakFieldName, breakField);

				beans.add(bean);

			} catch (InstantiationException e) {
				LOG.fatal(e);
			} catch (IllegalAccessException e) {
				LOG.fatal(e);
			} catch (ClassNotFoundException e) {
				LOG.fatal(e);
			} catch (InvocationTargetException e) {
				LOG.fatal(e);
			}

			// if (availableText.startsWith(RETURN)) {
			// availableText = availableText.substring(1);
			// }
			// if (availableText.startsWith(NEWLINE)) {
			// availableText = availableText.substring(1);
			// }

			restlength = availableText.length();

			rowCounter++;

		} while (rowCounter < 81);

		return beans;
	}

	public static List<Bean> injectText(String text, String className,
			String valueFieldName, String breakFieldName, int lengthOfLine,
			int maxrows) {

		List<Bean> beans = new ArrayList<Bean>();

		String availableText = text;
		int restlength = availableText.length();
		String currentText = "";
		int newlinePosition = 0;
		boolean hasNewline = false;

		StringField lineField = new StringField();
		CheckboxField breakField = new CheckboxField();

		int rowCounter = 1;

		do {

			// if (restlength > 78) {
			if (restlength > (lengthOfLine - 1)) {
				// currentText = availableText.substring(0, 79);
				currentText = availableText.substring(0, lengthOfLine);
			} else if (restlength == 0) {
				currentText = "";
			} else {
				currentText = availableText
						.substring(0, availableText.length());
			}

			if (currentText.contains(NEWLINE)) {
				hasNewline = true;
				newlinePosition = currentText.indexOf(NEWLINE);
				currentText = availableText.substring(0, newlinePosition);
				availableText = availableText.substring(newlinePosition + 1);
			} else {
				hasNewline = false;
				// if (availableText.length() > 78) {
				if (availableText.length() > (lengthOfLine - 1)) {
					// availableText = availableText.substring(79);
					availableText = availableText.substring(lengthOfLine);
				} else {
					availableText = "";
					hasNewline = true;
				}
			}

			if (currentText.endsWith(" ")) {
				availableText = " " + availableText;
			}

			// if (currentText.length() == 79 &&
			// availableText.startsWith(RETURN)) {
			if (currentText.length() == lengthOfLine
					&& availableText.startsWith(RETURN)) {
				hasNewline = true;
				availableText = availableText.substring(2);
			}

			Bean bean;
			try {
				bean = (Bean) Class.forName(className).newInstance();
				bean.setRownr(rowCounter);

				lineField = new StringField();
				lineField.setId(valueFieldName);
				lineField.setEditable("true");
				// lineField.setHtml(UnicodeConverter.forXml(currentText));
				lineField.setHtml(UnicodeString.convert(currentText));
				BeanUtils.setProperty(bean, valueFieldName, lineField);

				breakField = new CheckboxField();
				breakField.setId(breakFieldName);
				breakField.setSelected(hasNewline);
				breakField.setVisible("false");
				breakField.setEditable("false");
				BeanUtils.setProperty(bean, breakFieldName, breakField);

				beans.add(bean);

			} catch (InstantiationException e) {
				LOG.fatal(e);
			} catch (IllegalAccessException e) {
				LOG.fatal(e);
			} catch (ClassNotFoundException e) {
				LOG.fatal(e);
			} catch (InvocationTargetException e) {
				LOG.fatal(e);
			}

			// if (availableText.startsWith(RETURN)) {
			// availableText = availableText.substring(1);
			// }
			// if (availableText.startsWith(NEWLINE)) {
			// availableText = availableText.substring(1);
			// }

			restlength = availableText.length();

			rowCounter++;

			// } while (rowCounter < 81);
		} while (rowCounter < (maxrows + 1));

		return beans;
	}

	/**
	 * @param list
	 * @return
	 */
	public static boolean isTextInputRendered(List<Bean> beans,
			String valueFieldName) {
		boolean editable = false;
		if (beans != null && !beans.isEmpty()) {
			editable = Boolean.valueOf(((StringField) beans.get(0).get(
					valueFieldName)).getEditable());
		}
		return editable;
	}

	/**
	 * @param obj
	 * @param variableName
	 * @param value
	 */
	public static void setValueInBean(Object obj, String variableName,
			Object value) {
		try {
			BeanUtils.setProperty(obj, variableName, value);
		} catch (IllegalAccessException e) {
			// LOG.debug("IllegalAccessException / Object: " +
			// obj.getClass().getName() + " / Field: " + variableName, e);
		} catch (InvocationTargetException e) {
			// LOG.debug("InvocationTargetException / Object: " +
			// obj.getClass().getName() + " / Field: " + variableName, e);
		} catch (NullPointerException e) {
			// LOG.debug("NullPointerException / Object: " +
			// obj.getClass().getName() + " / Field: " + variableName, e);
		}
	}

	// public static Object createBackingBeanManager(String screen) {
	// StringBuffer sb = new StringBuffer();
	// try {
	// sb.append(screen);
	//
	// sb.append(BACKINGBEAN);
	//
	// String className = JsfSupporter.findManagedBean(sb.toString())
	// .getClass().getName();
	// return Class.forName(className).newInstance();
	//
	// } catch (ClassNotFoundException e) {
	// LOG.info("createBackingBeanManager -> ClassNotFoundException ", e);
	// } catch (InstantiationException e) {
	// LOG.info("createBackingBeanManager -> InstantiationException ", e);
	// } catch (IllegalAccessException e) {
	// LOG.info("createBackingBeanManager -> IllegalAccessException ", e);
	// }
	// return null;
	// }

	public static Object createBackingBeanManagerNew(
			IBackingBean backingBeanManager) {
		try {
			return Class.forName(backingBeanManager.getClass().getName())
					.newInstance();
		} catch (ClassNotFoundException e) {
			LOG.info("createBackingBeanManager -> ClassNotFoundException ", e);
		} catch (InstantiationException e) {
			LOG.info("createBackingBeanManager -> InstantiationException ", e);
		} catch (IllegalAccessException e) {
			LOG.info("createBackingBeanManager -> IllegalAccessException ", e);
		}
		return null;
	}

	// public static Object createBackingBean(String screen) {
	// StringBuffer sb = new StringBuffer();
	// try {
	// sb.append(P_PACKAGE);
	// sb.append(screen);
	// sb.append(BACKINGBEAN);
	//
	// return Class.forName(sb.toString()).newInstance();
	//
	// } catch (ClassNotFoundException e) {
	// LOG.error("createBackingBean -> ClassNotFoundException " + screen,
	// e);
	// } catch (InstantiationException e) {
	// LOG.error("createBackingBean -> InstantiationException " + screen,
	// e);
	// } catch (IllegalAccessException e) {
	// LOG.error("createBackingBean -> IllegalAccessException " + screen,
	// e);
	// }
	// return null;
	// }

	public static Object createBackingBeanNew(IBackingBean backingBeanManager) {
		String className = backingBeanManager.getClass().getSimpleName();
		className = className.replace("Manager", "");

		StringBuffer sb = new StringBuffer();
		try {
			sb.append(_BEAN_PACKAGE_NAME);
			sb.append(className);
			// sb.append(BACKINGBEAN);

			return Class.forName(sb.toString()).newInstance();

		} catch (ClassNotFoundException e) {
			LOG.error("createBackingBean -> ClassNotFoundException "
					+ className, e);
		} catch (InstantiationException e) {
			LOG.error("createBackingBean -> InstantiationException "
					+ className, e);
		} catch (IllegalAccessException e) {
			LOG.error("createBackingBean -> IllegalAccessException "
					+ className, e);
		}
		return null;
	}

	/**
	 * @param string
	 * @param environment
	 * @return
	 */
	public static String getFindProperty(String variable, String property) {
		String sysvar = System.getProperty(variable);

		LOG.info("Find system property for: " + variable
				+ " / File property is: " + property
				+ " / System property is: " + sysvar);

		if (sysvar == null) {
			LOG.info("Property is taken from file: " + property);
			return property;
		} else {
			LOG.info("Property is taken from system: " + sysvar);
			return sysvar;
		}
	}
}
