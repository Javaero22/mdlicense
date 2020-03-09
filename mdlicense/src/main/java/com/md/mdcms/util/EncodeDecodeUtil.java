package com.md.mdcms.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EncodeDecodeUtil {

	private static final Log LOG = LogFactory.getLog(EncodeDecodeUtil.class);

	public static String encode(String password) {
		return Base64Coder.encodeString(password);
	}

	public static String decode(String password) {
		return Base64Coder.decodeString(password);
	}

}
