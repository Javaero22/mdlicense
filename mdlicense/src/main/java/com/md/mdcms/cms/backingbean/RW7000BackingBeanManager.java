package com.md.mdcms.cms.backingbean;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlRootElement;

import com.md.mdcms.backingbean.BaseBackingBean;
import com.md.mdcms.model.StringField;
import com.sun.xml.internal.txw2.annotation.XmlElement;

public class RW7000BackingBeanManager extends MdBackingBean {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static final String BEAN_NAME = "RW7000BackingBean";

	private static final List<String> BUTTONSNOTTORENDER = Arrays.asList("PARTNERS", "GO");

	public List<String> getButtonsnottorender() {
		return BUTTONSNOTTORENDER;
	}

	/*
	 * single fields
	 */
	private StringField srlnbr;

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

	public RW7000BackingBeanManager() {
		super();
		init();
	}

	public RW7000BackingBeanManager(HttpServletRequest request, String jobNumber) {
		super(request, jobNumber);

		synchronized (BaseBackingBean.class) {
			if (loginCheckSuccessfull()) {
				boolean ok = create();
			} else {
				gotoLogin();
			}
		}

	}

	private void init() {
	}

	public String reset() {
		String emptyString = "";
		srlnbr.setHtml(emptyString);
		return null;
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

}