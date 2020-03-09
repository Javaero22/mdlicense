package com.md.mdcms.backingbean;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.md.mdcms.base.IConstants;
import com.md.mdcms.model.Operation;
import com.md.mdcms.model.State;
import com.md.mdcms.xml.Xml;

public interface IBackingBean extends IConstants, Xml, Serializable {

	Object get(String id);

	String getGridId();

	String getCaption();

	int getSelectionInt();

	void setSelectionInt(int selectionInt);

	String getFunction();

	String getScreenFocus();

	String getRowFocus();

	String getBrowse();

	void setFunction(String function);

	List<?> getList();

	void setList(List<?> baseList);

	String handleFunction();

	String handleFunction(String function);

	void handlePaging(Operation operation);

	String[] getNavSortSeq();

	boolean isMultiGrid();

	Hashtable<String, IBackingBean> getGrids();

	// Map<String, Button> getButtons();

	// void setButtons(Vector<Button> buttons);

	// boolean getBtnRendered(String btnName);

	void updateLists();

	String getTitleBeanName();

	void setGridId(String gridId);

	String getBEAN_NAME();

	Map<String, String> getSCROLLVALUES();

	boolean hasBrowseButton();

	void setHasBrowseButton(boolean b);

	// boolean hasFilter();

	void setHasLinklist(boolean hasLinklist);

	boolean getHasLinklist();

	void whileGenerateBeanInit();

	List<String> getButtonsnottorender();

	List<String> getButtonstorender();

	Vector<String> getLinksasbuttons();

	void setXlspth(String xlspth);

	String getXlspth();

	State getRequestState();

	void setRequestState(State requestState);

	void setResponseState(State responseState);

	State getResponseState();

	void setResponseOperation(Operation responseOperation);

	boolean create();

	void setNewRequestCode(String select);

	Vector<String> getErrorIds();

	String getBackingBeanName();

	void moveResponseToRequestState();

	List<String> getLinklist();

	ButtonBackingBean getButtonBean();

	String getJobNumber();

	void setJobNumber(String jobNumber);

	void setMaxpages(int parseInt);

	PaginatorBackingBean getPaginatorBean();

	void setPaginatorBean(PaginatorBackingBean paginatorBackingBean);

	String getSelectedGridId();

}
