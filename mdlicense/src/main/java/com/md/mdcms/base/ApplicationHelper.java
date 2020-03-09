package com.md.mdcms.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.ApplUserBean;
import com.md.mdcms.backingbean.ApplicationConfigurationBean;
import com.md.mdcms.backingbean.IBackingBean;
import com.md.mdcms.backingbean.StartConfigurationBean;
import com.md.mdcms.bean.UserBean;
import com.md.mdcms.model.Container;
import com.md.mdcms.model.Operation;
import com.md.mdcms.model.State;
import com.md.mdcms.xml.Xml;

public class ApplicationHelper implements Xml, IConstants {

	/** Log instance for this class. */
	private static final Log LOG = LogFactory.getLog(ApplicationHelper.class);

	public static ApplUserBean getUserBean() {
		return (ApplUserBean) UserBean.getUserBean();
	}

	public static String getServletRequestURL() {
		// ExternalContext ec = FacesContext.getCurrentInstance()
		// .getExternalContext();
		// Object request = ec.getRequest();
		// HttpServletRequest servletRequest = ((HttpServletRequest) request);
		// String requestUrl = servletRequest.getRequestURL().toString();
		// String scheme = servletRequest.getScheme() + "://";

		/*
		 * Port
		 */

		// int indexOfPortStart = requestUrl.indexOf(":", scheme.length());
		// int indexOfPortEnd = requestUrl.indexOf("/", scheme.length());
		// String port = "";
		// if (indexOfPortStart > 0) {
		// port = requestUrl.substring(indexOfPortStart + 1, indexOfPortEnd);
		// }
		// int indexOfContextPathEnd = requestUrl.indexOf("/", indexOfPortEnd +
		// 1);
		//
		// String servletRequestUrl = requestUrl.substring(0,
		// indexOfContextPathEnd + 1);
		//
		String servletRequestUrl = null;
		// LOG.debug("requestUrl found: " + requestUrl);
		// LOG.debug("servletRequestURL found: " + servletRequestUrl);

		return servletRequestUrl;
	}

	public static boolean isTest() {
		return "test".equals(StartConfigurationBean.getInstance().getProfil()) ? true : false;
	}

	public static String getTextForListCode(String fieldName, String code) {

		// LOG.info("getTextForListCode: " + fieldName + " code: " + code);

		// if (code != null && !"".equals(code)) {
		// try {
		// IListBean listBean = (IListBean)
		// JsfSupporter.findSessionScopedBean(ListBean.BEAN_NAME);
		// List<SelectItem> plist = listBean.getSelectItemsList(fieldName,
		// false);
		//
		// for (int j = 0; j < plist.size(); j++) {
		// SelectItem si = plist.get(j);
		// if (si.getValue().equals(code)) {
		// return si.getLabel();
		// }
		// }
		//
		// StringBuffer sb = new StringBuffer();
		// sb.append("<ddl>");
		// sb.append("<text>");
		// sb.append("not available");
		// sb.append("<text>");
		// sb.append("<listname>");
		// sb.append(fieldName);
		// sb.append("</listname>");
		// sb.append("<code>");
		// sb.append(code);
		// sb.append("</code>");
		// sb.append("</ddl>");
		// LOG.fatal(sb.toString());
		//
		// return "n/a";
		// } catch (Exception e) {
		// StringBuffer sb = new StringBuffer();
		// sb.append("<ddl>");
		// sb.append("<text>");
		// sb.append("not available");
		// sb.append("<text>");
		// sb.append("<listname>");
		// sb.append(fieldName);
		// sb.append("</listname>");
		// sb.append("<code>");
		// sb.append(code);
		// sb.append("</code>");
		// sb.append("</ddl>");
		// LOG.fatal(sb.toString(), e);
		// return "error n/a";
		// }
		// }
		return "";
	}

