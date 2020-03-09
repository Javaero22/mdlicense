package com.md.mdcms.base;

public interface IConstants {

	String BTN = "btn_";
	String LNK = "lnk_";
	String BLANK = "";
	String SLASH = " / ";
	String UPDATE = "Update";
	String TRUE = "true";
	String FALSE = "false";
	String LINK = "link";
	String ROWNR = "rownr";
	String LINKRENDERED = "linkrendered";
	String SFLOPT = "SFLOPT";
	String EDIT = "edit";
	String EDITVISIBLE = "editvisible";
	String COPY = "copy";
	String COPYVISIBLE = "copyvisible";
	String EDITRENDERED = "editrendered";
	String COPYRENDERED = "copyrendered";
	String HIDDEN = "hidden";
	String REMOVED = "removed";
	String DECFORM = "##############0.000000000000";
	String LOADSCREEN = "LOADSCREEN";
	String LINK_ = "LINK_";
	String SCREENTITLE = "screenTitle";
	String GRIDID = "gridId";
	String MAXROWS = "maxRows";
	String CAPTION = "CAPTION";
	String SCREEN_UC = "SCREEN";
	String NULL0 = "0";
	String NULL8 = "00000000";
	String SET = "set";
	String DISPLAYDISABLED = "DisplayDisabled";
	String CHECKBOXRENDERED = "CheckboxRendered";
	String REQUESTCODELIST = "REQUESTCODELIST";
	String MENU = "MENU";
	String SECTION = "SECTION";
	String SEPSINGLE = "*SEP";
	String SELECT = "SELECT";
	String REFRESH = "REFRESH";
	String CALRLST = "CALRLST";
	String SEARCH = "SEARCH";
	String SELECTRENDERED = "selectrendered";
	String BEANRENDERED = "beanrendered";
	String CANCEL = "CANCEL";
	String PAGE = "*PAGE";
	String MUTABLE = "mutable";

	String COPYROWFUNCTION = "COPY";
	String EDITROWFUNCTION = "EDIT";
	String EDITFUNCTIONCODE = "2";
	String EDITROWRENDERED = "editrowrendered";

	String EXCEPTION = "EXCEPTION";
	String NEWLINE = "\n";
	String NEWLINE_UNICODE = "&#x000D;";
	String RETURN = "\r";
	String LINEBREAK = "<br/>";
	String PROD = "prod";
	String SEPARATOR = "*SEP";
	String DOUBLESEPARATOR = "*DSEP";

	String MANAGER = "Manager";
	String BEAN = "Bean";
	String BACKINGBEAN = "BackingBean";
	String BACKINGBEANMANAGER = "BackingBeanManager";

	String BROWSERENDERED = "Browserendered";
	String COLHEADER = "ColHeader";
	String DISABLED = "Disabled";
	String RADIO = "Radio";
	String READONLY = "Readonly";
	String DISPLAY = "Display";
	String INPUTRENDERED = "Inputrendered";
	String LABEL = "Label";
	String LIST = "List";
	String OUTPUTRENDERED = "Outputrendered";
	String FOCUS = "Focus";
	String RENDERED = "rendered";
	String STYLECLASS = "StyleClass";
	String TOOLTIP = "Tooltip";
	String CHECKBOX = "Checkbox";

	String ACTION = "ACTION";
	String FUNCTION = "FUNCTION";
	String LANGID = "LANGID";
	String REQUESTCODE = "REQUESTCODE";
	String SCREEN = "SCREEN";
	String BROWSE = "BROWSE";
	String CLICK = "CLICK";
	String THREAD = "THREAD";
	String ALPHA1 = "ALPHA1";
	String ALPHA2 = "ALPHA2";
	String NUMERIC1 = "NUMERIC1";
	String NUMERIC2 = "NUMERIC2";

	String XLSPTH = "xlspth";

	String variablesToAvoid = "serialVersionUID,BEAN_NAME,actualList,baseList,functionList,"
			+ "function,selectionInt,sortByFieldName,ascending,link,"
			+ "propertyAscendingComparator,propertyDescendingComparator,"
			+ "filterState,caption,noOfRowsPerPage,hasPager,dateConverter,reset,save,select,"
			+ "back,pageup,pagedown,testDate,selectedItem,tableHeader,tableTitle,navSortSeq,"
			+ "resetNumVariables,resetBooleanVariables,variante,scrollTo,gridNames,editvisible,"
			+ "copyvisible,_DOT";

	String[] variableToAvoidEndsWith = { DISPLAY, TOOLTIP, LABEL, RENDERED,
			STYLECLASS, INPUTRENDERED, OUTPUTRENDERED, COLHEADER, "_f",
			"PageTitle", "Disabled", "List", "Link" };

	String[] variableToAvoidStartsWith = { "sort", "containerElement" };

	String MD_WORKFLOW_PROPERTIES = "mdWorkflowClientProperties.txt";
	int maxAttachmentFileLength = 80;

	String PASSWORD_EXPIRED = "password_expired";

}
