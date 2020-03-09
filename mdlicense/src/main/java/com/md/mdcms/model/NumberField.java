package com.md.mdcms.model;

public class NumberField extends PageField {

	private static final long serialVersionUID = 1L;

	private int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getValue() {
		return String.valueOf(number);
	}

	public void setValue(int number) {
		this.number = number;
	}

	public void setValue(String value) {
		if ("".equals(value)) {
			this.number = 0;
		} else {
			this.number = Integer.parseInt(value);
		}
	}

	public void setValue(Object value) {
		this.number = Integer.parseInt(value.toString());
	}

}
