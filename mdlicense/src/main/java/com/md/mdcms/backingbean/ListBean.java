package com.md.mdcms.backingbean;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.base.IConstants;
import com.md.mdcms.model.SelectItemData;

public class ListBean implements IListBean, Serializable, IConstants {

	/** Log instance for this class. */
	private static final Log LOG = LogFactory.getLog(ListBean.class);

	private static final long serialVersionUID = 1L;

	/*
	 * special files
	 */
	private static Map<String, String> LIST_PATH_MAP = new Hashtable<String, String>();

	// static {
	// LIST_PATH_MAP
	// .put("FAGP",
	// "E:/dev/mdWorkflow/kepler/workspace/mdWorkflowJSF12/src/com/md/mdcms/backingbean/yafora_fagp.xml");
	// }

	public static final String BEAN_NAME = "listBean";

	private static final long reloadWaitTime = 900000; // 15' (1000 * 60 * 15)
	protected Hashtable<String, SelectItemData> codeLists = new Hashtable<String, SelectItemData>();
	protected Hashtable<String, Long> lastLoaded = new Hashtable<String, Long>();

	// public synchronized List<SelectItem> getSelectItemsList(String listName,
	// boolean filtered) {
	// return getSelectItemsList(listName, filtered, false);
	// }

	// public synchronized List<SelectItem> getSelectItemsList(String listName,
	// boolean filtered, boolean reload) {
	// return getSelectItemsList(listName, filtered, reload, false);
	// }

	// public synchronized List<SelectItem> getSelectItemsList(String listName,
	// boolean filtered, boolean reload, boolean loadFromSpecialFile) {
	//
	// List<SelectItem> codeList = new ArrayList<SelectItem>();
	//
	// if (!ApplicationHelper.getUserBean().isLoggedOn()) {
	// return codeList;
	// }
	//
	// String userLanguage = ApplicationHelper.getUserBean().getUserLanguage();
	// String key = userLanguage.concat(listName);
	//
	// long newLoad = GregorianCalendar.getInstance().getTimeInMillis();
	// try {
	// if (!reload && codeLists.get(key) != null
	// && newLoad - lastLoaded.get(key) < reloadWaitTime) {
	// return getDdlCopy(key, filtered);
	// }
	// } catch (Exception e) {
	// }
	//
	// lastLoaded.remove(key);
	//
	// boolean loadedFromFile = false;
	// if (loadFromSpecialFile) {
	// try {
	// String filePath = LIST_PATH_MAP.get(listName.toUpperCase());
	// StringBuffer xmlInputStringBuf = new StringBuffer();
	// if (filePath != null) {
	// File inputFile = new File(filePath);
	// FileReader fr = null;
	// try {
	// fr = new FileReader(inputFile);
	// BufferedReader br = new BufferedReader(fr);
	//
	// String line = null;
	// while ((line = br.readLine()) != null) {
	// xmlInputStringBuf.append(line);
	// }
	// br.close();
	//
	// SelectItemData sid = DropDownList
	// .getDropDownListsFromFile(xmlInputStringBuf
	// .toString());
	//
	// loadedFromFile = true;
	// if (sid.getSelectItems().get(1) != null
	// && !sid.getSelectItems().get(1).isEmpty()) {
	// lastLoaded.put(key, newLoad);
	// codeLists.put(key, sid);
	// } else {
	// LOG.fatal("Empty DDLIST " + listName
	// + " / Language " + userLanguage + " / ");
	//
	// MiddlewareException exc = new MiddlewareException(
	// "", FacesMessage.SEVERITY_ERROR.toString(),
	// "Empty DDLIST " + listName);
	// ((ApplUserBean) UserBean.getUserBean())
	// .addException(exc);
	//
	// codeList.add(new SelectItem("0", "Empty DDLLIST "
	// + listName));
	// codeList.add(new SelectItem("1", "Empty DDLLIST "
	// + listName));
	// return codeList;
	// }
	// return getDdlCopy(key, filtered);
	// } catch (Exception e) {
	// e.printStackTrace();
	//
	// LOG.fatal("Exception creating DDLIST " + listName
	// + " / Language " + userLanguage + " / ", e);
	//
	// MiddlewareException exc = new MiddlewareException("",
	// FacesMessage.SEVERITY_ERROR.toString(),
	// e.getMessage());
	// ((ApplUserBean) UserBean.getUserBean())
	// .addException(exc);
	//
	// codeList.add(new SelectItem("0",
	// "Exception creating DDLIST " + listName));
	// codeList.add(new SelectItem("1", e.getMessage()));
	// return codeList;
	// } finally {
	// try {
	// fr.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// } catch (Exception e) {
	//
	// }
	// }
	//
	// if (!loadedFromFile) {
	// State ddRequestState = new State("", "GETDDLIST", userLanguage,
	// listName, "", "1");
	// try {
	// SelectItemData sid = DropDownList
	// .getDropDownLists(ddRequestState);
	//
	// if (sid.getSelectItems().get(1) != null
	// && !sid.getSelectItems().get(1).isEmpty()) {
	// lastLoaded.put(key, newLoad);
	// codeLists.put(key, sid);
	// } else {
	// LOG.fatal("Empty DDLIST " + listName + " / Language "
	// + userLanguage + " / ");
	//
	// MiddlewareException exc = new MiddlewareException("",
	// FacesMessage.SEVERITY_ERROR.toString(),
	// "Empty DDLIST " + listName);
	// ((ApplUserBean) UserBean.getUserBean()).addException(exc);
	//
	// codeList.add(new SelectItem("0", "Empty DDLLIST "
	// + listName));
	// codeList.add(new SelectItem("1", "Empty DDLLIST "
	// + listName));
	// return codeList;
	// }
	// return getDdlCopy(key, filtered);
	// } catch (Exception e) {
	// LOG.fatal("Exception creating DDLIST " + listName
	// + " / Language " + userLanguage + " / ", e);
	//
	// MiddlewareException exc = new MiddlewareException("",
	// FacesMessage.SEVERITY_ERROR.toString(), e.getMessage());
	// ((ApplUserBean) UserBean.getUserBean()).addException(exc);
	//
	// codeList.add(new SelectItem("0", "Exception creating DDLIST "
	// + listName));
	// codeList.add(new SelectItem("1", e.getMessage()));
	// return codeList;
	// }
	// }
	// return null;
	// }

