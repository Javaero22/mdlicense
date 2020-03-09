package com.md.mdcms.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.base.IConstants;
import com.md.mdcms.model.Button;
import com.md.mdcms.model.IButtons;
import com.md.mdcms.model.NavigationComponent;

public class ButtonBackingBean implements IButtons, IConstants, Serializable {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(ButtonBackingBean.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private List<String> buttonsNotToRender;
	private List<String> buttonsToRender;
	private String function;

	private NavigationComponent navigation;

	/* Buttons */
	private Map<String, Button> buttonsMap;
	private Vector<Button> buttons;

	public ButtonBackingBean() {
		super();
	}

	public Map<String, Button> getButtonsMap() {
		if (buttonsMap == null) {
			buttonsMap = new HashMap<String, Button>();
		}
		return buttonsMap;
	}

	/**
	 * @return the buttons
	 */
	public Vector<Button> getButtons() {
		if (buttons == null) {
			buttons = new Vector<Button>();
		}
		return buttons;
	}

	public NavigationComponent getNavigation() {
		if (this.navigation == null) {
			this.navigation = new NavigationComponent();
		}
		return navigation;
	}

	public List<String> getButtonsNotToRender() {
		return buttonsNotToRender;
	}

	public void setButtonsNotToRender(List<String> buttonsNotToRender) {
		this.buttonsNotToRender = buttonsNotToRender;
	}

	public List<String> getButtonsToRender() {
		return buttonsToRender;
	}

	public void setButtonsToRender(List<String> buttonsToRender) {
		this.buttonsToRender = buttonsToRender;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public void setNavigation(NavigationComponent navigation) {
		this.navigation = navigation;
	}

	public void setButtonsMap(Map<String, Button> buttonsMap) {
		this.buttonsMap = buttonsMap;
	}

	public void setButtons(Vector<Button> buttons) {
		this.buttons = buttons;
	}

}
