package com.md.mdcms.util;

/*
 * Copyright 2009 Javaero-Technology GmbH. All Rights reserved.
 *
 * This software is the proprietary information of GmbH
 * Use is subject to license and non-disclosure terms.
 */

/**
 * René Unternährer / javaero-technology GmbH
 * 20.12.2010
 */

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

public class SystemPropertiesHelper implements
		javax.servlet.ServletContextListener {

	private Logger LOG = Logger.getLogger(SystemPropertiesHelper.class);

	private ServletContext context = null;

	public void contextInitialized(ServletContextEvent event) {
		// context = event.getServletContext();
		// Enumeration<String> params = context.getInitParameterNames();
		//
		// while (params.hasMoreElements()) {
		// String param = (String) params.nextElement();
		// String value = context.getInitParameter(param);
		// if (param.startsWith("tomcat.")) {
		// System.setProperty(param, value);
		// LOG.info(param);
		// }
		// }

	}

	public void contextDestroyed(ServletContextEvent event) {
	}
}