	public static String getCodeAndTextForListCode(String jobNumber, String fieldName, String code) {

		// try {
		// IListBean listBean = (IListBean)
		// JsfSupporter.findSessionScopedBean(ListBean.BEAN_NAME);
		// List<SelectItem> plist = listBean.getSelectItemsList(fieldName,
		// false);
		//
		// for (int j = 0; j < plist.size(); j++) {
		// SelectItem si = plist.get(j);
		// if (si.getValue().equals(code)) {
		// return si.getValue() + "-" + si.getLabel();
		// }
		// }
		//
		// StringBuffer sb = new StringBuffer();
		// sb.append("<ddl>");
		// sb.append("<text>");
		// sb.append("not available");
		// sb.append("<text>");
		// sb.append("<listname>");
		// sb.append(fieldName);
		// sb.append("</listname>");
		// sb.append("<code>");
		// sb.append(code);
		// sb.append("</code>");
		// sb.append("</ddl>");
		// LOG.fatal(sb.toString());
		//
		// return "n/a";
		// } catch (Exception e) {
		// StringBuffer sb = new StringBuffer();
		// sb.append("<ddl>");
		// sb.append("<text>");
		// sb.append("not available");
		// sb.append("<text>");
		// sb.append("<listname>");
		// sb.append(fieldName);
		// sb.append("</listname>");
		// sb.append("<code>");
		// sb.append(code);
		// sb.append("</code>");
		// sb.append("</ddl>");
		// LOG.fatal(sb.toString(), e);
		// return "error n/a";
		// }

		return null;
	}

	public static String getTextForListCode(String jobNumber, String requestcode, String code, String action) {

		// try {
		// IListBean listBean = (IListBean)
		// JsfSupporter.findSessionScopedBean(ListBean.BEAN_NAME);
		// String siText = listBean.getSelectItemsText(action, requestcode,
		// code);
		// if (siText != null && !"".equals(siText)) {
		// return siText;
		// } else {
		// StringBuffer sb = new StringBuffer();
		// sb.append("<ddl>");
		// sb.append("<text>");
		// sb.append("not available");
		// sb.append("<text>");
		// sb.append("<listname>");
		// sb.append(requestcode);
		// sb.append("</listname>");
		// sb.append("<code>");
		// sb.append(code);
		// sb.append("</code>");
		// sb.append("<action>");
		// sb.append(action);
		// sb.append("</action>");
		// sb.append("</ddl>");
		// LOG.fatal(sb.toString());
		//
		// return "n/a";
		// }
		// } catch (Exception e) {
		// StringBuffer sb = new StringBuffer();
		// sb.append("<ddl>");
		// sb.append("<text>");
		// sb.append("not available");
		// sb.append("<text>");
		// sb.append("<listname>");
		// sb.append(requestcode);
		// sb.append("</listname>");
		// sb.append("<code>");
		// sb.append(code);
		// sb.append("</code>");
		// sb.append("<action>");
		// sb.append(action);
		// sb.append("</action>");
		// sb.append("</ddl>");
		// LOG.fatal(sb.toString(), e);
		// return "Label " + requestcode + "/" + action + "/" + code + " n/a";
		// }
		return null;
	}

	// public static SelectItem getSelectItem(String jobNumber, String
	// fieldName, String code) {

	// try {
	// IListBean listBean = (IListBean)
	// JsfSupporter.findSessionScopedBean(ListBean.BEAN_NAME);
	// List<SelectItem> plist = listBean.getSelectItemsList(fieldName,
	// false);
	//
	// for (int j = 0; j < plist.size(); j++) {
	// SelectItem si = plist.get(j);
	// if (si.getValue().equals(code)) {
	// return si;
	// }
	// }
	//
	// StringBuffer sb = new StringBuffer();
	// sb.append("<ddl>");
	// sb.append("<text>");
	// sb.append("not available");
	// sb.append("<text>");
	// sb.append("<listname>");
	// sb.append(fieldName);
	// sb.append("</listname>");
	// sb.append("<code>");
	// sb.append(code);
	// sb.append("</code>");
	// sb.append("</ddl>");
	// LOG.fatal(sb.toString());
	//
	// return new SelectItem("", "error n/a");
	// } catch (Exception e) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("<ddl>");
	// sb.append("<text>");
	// sb.append("not available");
	// sb.append("<text>");
	// sb.append("<listname>");
	// sb.append(fieldName);
	// sb.append("</listname>");
	// sb.append("<code>");
	// sb.append(code);
	// sb.append("</code>");
	// sb.append("</ddl>");
	// LOG.fatal(sb.toString(), e);
	// return new SelectItem("", "error n/a");
	// }
	// return null;
	// }

