package com.md.mdcms.backingbean;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MimePropertiesBean extends Properties {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(MimePropertiesBean.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static final String BEAN_NAME = "mimePropertiesBean";

	public static MimePropertiesBean getInstance() {
		// return (MimePropertiesBean) JsfSupporter.findManagedBean(BEAN_NAME);
		return null;
	}

	public String getMimeType(String fileExtension) {
		String contentType = getMimeExtensionMap().get(fileExtension);
		if (contentType != null && !"".equals(contentType)) {
			return contentType;
		} else {
			LOG.info("ContentType for file extension: " + fileExtension + " not found!");
			return "application/octet-stream";
		}
	}

	private Map<String, String> mimeExtensionMap;

	/**
	 * @return the mimeExtensionMap
	 */
	public Map<String, String> getMimeExtensionMap() {
		return mimeExtensionMap;
	}

	/**
	 * @param mimeExtensionMap
	 *            the mimeExtensionMap to set
	 */
	public void setMimeExtensionMap(Map<String, String> mimeExtensionMap) {
		this.mimeExtensionMap = mimeExtensionMap;
	}

}
