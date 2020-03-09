package com.md.mdcms.cms.backingbean;

import com.md.mdcms.model.StringField;

public class RW7003BackingBeanManager extends MdBackingBean {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static final String BEAN_NAME = "RW7003BackingBean";

	/*
	 * single fields
	 */
	private StringField cstnam;
	private StringField cstlnd;
	private StringField srlnb1;
	private StringField srlnb2;
	private StringField srlnb3;
	private StringField newver;

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
	private StringField selprd;
	private StringField product;

	/*
	 * Custom field values
	 */

	public RW7003BackingBeanManager() {
		super();
	}

	/**
	 * @return the cstnam
	 */
	public StringField getCstnam() {
		return cstnam;
	}

	/**
	 * @param cstnam
	 *            the cstnam to set
	 */
	public void setCstnam(StringField cstnam) {
		this.cstnam = cstnam;
	}

	/**
	 * @return the cstlnd
	 */
	public StringField getCstlnd() {
		return cstlnd;
	}

	/**
	 * @param cstlnd
	 *            the cstlnd to set
	 */
	public void setCstlnd(StringField cstlnd) {
		this.cstlnd = cstlnd;
	}

	/**
	 * @return the srlnb1
	 */
	public StringField getSrlnb1() {
		return srlnb1;
	}

	/**
	 * @param srlnb1
	 *            the srlnb1 to set
	 */
	public void setSrlnb1(StringField srlnb1) {
		this.srlnb1 = srlnb1;
	}

	/**
	 * @return the srlnb2
	 */
	public StringField getSrlnb2() {
		return srlnb2;
	}

	/**
	 * @param srlnb2
	 *            the srlnb2 to set
	 */
	public void setSrlnb2(StringField srlnb2) {
		this.srlnb2 = srlnb2;
	}

	/**
	 * @return the srlnb3
	 */
	public StringField getSrlnb3() {
		return srlnb3;
	}

	/**
	 * @param srlnb3
	 *            the srlnb3 to set
	 */
	public void setSrlnb3(StringField srlnb3) {
		this.srlnb3 = srlnb3;
	}

	/**
	 * @return the newver
	 */
	public StringField getNewver() {
		return newver;
	}

	/**
	 * @param newver
	 *            the newver to set
	 */
	public void setNewver(StringField newver) {
		this.newver = newver;
	}

	/**
	 * @return the selprd
	 */
	public StringField getSelprd() {
		return selprd;
	}

	/**
	 * @param selprd
	 *            the selprd to set
	 */
	public void setSelprd(StringField selprd) {
		this.selprd = selprd;
	}

	/**
	 * @return the product
	 */
	public StringField getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(StringField product) {
		this.product = product;
	}

}