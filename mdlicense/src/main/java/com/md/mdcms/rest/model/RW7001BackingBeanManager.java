package com.md.mdcms.rest.model;

import com.md.mdcms.model.NumberField;
import com.md.mdcms.model.StringField;

public class RW7001BackingBeanManager extends RWCommon {

	// private static final String GENXLS = "GENXLS";
	// private static final String GENSAV = "GENSAV";

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
	// public String handleFunction(String requestCode) {
	// if (requestCode.equals(GENXLS) || requestCode.equals(GENSAV)) {
	// String outcome = handleFunction(true);
	// try {
	// String filpthValue = filpth.getHtml();
	// if (filpthValue != null && !"".equals(filpthValue)) {
	// String lclnamValue = lclnam.getHtml();
	// System.out.println("filpth: " + filpthValue + " / lclnam: " +
	// lclnamValue);
	// provideDownload(filpthValue, lclnamValue);
	// }
	// } catch (Exception e) {
	//
	// }
	// return outcome;
	// } else {
	// return handleFunction();
	// }
	// }

	/*
	 * a button or requestCode was used to press
	 */
	// public String btnPress() {
	// String clickedButton = getBtnName();
	// setNewRequestCode(clickedButton.toUpperCase());
	//
	// if (clickedButton.equals(GENXLS) || clickedButton.equals(GENSAV)) {
	// String outcome = handleFunction();
	// try {
	// System.out.println("filpth: " + filpth.getHtml());
	// } catch (Exception e) {
	//
	// }
	// return outcome;
	// } else {
	// return handleFunction();
	// }
	// }

	public RW7001BackingBeanManager() {
		super();
	}

	public StringField getCstnam() {
		return cstnam;
	}

	public void setCstnam(StringField cstnam) {
		this.cstnam = cstnam;
	}

	public StringField getFsrln() {
		return fsrln;
	}

	public void setFsrln(StringField fsrln) {
		this.fsrln = fsrln;
	}

	public StringField getFprod() {
		return fprod;
	}

	public void setFprod(StringField fprod) {
		this.fprod = fprod;
	}

	public StringField getNewver() {
		return newver;
	}

	public void setNewver(StringField newver) {
		this.newver = newver;
	}

	public StringField getFilpth() {
		return filpth;
	}

	public void setFilpth(StringField filpth) {
		this.filpth = filpth;
	}

	public StringField getLclnam() {
		return lclnam;
	}

	public void setLclnam(StringField lclnam) {
		this.lclnam = lclnam;
	}

	public NumberField getCstnbr() {
		return cstnbr;
	}

	public void setCstnbr(NumberField cstnbr) {
		this.cstnbr = cstnbr;
	}

	public StringField getSrlnbr() {
		return srlnbr;
	}

	public void setSrlnbr(StringField srlnbr) {
		this.srlnbr = srlnbr;
	}

	public StringField getProduct() {
		return product;
	}

	public void setProduct(StringField product) {
		this.product = product;
	}

	public StringField getVersion() {
		return version;
	}

	public void setVersion(StringField version) {
		this.version = version;
	}

	public StringField getLickey() {
		return lickey;
	}

	public void setLickey(StringField lickey) {
		this.lickey = lickey;
	}

	public StringField getValunt() {
		return valunt;
	}

	public void setValunt(StringField valunt) {
		this.valunt = valunt;
	}

	public StringField getLicsts() {
		return licsts;
	}

	public void setLicsts(StringField licsts) {
		this.licsts = licsts;
	}

	public StringField getLicnbr() {
		return licnbr;
	}

	public void setLicnbr(StringField licnbr) {
		this.licnbr = licnbr;
	}

}