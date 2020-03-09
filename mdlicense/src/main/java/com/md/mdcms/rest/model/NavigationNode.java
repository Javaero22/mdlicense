package com.md.mdcms.rest.model;

import java.util.Vector;

import com.md.mdcms.nav.INavigation;

public class NavigationNode implements INavigation {

	protected String id;
	protected String action;
	protected int level;
	protected String requestCode;
	protected String type;
	protected String label;
	protected boolean expand;
	protected Vector<INavigation> navNode;

	public NavigationNode() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public Vector<INavigation> getNavNode() {
		return navNode;
	}

	public void setNavNode(Vector<INavigation> navNode) {
		this.navNode = navNode;
	}

}
