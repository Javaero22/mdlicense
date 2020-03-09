package com.md.mdcms.model;

import java.io.Serializable;

import com.md.mdcms.base.ApplicationHelper;

public class PageField implements IHtmlField, Serializable {

	private static final long serialVersionUID = 1L;

	public static boolean TEST = false;
	private static final String FALSE = "false";
	private static final String TRUE = "true";
	private static final String READONLY = "read-only";
	private static final String MUTABLE = "mutable";
	private static final String DISABLED = "disabled";
	private static final String HIDDEN = "hidden";
	private static final String BLANK = "";

	private String id;

	private String label;

	private String editable;

	private String browse;

	private String tooltip;

	private String list;

	private String visible;

	private String focus;

	private boolean link;

	private String status;

	private boolean displayDisabled;

	private boolean browserendered;

	protected String value;

	protected boolean update;

	public PageField() {
		super();
	}

	public String getText() {
		return ApplicationHelper.getTextForListCode(this.getId(), this
				.getValue().toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.model.IHtmlField#isRequestImportant()
	 */
	public boolean isRequestImportant() {
		if (editable != null && TRUE.equals(editable)) {
			return true;
		} else if (visible != null && FALSE.equals(visible)) {
			return true;
		} else if (getUpdate()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getOutputrendered() {
		if (visible != null && FALSE.equals(visible)) {
			return false;
		}
		/*
		 * visible
		 */
		else if (editable != null && FALSE.equals(editable)) {
			return true;
		}
		/*
		 * default editable -> true
		 */
		else {
			return false;
		}
	}

	public boolean getInputrendered() {
		if (visible != null && FALSE.equals(visible)) {
			return false;
		}
		/*
		 * visible
		 */
		else if (editable != null && FALSE.equals(editable)) {
			return false;
		}
		/*
		 * default editable -> true
		 */
		else {
			return true;
		}
	}

	public boolean getRendered() {
		if (visible != null && FALSE.equals(visible))
			return false;
		else
			return true;
	}

	public String getDisplay() {
		if (visible == null) {
			return editable;
		} else if (Boolean.parseBoolean(visible)) {
			return editable;
		} else {
			return HIDDEN;
		}
	}

	public String getTooltip() {
		if (TEST) {
			if (tooltip != null && !"".equals(tooltip)) {
				return tooltip + " - " + getId();
			} else {
				return getId() + "-" + getValue();
			}
		}
		return tooltip;
	}

	public String getBrowse() {
		return browse;
	}

	public boolean isBrowserendered() {
		if (browse != null && TRUE.equals(browse)) {
			return true;
		} else {
			return false;
		}
	}

	public String getColHeader() {
		return value;
	}

	public boolean isDisabled() {
		if (editable != null && TRUE.equals(editable)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isDisplayDisabled() {
		if (TRUE.equals(editable) || MUTABLE.equals(editable)) {
			return false;
		} else {
			return true;
		}
	}

	public String getEditable() {
		return editable;
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

	public boolean isLink() {
		return link;
	}

	public String getList() {
		return list;
	}

	public String getStatus() {
		return status;
	}

	public String getVisible() {
		return visible;
	}

	public void setBrowse(String browse) {
		this.browse = browse;
	}

	public void setBrowserendered(boolean browserendered) {
		this.browserendered = browserendered;
	}

	public void setDisplayDisabled(boolean displayDisabled) {
		this.displayDisabled = displayDisabled;
	}

	public void setEditable(String editable) {
		this.editable = editable;
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

	public void setLink(boolean link) {
		this.link = link;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public boolean getUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValue(Object value) {
		this.value = value.toString();
	}

	public Object getValue() {
		return value;
	}

	public String getHtml() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.model.IHtmlField#getAmount()
	 */
	public double getAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
