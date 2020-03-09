package com.md.mdcms.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.md.mdcms.model.StringField;

public abstract class Bean implements IBean, Serializable {

	protected StringField sflopt;

	private static final long serialVersionUID = 1L;
	protected boolean selected;
	protected int selectionInt;
	protected int rownr;

	public Bean() {
		super();
	}

	public Bean(Bean bean) {
		super();
		try {
			BeanUtils.copyProperties(this, bean);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object get(String fieldName) {
		Field f;
		try {
			f = this.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(this);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return null;
	}

	public boolean getFilterRendered() {
		if (selectionInt == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getRendered() {
		if (selectionInt == 0) {
			return false;
		} else {
			return true;
		}
	}

	public int getRownr() {
		return rownr;
	}

	public void setRownr(int rownr) {
		this.rownr = rownr;
	}

	public int getRowint() {
		return rownr - 1;
	}

	/**
	 * @return the rowlink
	 */
	public String getRowlink() {
		return "row" + rownr;
	}

	/**
	 * @return the selectionInt
	 */
	public int getSelectionInt() {
		return selectionInt;
	}

	/**
	 * @param selectionInt
	 *            the selectionInt to set
	 */
	public void setSelectionInt(int selectionInt) {
		this.selectionInt = selectionInt;
	}

}