	public static Operation createPagingOperation(State requestState, String gridId, String pageNr, String screen,
			String thread) {
		Operation operation = new Operation();
		if (gridId != null) {
			requestState.setAction(gridId);
		}
		requestState.setFunction(PAGE);
		requestState.setRequestCode(pageNr);
		requestState.setScreen(screen);
		requestState.setThread(thread);

		operation.add(requestState.getAsGlobalContainer());
		return operation;
	}

	public static Operation createSortOperation(State requestState, String gridId, String sortFieldName,
			String sortOrder, String screen, String thread) {
		Operation operation = new Operation();
		if (requestState == null) {
			requestState = new State();
		}
		requestState.setAction(gridId);
		requestState.setFunction("*SORT" + sortOrder);
		requestState.setRequestCode(sortFieldName);
		requestState.setScreen(screen);
		requestState.setThread(thread);

		operation.add(requestState.getAsGlobalContainer());
		return operation;
	}

	public static Operation createLoadscreenOperation(IBackingBean backingBean) {
		Operation requestOperation = new Operation();
		Container container = new Container(CONTAINERTYPEGLOBAL);

		container.addField(ACTION, "");
		container.addField(FUNCTION, "LOADSCREEN");
		container.addField(LANGID, "D");
		container.addField(REQUESTCODE, backingBean.getBEAN_NAME().replace("BackingBean", ""));
		container.addField(SCREEN, backingBean.getBEAN_NAME().replace("BackingBean", ""));
		requestOperation.add(container);

		return requestOperation;

	}

	// public static Object findManagedBean(String beanName) {
	// Object result = null;
	// if (LOG.isDebugEnabled()) {
	// LOG.debug("looking for " + beanName);
	// }
	//
	// // check preconditions
	// if (beanName == null || beanName.length() == 0) {
	// throw new IllegalArgumentException("beanName must not be null or
	// zero-length (" + beanName + ").");
	// }
	//
	// // get references to primary involved objects
	// FacesContext facesContext = FacesContext.getCurrentInstance();
	// ELResolver elResolver = facesContext.getApplication().getELResolver();
	// ELContext elContext = facesContext.getELContext();
	//
	// // let Faces do its work
	// result = elResolver.getValue(elContext, null, beanName);
	// if (LOG.isDebugEnabled()) {
	// LOG.debug(" returning " + result);
	// }
	//
	// return result;
	// }

	/**
	 * @return
	 */
	public static INetworkUserProvider getNetworkUserProvider() {
		try {
			Class<?> networkUserProviderClass = Class
					.forName(ApplicationConfigurationBean.getInstance().getNetworkUserProviderClass());

			INetworkUserProvider networkUserProvider = (INetworkUserProvider) networkUserProviderClass.newInstance();

			return networkUserProvider;
		} catch (InstantiationException e) {
			LOG.fatal(e);
		} catch (IllegalAccessException e) {
			LOG.fatal(e);
		} catch (ClassNotFoundException e) {
			LOG.fatal(e);
		} catch (IllegalArgumentException e) {
			LOG.fatal(e);
		} catch (SecurityException e) {
			LOG.fatal(e);
		}
		return null;
	}

	/**
	 * @return
	 */
	public static ILoginHelper getLoginHelper() {
		try {
			Class<?> loginHelperClass = Class.forName(ApplicationConfigurationBean.getInstance().getLoginHelperClass());

			ILoginHelper loginHelper = (ILoginHelper) loginHelperClass.newInstance();

			return loginHelper;
		} catch (InstantiationException e) {
			LOG.fatal(e);
		} catch (IllegalAccessException e) {
			LOG.fatal(e);
		} catch (ClassNotFoundException e) {
			LOG.fatal(e);
		} catch (IllegalArgumentException e) {
			LOG.fatal(e);
		} catch (SecurityException e) {
			LOG.fatal(e);
		}
		return null;
	}

}
