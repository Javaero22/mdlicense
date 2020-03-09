package com.md.mdcms.model;

public interface IHtmlField {

	void setFocus(String focus);

	Object getValue();

	boolean getUpdate();

	void setUpdate(boolean update);

	void setValue(Object value);

	String getText();

	boolean isRequestImportant();

	String getVisible();

	String getEditable();

	String getHtml();

	void setBrowse(String FALSE);

	double getAmount();

}
