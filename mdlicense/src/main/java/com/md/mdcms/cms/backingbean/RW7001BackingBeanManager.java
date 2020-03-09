package com.md.mdcms.cms.backingbean;

import java.util.Arrays;
import java.util.List;

import com.md.mdcms.model.NumberField;
import com.md.mdcms.model.StringField;

public class RW7001BackingBeanManager extends MdBackingBean {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private static final String GENXLS = "GENXLS";
	private static final String GENSAV = "GENSAV";

	public static final String BEAN_NAME = "RW7001BackingBean";

	private static final List<String> BUTTONSNOTTORENDER = Arrays.asList("GO",
			"SEARCH");

	public List<String> getButtonsnottorender() {
		return BUTTONSNOTTORENDER;
	}

	/*
	 * single fields
	 */
	private StringField cstnam;
	private StringField fsrln;
	private StringField fprod;
	private StringField newver;

	/*
	 * hidden
	 */
	private StringField filpth;
	private StringField lclnam;
	private NumberField cstnbr;

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
	private StringField srlnbr;
	private StringField product;
	private StringField version;
	private StringField lickey;
	private StringField valunt;
	private StringField licsts;
	private StringField licnbr;

	/*
	 * Custom field values
	 */

	/*
	 * request by button
	 */
	public String handleFunction(String requestCode) {
		if (requestCode.equals(GENXLS) || requestCode.equals(GENSAV)) {
			String outcome = handleFunction(true);
			try {
				String filpthValue = filpth.getHtml();
				if (filpthValue != null && !"".equals(filpthValue)) {
					String lclnamValue = lclnam.getHtml();
					System.out.println("filpth: " + filpthValue + " / lclnam: "
							+ lclnamValue);
					provideDownload(filpthValue, lclnamValue);
				}
			} catch (Exception e) {

			}
			return outcome;
		} else {
			return handleFunction();
		}
	}

	/*
	 * a button or requestCode was used to press
	 */
	public String btnPress() {
		String clickedButton = getBtnName();
		setNewRequestCode(clickedButton.toUpperCase());

		if (clickedButton.equals(GENXLS) || clickedButton.equals(GENSAV)) {
			String outcome = handleFunction();
			try {
				System.out.println("filpth: " + filpth.getHtml());
			} catch (Exception e) {

			}
			return outcome;
		} else {
			return handleFunction();
		}
	}

	public RW7001BackingBeanManager() {
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
	 * @return the fsrln
	 */
	public StringField getFsrln() {
		return fsrln;
	}

	/**
	 * @param fsrln
	 *            the fsrln to set
	 */
	public void setFsrln(StringField fsrln) {
		this.fsrln = fsrln;
	}

	/**
	 * @return the fprod
	 */
	public StringField getFprod() {
		return fprod;
	}

	/**
	 * @param fprod
	 *            the fprod to set
	 */
	public void setFprod(StringField fprod) {
		this.fprod = fprod;
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
	 * @return the filpth
	 */
	public StringField getFilpth() {
		return filpth;
	}

	/**
	 * @param filpth
	 *            the filpth to set
	 */
	public void setFilpth(StringField filpth) {
		this.filpth = filpth;
	}

	/**
	 * @return the lclnam
	 */
	public StringField getLclnam() {
		return lclnam;
	}

	/**
	 * @param lclnam
	 *            the lclnam to set
	 */
	public void setLclnam(StringField lclnam) {
		this.lclnam = lclnam;
	}

	/**
	 * @return the cstnbr
	 */
	public NumberField getCstnbr() {
		return cstnbr;
	}

	/**
	 * @param cstnbr
	 *            the cstnbr to set
	 */
	public void setCstnbr(NumberField cstnbr) {
		this.cstnbr = cstnbr;
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
	public StringField getValunt() {
		return valunt;
	}

	/**
	 * @param valunt
	 *            the valunt to set
	 */
	public void setValunt(StringField valunt) {
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
	public StringField getLicnbr() {
		return licnbr;
	}

	/**
	 * @param licnbr
	 *            the licnbr to set
	 */
	public void setLicnbr(StringField licnbr) {
		this.licnbr = licnbr;
	}

}