	// private List<SelectItem> getDdlCopy(String key, boolean filtered) {
	// List<SelectItem> ddl = new ArrayList<SelectItem>();
	// String type = codeLists.get(key).getView();
	//
	// List<SelectItem> inputDDL = codeLists.get(key).getSelectItems()
	// .get(filtered ? 0 : 1);
	// for (Iterator<SelectItem> iter = inputDDL.iterator(); iter.hasNext();) {
	// SelectItem si = (SelectItem) iter.next();
	// if ("code".equals(type)) {
	// ddl.add(new SelectItem(si.getValue(), si.getValue().toString()));
	// } else if ("value".equals(type)) {
	// ddl.add(new SelectItem(si.getValue(), si.getLabel()));
	// } else {
	// ddl.add(new SelectItem(si.getValue(), si.getValue() + "- "
	// + si.getLabel()));
	// }
	// }
	//
	// return ddl;
	// }

	// public static List<SelectItem> getEmptyDDList() {
	// List<SelectItem> emptyList = new ArrayList<SelectItem>();
	// emptyList.add(new SelectItem("", ""));
	// return emptyList;
	// }

	/*
	 * Lists
	 */
	// public List<SelectItem> getFprod() {
	// return getSelectItemsList("fprod", true);
	// }
	//
	// public List<SelectItem> getNewver() {
	// return getSelectItemsList("newver", true);
	// }
	//
	// public List<SelectItem> getLicsts() {
	// return getSelectItemsList("licsts", true);
	// }
	//
	// public List<SelectItem> getCstlnd() {
	// return getSelectItemsList("cstlnd", true);
	// }
	//
	// public List<SelectItem> getProduct() {
	// return getSelectItemsList("product", true);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.backingbean.IListBean#getSelectItemsList()
	 */
	// public List<SelectItem> getSelectItemsList() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.md.mdcms.backingbean.IListBean#getSelectItemsText(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public String getSelectItemsText(String action, String requestcode, String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
