package com.md.mdcms.base;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.IBackingBean;
import com.md.mdcms.exception.MiddlewareException;
import com.md.mdcms.model.Container;
import com.md.mdcms.model.Operation;
import com.md.mdcms.xml.Xml;

public class FunctionBase implements IFunction, Xml, IConstants {

	/** Log instance for this class. */
	private static final Log LOG = LogFactory.getLog(FunctionBase.class);

	private static final String P_BACKINGBEAN = "backingbean";
	private static final String P_BEAN = "bean";
	private static final String P_MANAGER = "Manager";
	private static final String POINT = ".";

	protected IBackingBean reqBean;
	protected String screenName;

	protected Operation requestOperation;
	protected Operation responseOperation;

	// protected State responseStateSave;

	protected Vector<MiddlewareException> middlewareExceptions;

	// protected Object createBackingBeanManager(String screen)
	// throws InstantiationException, IllegalAccessException {
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
	// }
	// return null;
	// }

	// protected Object createBackingBean(String screen)
	// throws InstantiationException, IllegalAccessException {
	// StringBuffer sb = new StringBuffer();
	// try {
	// sb.append(screen);
	// sb.append(BACKINGBEAN);
	//
	// String className = JsfSupporter.findManagedBean(sb.toString())
	// .getClass().getPackage().getName();
	// className = className.replace(P_BACKINGBEAN, P_BEAN);
	//
	// sb = new StringBuffer();
	// sb.append(className);
	// sb.append(POINT);
	// sb.append(screen);
	// sb.append(BACKINGBEAN);
	// return Class.forName(sb.toString()).newInstance();
	//
	// } catch (ClassNotFoundException e) {
	// LOG.error("createBackingBean -> ClassNotFoundException " + screen,
	// e);
	// }
	// return null;
	// }

	/**
	 * @param obj
	 * @param variableName
	 * @param value
	 */
	protected void setValueInBean(Object obj, String variableName, Object value) {
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

	protected String getValueFromBean(Object obj, String variableName) {
		try {
			return BeanUtils.getProperty(obj, variableName);
		} catch (IllegalAccessException e) {
			// LOG.debug("IllegalAccessException / Object: " +
			// obj.getClass().getName() + " / Field: " + variableName, e);
		} catch (InvocationTargetException e) {
			// LOG.debug("InvocationTargetException / Object: " +
			// obj.getClass().getName() + " / Field: " + variableName, e);
		} catch (NoSuchMethodException e) {
			// LOG.debug("NoSuchMethodException / Object: " +
			// obj.getClass().getName() + " / Field: " + variableName, e);
		}
		return null;
	}

	public Container getContainer(String containerType) {
		List<Container> containers = responseOperation.getContainers();
		if (!containers.isEmpty() && containers != null) {
			for (Iterator<Container> iterator = containers.iterator(); iterator.hasNext();) {
				Container cont = (Container) iterator.next();
				if (cont.getType().equals(containerType))
					return cont;
			}
		}
		return null;
	}

	protected Vector<Container> getContainers(String containerType) {
		Vector<Container> conts = new Vector<Container>();
		List<Container> containers = responseOperation.getContainers();
		for (Iterator<Container> iterator = containers.iterator(); iterator.hasNext();) {
			Container cont = (Container) iterator.next();
			if (cont.getType().equals(containerType))
				conts.add(cont);
		}
		if (conts.size() > 0)
			return conts;

		return null;
	}

	protected void addMiddlewareException(String messageString) {
		// MiddlewareException exc = new MiddlewareException("",
		// FacesMessage.SEVERITY_ERROR.toString(), messageString);
		// ApplicationHelper.getUserBean().addException(exc);

		// getMiddlewareExceptions().add(exc);
	}

	/**
	 * @return the middlewareExceptions
	 */
	public Vector<MiddlewareException> getMiddlewareExceptions() {
		if (middlewareExceptions == null) {
			middlewareExceptions = new Vector<MiddlewareException>();
		}
		return middlewareExceptions;
	}

	/**
	 * @param middlewareExceptions
	 *            the middlewareExceptions to set
	 */
	public void setMiddlewareExceptions(Vector<MiddlewareException> middlewareExceptions) {
		this.middlewareExceptions = middlewareExceptions;
	}

	/**
	 * @return the responseOperation
	 */
	public Operation getResponseOperation() {
		return responseOperation;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

}
