package com.md.mdcms.model;

public class SelectItemData {
	private String view;
	private boolean customValue;
	// private Vector<List<SelectItem>> selectItems;

	// public SelectItemData(String view, boolean customValue,
	// Vector<List<SelectItem>> selectItems) {
	// this.view = view;
	// this.customValue = customValue;
	// this.selectItems = selectItems;
	// }

	public SelectItemData() {
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
	public boolean isCustomValue() {
		return customValue;
	}

	/**
	 * @param customValue
	 *            the customValue to set
	 */
	public void setCustomValue(boolean customValue) {
		this.customValue = customValue;
	}

	/**
	 * @return the selectItems
	 */
	// public Vector<List<SelectItem>> getSelectItems() {
	// return selectItems;
	// }

	/**
	 * @param selectItems
	 *            the selectItems to set
	 */
	// public void setSelectItems(Vector<List<SelectItem>> selectItems) {
	// this.selectItems = selectItems;
	// }

}
