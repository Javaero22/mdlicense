package com.md.mdcms.cms.backingbean;

import com.md.mdcms.model.StringField;

public class RW7002BackingBeanManager extends MdBackingBean {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static final String BEAN_NAME = "RW7002BackingBean";

	/*
	 * single fields
	 */
	private StringField usrid;
	private StringField password;

	/*
	 * filter fields
	 */

	/*
	 * custom filter field types
	 */

	/*
	 * position fields
	 */

	/*
	 * grid fields header
	 */

	/*
	 * Custom field values
	 */

	public RW7002BackingBeanManager() {
		super();
	}

	/**
	 * @return the usrid
	 */
	public StringField getUsrid() {
		return usrid;
	}

	/**
	 * @param usrid
	 *            the usrid to set
	 */
	public void setUsrid(StringField usrid) {
		this.usrid = usrid;
	}

	/**
	 * @return the password
	 */
	public StringField getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(StringField password) {
		this.password = password;
	}

}