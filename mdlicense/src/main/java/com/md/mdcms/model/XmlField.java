package com.md.mdcms.model;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.simpleframework.xml.Attribute;

import com.md.mdcms.base.ApplicationHelper;

public class XmlField {

	private static final String FALSE = "false";
	private static final String TRUE = "true";
	private static final String READONLY = "read-only";
	private static final String MUTABLE = "mutable";
	private static final String DISABLED = "disabled";
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

	public XmlField() {
		super();
	}

	public XmlField(String browse, String editable, String id, String value,
			String visible, String focus) {
		super();
		this.browse = browse;
		setEditable(editable, true);
		this.id = id;
		this.value = value;
		this.visible = visible;
		this.focus = focus;
	}

	public XmlField(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	public XmlField(boolean editable, String fieldName, String value,
			boolean visible, String focus) {
		super();
		setEditable(String.valueOf(editable), true);
		this.id = fieldName;
		this.value = value;
		if (visible)
			this.visible = null;
		else
			this.visible = FALSE;
		this.focus = focus;
	}

	public XmlField(XmlField field) {
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

	public String getEditable() {
		return editable;
	}

	public String getTooltip() {
		if (ApplicationHelper.isTest()) {
			if (tooltip != null && !"".equals(tooltip))
				return tooltip;
			else
				return getId() + "-" + getValue();
		}
		return tooltip;
	}

	public void setEditable(String editable, boolean request) {
		if (editable != null) {
			this.editable = READONLY.equals(editable) || FALSE.equals(editable) ? FALSE
					: TRUE;
		} else {
			this.editable = editable;
		}
	}

	public void setEditable(String editable) {
		this.editable = (editable != null && editable.equals(READONLY) || editable
				.equals(FALSE)) || editable.equals(BLANK) ? FALSE : TRUE;
	}

	public String getBrowse() {
		return browse;
	}

	public String getFocus() {
		return focus;
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getList() {
		return list;
	}

	public String getValue() {
		return value;
	}

	public String getVisible() {
		return visible;
	}

	public void setBrowse(String browse) {
		this.browse = browse;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public boolean isLink() {
		return link;
	}

	public void setLink(boolean link) {
		this.link = link;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getVisible(int i) {
		switch (i) {
		case 1:
			if (visible == null)
				return true;
			else
				return Boolean.valueOf(this.visible);
		default:
			return true;
		}
	}

}
