package com.md.mdcms.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Array implements Serializable {

	private static final long serialVersionUID = -3533119604357436026L;

	@Attribute(required = false)
	private String type;

	@Attribute(required = false)
	private String value;

	@Attribute(required = false)
	private String display;

	@Attribute(required = false)
	private String view;

	@Attribute(required = false)
	private String customValue;

	@ElementList(required = false, inline = true)
	private List<Item> arrayItems;

	public Array() {
		super();
	}

	public Array(List<Item> arrayItems) {
		super();
		this.arrayItems = arrayItems;
	}

	/**
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * @param display
	 *            the display to set
	 */
	public void setDisplay(String display) {
		this.display = display;
	}

	/**
	 * @return the view
	 */
	public String getView() {
		return view;
	}

	/**
	 * @param view
	 *            the view to set
	 */
	public void setView(String view) {
		this.view = view;
	}

	/**
	 * @return the customValue
	 */
	public String getCustomValue() {
		return customValue;
	}

	/**
	 * @param customValue
	 *            the customValue to set
	 */
	public void setCustomValue(String customValue) {
		this.customValue = customValue;
	}

	/**
	 * @return the arrayItems
	 */
	public List<Item> getArrayItems() {
		return arrayItems;
	}

	/**
	 * @param arrayItems
	 *            the arrayItems to set
	 */
	public void setArrayItems(List<Item> arrayItems) {
		this.arrayItems = arrayItems;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
