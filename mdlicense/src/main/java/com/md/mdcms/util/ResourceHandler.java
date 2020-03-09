package com.md.mdcms.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceHandler {
	private static final String CONFIGURATION_BUNDLE_NAME = "configuration";
	private static final String MESSAGE_BUNDLE_NAME = "message";
	private static final String LOGIN_BUNDLE_NAME = "userLogin";

	private ResourceHandler() {
	}

	private static final ResourceBundle CONFIGURATION_BUNDLE = ResourceBundle.getBundle(CONFIGURATION_BUNDLE_NAME);

	private static final ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGE_BUNDLE_NAME);

	private static final ResourceBundle LOGIN_BUNDLE = ResourceBundle.getBundle(LOGIN_BUNDLE_NAME);

	public static String getConfigurationResourceString(String key) {
		try {
			return CONFIGURATION_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getMessageResourceString(String key) {
		try {
			return MESSAGE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getLoginResourceString(String key) {
		try {
			return LOGIN_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static ResourceBundle getResourceFor(String ressourceName) {
		return ResourceBundle.getBundle(ressourceName);
	}

}
