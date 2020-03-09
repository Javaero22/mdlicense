package com.md.mdcms.backingbean;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.model.Operation;

public class GlobalBackingBeanManager {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(GlobalBackingBeanManager.class);

	private static GlobalBackingBeanManager instance = null;

	public static final String BEAN_NAME = "globalBackingBean";
	public static String zoneId = "";

	public String screenTitle;

	private Map<String, Operation> helperOperation;

	private static final Map<String, String> dateSeparators = new HashMap<String, String>();

	static {
		dateSeparators.put("/", "0");
		dateSeparators.put("-", "1");
		dateSeparators.put(".", "2");
		dateSeparators.put(",", "3");
		dateSeparators.put(" ", "4");
	}

	private static final Map<String, String> dateFormats = new HashMap<String, String>();

	static {
		dateFormats.put("DMY", "0");
		dateFormats.put("MDY", "1");
		dateFormats.put("YMD", "2");
	}

	public GlobalBackingBeanManager() {
		super();
	}

	public static GlobalBackingBeanManager getInstance() {
		if (instance == null) {
			synchronized (StartConfigurationBean.class) {
				if (instance == null) {
					instance = new GlobalBackingBeanManager();
				}
			}
		}
		return instance;
	}

	public String getCurrentDate() {

		if ("".equals(zoneId)) {
			zoneId = ApplicationConfigurationBean.getInstance().getGmtOffsetForTS();
			if (zoneId != null && !"".equals(zoneId)) {
				zoneId = "GMT" + zoneId;
			} else {
				zoneId = "GMT";
			}
		}

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);
		String gmtDateFormat = dateFormat.format(date);

		TimeZone newzone = TimeZone.getTimeZone(zoneId);

		dateFormat.setTimeZone(newzone);
		gmtDateFormat = dateFormat.format(date);

		return gmtDateFormat;
	}

	/**
	 * @return the dateseparators
	 */
	public Map<String, String> getDateseparators() {
		return dateSeparators;
	}

	/**
	 * @return the dateformats
	 */
	public Map<String, String> getDateformats() {
		return dateFormats;
	}

	private String getScreenTitle() {
		return screenTitle;
	}

	public void setScreenTitle(String screenTitle) {
		this.screenTitle = screenTitle;
	}

	public Map<String, Operation> getHelperOperation() {
		if (this.helperOperation == null) {
			this.helperOperation = new HashMap<String, Operation>();
		}
		return helperOperation;
	}

}
