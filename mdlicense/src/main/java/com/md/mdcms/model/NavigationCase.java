package com.md.mdcms.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "navigation-case")
public class NavigationCase {

	@Element(name = "from-outcome")
	private String fromOutcome;

	@Element(name = "to-view-id")
	private String toViewId;

	@Element(required = false)
	private String redirect;

	public String getFromOutcome() {
		return fromOutcome;
	}

	public String getRedirect() {
		return redirect;
	}

	public String getToViewId() {
		return toViewId;
	}

	public void setFromOutcome(String fromOutcome) {
		this.fromOutcome = fromOutcome;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public void setToViewId(String toViewId) {
		this.toViewId = toViewId;
	}

}
