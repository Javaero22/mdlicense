package com.md.mdcms.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.simpleframework.xml.Attribute;

public class Field implements Serializable {

	private static final long serialVersionUID = -653441955768087692L;

	private static final String FALSE = "false";
	private static final String TRUE = "true";
	private static final String BLANK = "";

	@Attribute
	private String id;

	@Attribute
	private String value;

	@Attribute(required = false)
	private String label;

	@Attribute(required = false)
	private String editable;

	@Attribute(required = false)
	private String browse;

	@Attribute(required = false)
	private String tooltip;

	@Attribute(required = false)
	private String list;

	@Attribute(required = false)
	private String visible;

	@Attribute(required = false)
	private String focus;

	private boolean link;
	private String status;

	public Field() {
		super();
	}

	public Field(String browse, String editable, String id, String value,
			String visible, String focus) {
		super();
		this.browse = browse;
		this.editable = editable;
		this.id = id;
		this.value = value;
		this.visible = visible;
		this.focus = focus;
	}

	public Field(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	public Field(boolean editable, String fieldName, String value,
			boolean visible, String focus) {
		super();
		this.editable = String.valueOf(editable);
		this.id = fieldName;
		this.value = value;
		if (visible)
			this.visible = null;
		else
			this.visible = FALSE;
		this.focus = focus;
	}

	public Field(Field field) {
		super();
		try {
			BeanUtils.copyProperties(this, field);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the editable
	 */
	public String getEditable() {
		return editable;
	}

	/**
	 * @param editable
	 *            the editable to set
	 */
	public void setEditable(String editable) {
		this.editable = editable;
	}

	/**
	 * @return the browse
	 */
	public String getBrowse() {
		return browse;
	}

	/**
	 * @param browse
	 *            the browse to set
	 */
	public void setBrowse(String browse) {
		this.browse = browse;
	}

	/**
	 * @return the tooltip
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * @param tooltip
	 *            the tooltip to set
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	/**
	 * @return the list
	 */
	public String getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(String list) {
		this.list = list;
	}

	/**
	 * @return the visible
	 */
	public String getVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(String visible) {
		this.visible = visible;
	}

	/**
	 * @return the focus
	 */
	public String getFocus() {
		return focus;
	}

	/**
	 * @param focus
	 *            the focus to set
	 */
	public void setFocus(String focus) {
		this.focus = focus;
	}

	/**
	 * @return the link
	 */
	public boolean isLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(boolean link) {
		this.link = link;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
