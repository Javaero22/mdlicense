package com.md.mdcms.backingbean;

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
	private String function = "";

	private NavigationComponent navigation;

	/* Buttons */
	private Map<String, Button> buttonsMap;
	private Vector<Button> buttons;

	public ButtonBackingBean() {
		super();
	}

	public Map<String, Button> getButtonsMap() {
		if (buttonsMap == null)
			buttonsMap = new HashMap<String, Button>();
		return buttonsMap;
	}

	/**
	 * @return the buttons
	 */
	public Vector<Button> getButtons() {
		if (buttons == null)
			buttons = new Vector<Button>();
		return buttons;
	}

	public void clear() {
		if (buttons != null)
			buttons.clear();
	}

	public List<List<Button>> getButtonBars() {
		List<List<Button>> buttonBars = new ArrayList<List<Button>>();
		List<Button> buttons = new ArrayList<Button>();

		Vector<Button> restButtons = getButtons();
		if (restButtons != null && !restButtons.isEmpty()) {
			for (Iterator<Button> iterator = restButtons.iterator(); iterator.hasNext();) {
				Button button = (Button) iterator.next();
				if (!buttonsNotToRender.contains(button.getRc())) {
					if (!NOBUTTON.contains(button.getRc()))
						buttons.add(button);
				}
			}
		}

		buttonBars.add(buttons);

		return buttonBars;
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

	public NavigationComponent getNavigation() {
		if (this.navigation == null) {
			this.navigation = new NavigationComponent();
		}
		return navigation;
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
