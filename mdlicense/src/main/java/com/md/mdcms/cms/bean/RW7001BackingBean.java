package com.md.mdcms.cms.bean;

import com.md.mdcms.bean.Bean;
import com.md.mdcms.model.DateField;
import com.md.mdcms.model.NumberField;
import com.md.mdcms.model.StringField;

public class RW7001BackingBean extends Bean {

	private static final long serialVersionUID = 1L;

	/*
	 * grid fields
	 */
	private StringField srlnbr;
	private StringField product;
	private StringField version;
	private StringField lickey;
	private DateField valunt;
	private StringField licsts;
	private NumberField licnbr;

	public RW7001BackingBean() {
		super();
	}

	public RW7001BackingBean(RW7001BackingBean bean) {
		super(bean);
	}

	/**
	 * @return the srlnbr
	 */
	public StringField getSrlnbr() {
		return srlnbr;
	}

	/**
	 * @param srlnbr
	 *            the srlnbr to set
	 */
	public void setSrlnbr(StringField srlnbr) {
		this.srlnbr = srlnbr;
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

	/**
	 * @return the version
	 */
	public StringField getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(StringField version) {
		this.version = version;
	}

	/**
	 * @return the lickey
	 */
	public StringField getLickey() {
		return lickey;
	}

	/**
	 * @param lickey
	 *            the lickey to set
	 */
	public void setLickey(StringField lickey) {
		this.lickey = lickey;
	}

	/**
	 * @return the valunt
	 */
	public DateField getValunt() {
		return valunt;
	}

	/**
	 * @param valunt
	 *            the valunt to set
	 */
	public void setValunt(DateField valunt) {
		this.valunt = valunt;
	}

	/**
	 * @return the licsts
	 */
	public StringField getLicsts() {
		return licsts;
	}

	/**
	 * @param licsts
	 *            the licsts to set
	 */
	public void setLicsts(StringField licsts) {
		this.licsts = licsts;
	}

	/**
	 * @return the licnbr
	 */
	public NumberField getLicnbr() {
		return licnbr;
	}

	/**
	 * @param licnbr
	 *            the licnbr to set
	 */
	public void setLicnbr(NumberField licnbr) {
		this.licnbr = licnbr;
	}

}
