package com.md.mdcms.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Attribute;

public class Item implements Serializable {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(Item.class);
	private static final long serialVersionUID = -4115935985222473255L;

	private static final String FALSE = "false";
	private static final String TRUE = "true";
	private static final String READONLY = "read-only";
	private static final String MUTABLE = "mutable";

	@Attribute(required = false)
	private String id;

	@Attribute(required = false)
	private String key1;

	@Attribute(required = false)
	private String key2;

	@Attribute(required = false)
	private String key3;

	@Attribute(required = false)
	private String label;

	@Attribute(required = false)
	private String fieldId;

	@Attribute(required = false)
	private String code;

	@Attribute(required = false)
	private String type;

	@Attribute(required = false)
	private String value;

	@Attribute(required = false)
	private String edit;

	@Attribute(required = false)
	private String tooltip;

	@Attribute(required = false)
	private String list;

	@Attribute(required = false)
	private String linklist;

	@Attribute(required = false)
	private String linklistvalue;

	@Attribute(required = false)
	private String editable;

	@Attribute(required = false)
	private String visible;

	@Attribute(required = false)
	private String browse;

	@Attribute(required = false)
	private String severity;

	@Attribute(required = false)
	private String status;

	@Attribute(required = false)
	private String total;

	@Attribute(required = false)
	private String newp;

	@Attribute(required = false)
	private String due;

	@Attribute(required = false)
	private String link;

	@Attribute(required = false)
	private String focus;

	@Attribute(required = false)
	private String async;

	public String getEditable(int type) {
		switch (type) {
		// -1-> true -> false or false -> true
		case -1:
			if (editable == null) {
				return TRUE;
			} else if (editable.equals(FALSE) || editable.equals(READONLY)) {
				return TRUE;
			} else if (editable.equals(TRUE) || editable.equals(MUTABLE)) {
				return FALSE;
			} else
				return TRUE;

			// 1-> editable in boolean, ...rendered
		case 1:
			if (editable == null) {
				return FALSE;
			} else if (editable.equals(FALSE) || editable.equals(READONLY)) {
				return FALSE;
			} else if (editable.equals(TRUE) || editable.equals(MUTABLE)) {
				return TRUE;
			} else
				return FALSE;

			// 2-> ...Disabled, ...DisplayDisabled
		case 2:
			if (editable == null) {
				return TRUE;
			} else if (editable.equals(FALSE) || editable.equals(READONLY)) {
				return TRUE;
			} else if (editable.equals(TRUE) || editable.equals(MUTABLE)) {
				return FALSE;
			} else
				return TRUE;

		case 3:
			if (editable == null) {
				return READONLY;
			} else if (editable.equals(FALSE) || editable.equals(READONLY)) {
				return READONLY;
			} else if (editable.equals(TRUE) || editable.equals(MUTABLE)) {
				return MUTABLE;
			} else
				return READONLY;

		default:
			return null;
		}
	}

	public String getTooltip() {
		return tooltip;
	}

	public String getEditable() {
		return editable;
	}

	public boolean getVisible(int i) {
		switch (i) {
		case 1:
			if (this.visible == null)
				return true;
			else
				return Boolean.valueOf(this.visible);
		default:
			return true;
		}
	}

	public String getVisible() {
		return visible;
	}

	public Item(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	public Item(String editable, String focus, String id, String value,
			String visible) {
		super();
		this.editable = editable;
		this.focus = focus;
		this.id = id;
		this.value = value;
		this.visible = visible;
	}

	public Item(boolean editable, String focus, String id, String value,
			boolean visible) {
		super();
		this.editable = String.valueOf(editable);
		this.focus = focus;
		this.id = id;
		this.value = value;
		if (visible)
			this.visible = null;
		else
			this.visible = String.valueOf(visible);
	}

	public void asField(IHtmlField pageField) {
		if (pageField instanceof DateField) {
			((DateField) pageField).setValue(this.getValue());
		} else if (pageField instanceof CheckboxField) {
			((CheckboxField) pageField).setValue(this.getValue());
		}

		try {
			BeanUtils.copyProperties(pageField, this);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return pageField;
	}

	public Item() {
		super();
	}

	public Item(Item item) {
		super();
		try {
			BeanUtils.copyProperties(this, item);
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
	 * @return the fieldId
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId
	 *            the fieldId to set
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

	/**
	 * @return the edit
	 */
	public String getEdit() {
		return edit;
	}

	/**
	 * @param edit
	 *            the edit to set
	 */
	public void setEdit(String edit) {
		this.edit = edit;
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
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
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

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the newp
	 */
	public String getNewp() {
		return newp;
	}

	/**
	 * @param newp
	 *            the newp to set
	 */
	public void setNewp(String newp) {
		this.newp = newp;
	}

	/**
	 * @return the due
	 */
	public String getDue() {
		return due;
	}

	/**
	 * @param due
	 *            the due to set
	 */
	public void setDue(String due) {
		this.due = due;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
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
	 * @return the async
	 */
	public String getAsync() {
		return async;
	}

	/**
	 * @param async
	 *            the async to set
	 */
	public void setAsync(String async) {
		this.async = async;
	}

	/**
	 * @param tooltip
	 *            the tooltip to set
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	/**
	 * @param editable
	 *            the editable to set
	 */
	public void setEditable(String editable) {
		this.editable = editable;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(String visible) {
		this.visible = visible;
	}

	/**
	 * @return the linklist
	 */
	public String getLinklist() {
		return linklist;
	}

	/**
	 * @param linklist
	 *            the linklist to set
	 */
	public void setLinklist(String linklist) {
		this.linklist = linklist;
	}

	/**
	 * @return the linklistvalue
	 */
	public String getLinklistvalue() {
		return linklistvalue;
	}

	/**
	 * @param linklistvalue
	 *            the linklistvalue to set
	 */
	public void setLinklistvalue(String linklistvalue) {
		this.linklistvalue = linklistvalue;
	}

	/**
	 * @return the key1
	 */
	public String getKey1() {
		return key1;
	}

	/**
	 * @param key1
	 *            the key1 to set
	 */
	public void setKey1(String key1) {
		this.key1 = key1;
	}

	/**
	 * @return the key2
	 */
	public String getKey2() {
		return key2;
	}

	/**
	 * @param key2
	 *            the key2 to set
	 */
	public void setKey2(String key2) {
		this.key2 = key2;
	}

	/**
	 * @return the key3
	 */
	public String getKey3() {
		return key3;
	}

	/**
	 * @param key3
	 *            the key3 to set
	 */
	public void setKey3(String key3) {
		this.key3 = key3;
	}

}
