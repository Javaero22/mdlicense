package com.md.mdcms.model;

public class AmountField_Deprecated extends PageField {

	private static final long serialVersionUID = 1L;

	private double amount;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getValue() {
		return String.valueOf(amount);
	}

	public void setValue(double value) {
		this.amount = value;
	}

	public void setValue(String value) {
		if ("".equals(value))
			this.amount = 0D;
		else
			this.amount = Double.parseDouble(value);
	}

	public void setValue(Object value) {
		this.amount = Double.valueOf(value.toString());
	}

}
