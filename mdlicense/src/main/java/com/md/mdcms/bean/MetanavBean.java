package com.md.mdcms.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MetanavBean implements Serializable {

	private static final long serialVersionUID = 7655368545346849803L;

	public static final String BEAN_NAME = "mdjsf_metanav";

	private List<Locale> supportedLocales;

	public static MetanavBean getMetanavBean() {
		// return (MetanavBean) JsfSupporter.findManagedBean(BEAN_NAME);
		return null;
	}

	public String getLocale() {
		UserBean userBean = UserBean.getUserBean();
		Locale toreturn = null;
		// FacesContext fctx = FacesContext.getCurrentInstance();
		if (userBean != null && userBean.getLocale() != null) {
			// user defined locale (allows change of locale in application)
			toreturn = userBean.getLocale();
		} else {
			// request defined locale - taken from Browser accept-language
			// ExternalContext ectx = fctx.getExternalContext();
			// Iterator<Locale> locales = ectx.getRequestLocales();
			Locale locale = null;
			// while (locales.hasNext() && locale == null) {
			// locale = getSupportedLocale(locales.next());
			// }
			// found a matching locale?
			if (locale != null) {
				toreturn = locale;
			}
		}
		if (toreturn == null) {
			// use application default locale as the last option
			// toreturn = fctx.getApplication().getDefaultLocale();
		}
		return toreturn.toString();
	}

	// private void setLocale(String locale) {
	// FacesContext.getCurrentInstance().getViewRoot().setLocale(
	// new Locale(locale));
	// }

	public List<Locale> getSupportedLocales() {
		if (supportedLocales == null) {
			supportedLocales = new ArrayList<Locale>();
			// Application app = FacesContext.getCurrentInstance()
			// .getApplication();
			// TreeSet<Locale> locales = new TreeSet<Locale>(
			// new LocaleComparator());
			// Iterator<Locale> supported = app.getSupportedLocales();
			// while (supported.hasNext()) {
			// locales.add(supported.next());
			// }
			// supportedLocales.addAll(locales);
			// if (supportedLocales.size() == 0) {
			// supportedLocales.add(app.getDefaultLocale());
			// }
		}
		return supportedLocales;
	}

	public String getViewLocale() {
		// return
		// FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
		return null;
	}

	// public String getRequestURI() {
	// FacesContext context = FacesContext.getCurrentInstance();
	// Object request = context.getExternalContext().getRequest();
	// String currentPath = null;
	// if (request instanceof HttpServletRequest) {
	// HttpServletRequest req = (HttpServletRequest) request;
	// currentPath = req.getRequestURI();
	// } else if (request instanceof PortletRequest) {
	// PortletRequest req = (PortletRequest) request;
	// currentPath = req.toString();
	// }
	// return currentPath;
	// }

	public String changeLocale() {
		// FacesContext fctx = FacesContext.getCurrentInstance();
		// ExternalContext ectx = fctx.getExternalContext();
		// Map<String, String> params = ectx.getRequestParameterMap();
		// String newLocale = params.get("userLocale");
		// if (newLocale != null) {
		// UserBean userBean = UserBean.getUserBean();
		// if (userBean != null) {
		// StringTokenizer st = new StringTokenizer(newLocale, "_");
		// String lang = st.nextToken();
		// // no country must be empty string for Locale
		// String country = st.hasMoreTokens() ? st.nextToken() : "";
		// userBean.setLocale(new Locale(lang, country));
		// // getNavigationContainer().dropRootNode();
		// }
		// }
		// prevent view-magic ...
		// JsfSupporter.invalidateView();
		return null;
	}

	private Locale getSupportedLocale(Locale candidate) {
		Iterator<Locale> supported = getSupportedLocales().iterator();
		while (supported.hasNext()) {
			Locale supportedLocale = supported.next();
			if (supported.toString().equals(candidate.toString())
					|| supportedLocale.getLanguage().equals(candidate.getLanguage())) {
				return candidate;
			}
		}
		return null;
	}

	private static class LocaleComparator implements Comparator<Locale> {

		private static final String LANG_ORDER = "en#de#fr#it#es";

		public int compare(Locale locale1, Locale locale2) {
			String l1 = locale1.getLanguage().toLowerCase();
			String l2 = locale2.getLanguage().toLowerCase();
			int pos1 = LANG_ORDER.indexOf(l1);
			int pos2 = LANG_ORDER.indexOf(l2);

			if (pos1 == -1) {
				pos1 = Integer.MAX_VALUE;
			}
			if (pos2 == -1) {
				pos2 = Integer.MAX_VALUE;
			}
			if (pos1 == pos2) {
				// combine country and variant with a space - must be a space to
				// have "en" sorted in front of "en_US" (compare "en " with
				// "en_US").
				String s1 = locale1.getCountry() + " " + locale1.getVariant();
				String s2 = locale2.getCountry() + " " + locale2.getVariant();
				return s1.compareTo(s2);
			} else {
				return pos1 - pos2;
			}
		}

	}
}
