package com.md.mdcms.nav;

import java.util.Vector;

public class Navigation {

	protected String action;
	protected int level;
	protected String requestCode;
	protected String type;
	protected String label;
	protected boolean expand;
	protected Vector<Navigation> navNode = new Vector<Navigation>();

	public Navigation() {
		super();
	}

	public Navigation(String requestCode, String label) {
		super();
		this.requestCode = requestCode;
		this.label = label;
	}

	public Navigation(String requestCode, String type, String label) {
		super();
		this.requestCode = requestCode;
		this.type = type;
		this.label = label;
	}

	public Navigation(Navigation navNode) {
		super();
		this.action = navNode.action;
		this.level = navNode.level;
		this.requestCode = navNode.requestCode;
		this.type = navNode.type;
		this.label = navNode.label;
		this.expand = navNode.expand;
	}

	public String getId() {
		return "id" + requestCode;
	}

	public boolean isSepRendered() {
		if ("sep".equals(type))
			return true;
		else
			return false;
	}

	public boolean isLinkRendered() {
		if ("link".equals(type) || "linklist".equals(type))
			return true;
		else
			return false;
	}

	public Navigation(String requestCode) {
		super();
		this.requestCode = requestCode;
	}

	public String getLabel() {
		return label;
	}

	public String getRequestCode() {
		// if(Character.isDigit(requestCode.charAt(0)))
		// return "XX_".concat(requestCode);
		return requestCode;
	}

	public String getType() {
		return type;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public void setType(String type) {
		this.type = type;
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

	public Vector<Navigation> getNavNode() {
		return navNode;
	}

	public void setNavNode(Vector<Navigation> navNode) {
		this.navNode = navNode;
	}

	public boolean getExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

}
