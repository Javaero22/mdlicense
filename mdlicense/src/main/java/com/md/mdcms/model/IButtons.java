package com.md.mdcms.model;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.md.mdcms.base.IConstants;

public interface IButtons extends IConstants {

	List<String> NOBUTTON = Arrays.asList("CLICK", "BROWSE", "POSITION",
			"SELECT", "EXCEL", "PAGEUP", "PAGEDOWN", "E", "SEARCH", "REPORT");

	List<String> DEFAULTBUTTONORDER = Arrays.asList("BACK", "PREV", "PCANCEL",
			"CANCEL", "REFRESH", "LOADBODY", "UNTDFT", "CREATE", "AUTH",
			"CALCULATE", "KALKR", "ADD", "ACTIVATE", "DELETE", "DELREQ",
			"FREE", "FREEALL", "LOCK", "LOCKALL", "EDIT", "SAVE", "INARBEIT",
			"CLOSE", "E", "NEXT", "VTRWF0");

	Vector<Button> getButtons();

}
