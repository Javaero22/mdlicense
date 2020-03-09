package com.md.mdcms.util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.base.ApplicationHelper;

public class DateTimeHelper {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(DateTimeHelper.class);

	private static final int nn = 2;
	private static DecimalFormat decimalFormat6 = new DecimalFormat("000000");
	private static DecimalFormat decimalFormat8 = new DecimalFormat("00000000");

	private static DecimalFormat decimalFormat2 = new DecimalFormat("00");
	private static DecimalFormat decimalFormat4 = new DecimalFormat("0000");

	public static String convertTimeToExtern(String value, int size) {

		String timeSep = ApplicationHelper.getUserBean().getMdcmsUser()
				.getTimeSep();

		if (value != null) {
			Double internDoubleValue = Double.parseDouble(value);
			if (internDoubleValue == 0) {
				return "";
			}
			String internStringValue = "";
			if (size == 6) {
				internStringValue = decimalFormat6.format(internDoubleValue);
			} else if (size == 8) {
				internStringValue = decimalFormat8.format(internDoubleValue);
			}

			StringBuffer t1 = new StringBuffer(internStringValue);

			int maxIndex = internStringValue.length() / nn;
			for (int i = 1; i < maxIndex; i++) {
				t1.insert(i * nn + i - 1, timeSep);
			}

			return t1.toString();
		} else {
			return "";
		}
	}

	public static String convertTimeToIntern(String formatedValue) {
		if ("".equals(formatedValue))
			return "";

		String timeSep = ApplicationHelper.getUserBean().getMdcmsUser()
				.getTimeSep();

		String internTime = formatedValue.replace(timeSep, "");

		if (internTime.length() < 6) {
			int il = 6 - internTime.length();
			for (int i = 0; i < il; i++) {
				internTime += "0";
			}
		}

		// if (internTime.length() == 4) {
		// internTime += "00";
		// }

		return internTime;
	}

	public static String convertDateToExtern(String value) {

		String dateFormat = ApplicationHelper.getUserBean().getMdcmsUser()
				.getDateFormat();

		if (value == null || value.length() < 8)
			return "";

		String day = value.substring(6, 8);
		String month = value.substring(4, 6);
		String year = value.substring(0, 4);
		String outDate = dateFormat.replace("DD", day);
		outDate = outDate.replace("MM", month);
		outDate = outDate.replace("YYYY", year);
		return outDate;
	}

	public static String convertDateToIntern(String externDate) {

		if (LOG.isInfoEnabled()) {
			LOG.info("Convert to intern date: " + externDate);
		}

		String dateFormat = ApplicationHelper.getUserBean().getMdcmsUser()
				.getDateFormat();
		// String dateFormat = "DD.MM.YYYY";
		String internDate = "";

		if ("".equals(externDate))
			return "0";

		// int[][] dateFormatIndex = ApplicationHelper.getUserBean()
		// .getDateFormatIndex();
		int[] dateIndex = ApplicationHelper.getUserBean().getDateIndex();
		String dateSepString = ApplicationHelper.getUserBean()
				.getDateSeparator();
		// char dateSepChar = dateSepString.toCharArray()[0];

		externDate = externDate.replace(dateSepString, "X");

		char[] chars = externDate.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isDigit(chars[i]) && 'X' != chars[i]) {
				if (!Character.isDigit(chars[i])) {
					LOG.info("EXCEPTION: (2) invalid date - date format: "
							+ dateFormat
							+ " with only nummeric character allowed");
					return "EXCEPTION:invalid date - date format: "
							+ dateFormat
							+ " with only nummeric character allowed";
				} else {
					if (LOG.isInfoEnabled()) {
						LOG.info("EXCEPTION: (1) invalid date separator - date format: "
								+ dateFormat
								+ " with only nummeric character allowed");
					}
					return "EXCEPTION:invalid date separator " + chars[i]
							+ " - date format: " + dateFormat
							+ " with only nummeric character allowed";
				}
			}
		}

		String[] splitDate = null;

		splitDate = externDate.split("X");

		try {
			int yearInt = Integer.parseInt(splitDate[dateIndex[2]]);
			if (splitDate[dateIndex[2]].length() < 3) {
				yearInt = yearInt + 2000;
			}

			Calendar calendar = GregorianCalendar.getInstance();
			calendar.set(yearInt,
					Integer.parseInt(splitDate[dateIndex[1]]) - 1,
					Integer.parseInt(splitDate[dateIndex[0]]));

			calendar.setLenient(false);

			String day;
			String month;
			String year;

			year = decimalFormat4.format(calendar.get(Calendar.YEAR));
			month = decimalFormat2.format(calendar.get(Calendar.MONTH) + 1);
			day = decimalFormat2.format(calendar.get(Calendar.DATE));

			internDate = year + month + day;
		} catch (Exception e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("EXCEPTION: (3) invalid date - date format: "
						+ dateFormat + " ");
			}
			return "EXCEPTION:invalid date format - date format: " + dateFormat
					+ " invalid date";
		}

		return internDate;
	}

	/**
	 * @param formattedTime
	 * @param i
	 * @return
	 */
	public static String fillWithZero(String inputTime, int toLength) {
		int inputLength = inputTime.length();
		int differenz = toLength - inputLength;
		String outputTime = inputTime;
		if (differenz > 0) {
			for (int i = 0; i < differenz; i++) {
				outputTime = outputTime + "0";
			}
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("InputTime: " + inputTime + " / OutputTime: " + outputTime);
		}
		return outputTime;
	}

}
