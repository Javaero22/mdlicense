package com.md.mdcms.nav;

import java.util.List;

public interface INavigation {

	List<INavigation> getNavNode();

	int getLevel();

	String getRequestCode();

	String getLabel();

	String getType();

	void setLabel(String label);
}
