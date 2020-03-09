package com.md.mdcms.model;

import java.text.DecimalFormat;

import com.md.mdcms.base.IConstants;

public class DateField extends PageField implements IConstants {

	private static final long serialVersionUID = 1L;

	private static DecimalFormat decimalFormat2 = new DecimalFormat("00");
	private static DecimalFormat decimalFormat4 = new DecimalFormat("0000");
	private String value;

	public DateField() {
		super();
	}

}
