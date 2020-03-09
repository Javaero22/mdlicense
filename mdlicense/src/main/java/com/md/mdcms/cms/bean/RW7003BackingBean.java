package com.md.mdcms.cms.bean;

import com.md.mdcms.bean.Bean;
import com.md.mdcms.model.CheckboxField;
import com.md.mdcms.model.StringField;

public class RW7003BackingBean extends Bean {

	private static final long serialVersionUID = 1L;

	/*
	 * grid fields
	 */
	private CheckboxField selprd;
	private StringField product;

	public RW7003BackingBean() {
		super();
	}

	public RW7003BackingBean(RW7003BackingBean bean) {
		super(bean);
	}

	/**
	 * @return the selprd
	 */
	public CheckboxField getSelprd() {
		return selprd;
	}

	/**
	 * @param selprd
	 *            the selprd to set
	 */
	public void setSelprd(CheckboxField selprd) {
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
