package com.md.mdcms.backingbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.IFSFileInputStream;
import com.md.mdcms.base.ApplicationHelper;
import com.md.mdcms.base.PagingFunction;
import com.md.mdcms.base.SortFunction;
import com.md.mdcms.bean.IBean;
import com.md.mdcms.model.Container;
import com.md.mdcms.model.Grid;
import com.md.mdcms.model.NumberField;
import com.md.mdcms.model.Operation;
import com.md.mdcms.model.OutcomeObject;
import com.md.mdcms.model.PageField;
import com.md.mdcms.model.State;
import com.md.mdcms.nav.INavigation;
import com.md.mdcms.util.MdUtil;

/**
 * @author René Unternährer
 * @author Javaero-Technology GmbH
 * 
 */
public abstract class BaseBackingBean implements IBackingBean {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(BaseBackingBean.class);

	private static final long serialVersionUID = 1L;

	public static List<String> rcToSaveScrollPositions = Arrays.asList("BROWSE", "SEARCH", "REFRESH", "REPORT", "RESET",
			"POSITION");

	private String jobNumber;
	protected ApplUserBean userBean;

	protected State requestState;
	protected State responseState;

	private Vector<String> errorIds;

	protected Operation responseOperation;

	// paginator
	private int pagenr;
	// private List<PaginatorItem> pages;
	protected int maxpages;
	private String selectedGridId;

	// sort
	protected final AscendingComparator propertyAscendingComparator;
	protected final DescendingComparator propertyDescendingComparator;
	protected boolean ascending;
	private String sortColumnName;

	protected String gridId = "";
	protected String caption = "";
	protected String screenTitle = "";
	protected String screenFocus = "";
	protected String rowFocus = "";
	protected String function = "";
	protected String browseField = "";
	protected boolean hasBrowseButton;

	// list
	protected int selectionInt;

	// protected boolean hasFilter;

	protected List<?> actualList;

	private String functionClassName;

	protected Hashtable<String, IBackingBean> grids;
	private boolean multiGrid = false;

	private boolean hasLinklist = false;

	// buttons
	protected Vector<String> linklist;
	private String btnName;

	private String clickedButton;

	private boolean beanrendered;

	private PaginatorBackingBean paginatorBean;
	private ButtonBackingBean buttonBean;

	// xml
	protected String requestXmlFileName;
	protected String responseXmlFileName;

	// scrolling
	private String scrollToXY;
	private int scrollToX;
	private int scrollToY;

	// time measuring
	protected long startTime;
	protected long endTime;
	protected long splitTime;

	protected BaseBackingBean() {
		super();
		this.propertyAscendingComparator = new AscendingComparator();
		this.propertyDescendingComparator = new DescendingComparator();
		init();

		getResponseRoutine();
	}

	public BaseBackingBean(boolean create) {
		super();
		this.propertyAscendingComparator = new AscendingComparator();
		this.propertyDescendingComparator = new DescendingComparator();
		init();
	}

	public BaseBackingBean(HttpServletRequest request, String jobNumber) {
		this.propertyAscendingComparator = new AscendingComparator();
		this.propertyDescendingComparator = new DescendingComparator();

		this.jobNumber = jobNumber;

		HttpSession session = request.getSession(false);
		userBean = (ApplUserBean) session.getAttribute(ApplUserBean.BEAN_NAME);
		init();

		getResponseRoutine();
	}

	private void init() {
		this.actualList = new ArrayList<IBean>();

		// get the jobNumber from the request
		// HttpServletRequest request = (HttpServletRequest)
		// FacesContext.getCurrentInstance().getExternalContext()
		// .getRequest();
		// this.jobNumber = (String) request.getAttribute("jobNumber");
	}

	private void getResponseRoutine() {
		boolean doAgain = true;
		int cicles = 1;
		while (doAgain) {
			this.responseOperation = userBean.getResponseOperation(this.getClass().getSimpleName(), jobNumber, true);
			if (this.responseOperation != null || cicles > 2) {
				if (this.responseOperation != null) {
					LOG.debug("responseOperation found for: " + this.getClass().getSimpleName() + "/jobNumber: "
							+ this.responseOperation.getJobNumber());
				}
				doAgain = false;
			} else {
				LOG.debug("no responseOperation found for: " + this.getClass().getSimpleName() + " " + cicles);
				cicles++;
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		if (this.responseOperation != null) {
			this.responseState = this.responseOperation.getState();
			this.jobNumber = this.responseOperation.getJobNumber();
		}

	}

	public boolean create() {
		synchronized (BaseBackingBean.class) {
			startTime = System.currentTimeMillis();

			if (this.responseOperation != null) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Construct: " + this.getClass().getName() + " - jobNumber: " + this.jobNumber);
				}

				long timeAlreadyUsed = this.responseOperation.getTotalTime();

				/*
				 * 1 - needs to be before the setScreenResponseValues and
				 * setFunctionResponseCodes because of the browserendered stuff
				 */
				Container reqCont = this.responseOperation.getContainer(REQUESTCODELIST);
				if (reqCont != null) {
					// setButtonBean(reqCont.fillRequestCodeList(this));
				}

				Container cont = this.responseOperation.getContainer(CONTAINERTYPESCREEN);
				if (cont != null) {
					setScreenTitle(cont.getCaption().getValue());

					// set the page title
					setScreenTitle(cont.getCaption().getValue());

					/*
					 * 2 - needs to be after the setRequestResponseCodes because
					 * of the browserendered stuff
					 */
					// cont.fillScreenFields(this);

					// in case one grid is grid of MasterBackingBean
					if (cont.getGrids() != null && !cont.getGrids().isEmpty()) {
						Grid grid = cont.getGrids().get(0);

						if (isMultiGrid()) {
							Enumeration<String> gridKeys = getGrids().keys();

							while (gridKeys.hasMoreElements()) {
								String gridKey = (String) gridKeys.nextElement();
								IBackingBean gridBean = getGrids().get(gridKey);

								if (gridBean.getClass().getSimpleName().toLowerCase()
										.startsWith(getResponseState().getScreen().toLowerCase())) {
									grid = cont.getGrid(gridKey);
									break;
								}
							}
						}

						IBean bean = (IBean) MdUtil.createBackingBeanNew(this);

						if (bean != null) {
							setGridId(grid.getId());
							setCaption(cont.getCaption().getValue());
							grid.fillHeaderItems(this);
							grid.fillRowResponseValues(this, this, bean, grid);
							setPaginatorBean(setPaginator(grid, this));

							/*
							 * 2 - needs to be after the setRequestResponseCodes
							 * because of the browserendered stuff
							 */
							/*
							 * THIS THIS THIS?????
							 */
							// grid.fillFunctionResponseCodes(backingBean);
						}

						/* needs this position */
						grid.fillLinklist(this);

						if (isMultiGrid()) {
							generateGrids(this);
						}
					}

					whileGenerateBeanInit();

					setScrollToXY(userBean.getScrollPositionSaveFor(this.getClass().getSimpleName(), getJobNumber()));
				} else {
					setGridId("Grid");
					setCaption("Caption");
					setScreenTitle("Screen title");
				}

				this.responseOperation.setTotalTime(timeAlreadyUsed + (System.currentTimeMillis() - startTime));

				if (LOG.isInfoEnabled()) {
					LOG.info("End create: " + this.getClass().getName() + " create End");
				}

				// remove the responseOperation from
				// ApplicationHelper.getUserBean().getResponseOperations()
				// .remove(this.jobNumber);

				return true;
			}
			return false;
		}
	}

	protected void generateGrids(IBackingBean backingBean) {
		IBean gbb = null;
		String gridId;
		// String gridBeanName = BLANK;

		Container cont = this.responseOperation.getContainer(CONTAINERTYPESCREEN);

		List<Grid> grids = cont.getGrids();

		for (int i = 0; i < grids.size(); i++) {
			gridId = grids.get(i).getId();
			IBackingBean gmbb = backingBean.getGrids().get(gridId);

			if (gmbb != null) {
				if (!gmbb.getClass().getName().equals(backingBean.getClass().getName())) {
					gmbb.setRequestState(getRequestState());
					gmbb.setResponseState(getResponseState());
					gmbb.setJobNumber(getJobNumber());

					gbb = (IBean) MdUtil.createBackingBeanNew(gmbb);
					gmbb.setGridId(gridId);
					generateBeans(backingBean, gmbb, gbb, grids.get(i));
				}
			} else {
				if (LOG.isInfoEnabled()) {
					LOG.info("GridId: " + gridId + " is not defined in " + backingBean.getClass().getName());
				}
			}
		}
	}

	public String showRequestXml() {
		String filePath = StartConfigurationBean.getInstance().getXmlfilepath()
				+ ApplicationHelper.getUserBean().getUserId() + File.separatorChar + "req" + File.separatorChar
				+ this.responseOperation.getFileName(0);
		openXml(filePath);
		return null;
	}

	public String showResponseXml() {
		String filePath = StartConfigurationBean.getInstance().getXmlfilepath()
				+ ApplicationHelper.getUserBean().getUserId() + File.separatorChar + "res" + File.separatorChar
				+ this.responseOperation.getFileName(1);
		openXml(filePath);
		return null;
	}

	protected void generateBeans(IBackingBean masterBackingBean, IBackingBean gridManagerBackingBean,
			IBean gridBackingBean, Grid grid) {
		grid.fillHeaderItems(gridManagerBackingBean);
		grid.fillRowResponseValues(masterBackingBean, gridManagerBackingBean, gridBackingBean, grid);
		gridManagerBackingBean.setPaginatorBean(setPaginator(grid, gridManagerBackingBean));
	}

	public String getBackingBeanName() {
		return getResponseState().getScreenThread() + BACKINGBEAN;
	}

	/*
	 * paginator
	 */
	protected PaginatorBackingBean setPaginator(Grid grid, IBackingBean backingBean) {
		int maxPages = Integer.parseInt(grid.getMaxpages());
		setMaxpages(maxPages);
		int pageNr = Integer.parseInt(grid.getPagenr());
		String gridId = grid.getId();

		PaginatorBackingBean pbb = new PaginatorBackingBean();
		pbb = new PaginatorBackingBean();
		pbb.setPagenr(pageNr);
		pbb.setMaxpages(maxPages);
		// pbb.setGridId(gridId);

		return pbb;
	}

	public String paginatorLinkPressed() {
		synchronized (this.getClass()) {
			saveScrollPosition();

			String userSelectedPage = getPaginatorBean().getUserSelectedPage();
			String gridId = this.selectedGridId;
			if (userSelectedPage == null || userSelectedPage.equals("")) {
				IBackingBean selectedBackingBean = getGrids().get(gridId);
				userSelectedPage = selectedBackingBean.getPaginatorBean().getUserSelectedPage();
			}

			if ("AA".equals(userSelectedPage)) {
				pagenr = 1;
			} else if ("A".equals(userSelectedPage)) {
				pagenr--;
			} else if ("Z".equals(userSelectedPage)) {
				pagenr++;
			} else if ("ZZ".equals(userSelectedPage)) {
				pagenr = maxpages;
			} else {
				pagenr = Integer.parseInt(userSelectedPage);
			}

			State requestState = new State();
			Operation operation = ApplicationHelper.createPagingOperation(requestState, gridId, String.valueOf(pagenr),
					this.responseState.getScreen(), this.responseState.getThreadString());
			operation.setJobNumber(getJobNumber());

			setRequestState(requestState);
			handlePaging(operation);
		}
		return null;
	}

	public Object get(String fieldName) {
		java.lang.reflect.Field f;
		try {
			f = this.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(this);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (NullPointerException e) {

		}
		return null;
	}

	// all selection stuff
	public int getSelectionInt() {
		return selectionInt;
	}

	public void setSelectionInt(int selectionInt) {
		this.selectionInt = selectionInt;
	}

	public void setList(List<?> list) {
		actualList = list;
	}

	public List<?> getList() {
		return actualList;
	}

	// all stuff for the function list
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	/*
	 * Report
	 */
	protected void openReport(String xlsPath) {
		// FacesContext context = FacesContext.getCurrentInstance();
		// HttpServletResponse response = (HttpServletResponse)
		// context.getExternalContext().getResponse();

		String pathSep = "/";
		int lastIndex = xlsPath.lastIndexOf(pathSep);

		String fileName = xlsPath;
		if (lastIndex > 0) {
			fileName = xlsPath.substring(lastIndex + 1, xlsPath.length());
		}

		// response.setContentType("application/vnd.ms-excel");
		String fileExtension = FilenameUtils.getExtension(fileName);
		// response.setContentType(MimePropertiesBean.getInstance().getMimeType(fileExtension));

		// response.setHeader("Content-disposition", "attachment; filename=\"" +
		// fileName + "\"");

		try {
			// ServletOutputStream sos = response.getOutputStream();
			IFSFile ifsfile = new IFSFile(ApplicationHelper.getUserBean().getISeriesSession(this.jobNumber).getAs400(),
					xlsPath);
			IFSFileInputStream ifsfis = new IFSFileInputStream(ifsfile);
			byte[] bytes = new byte[1024];
			int bytesRead = ifsfis.read(bytes);
			while (bytesRead > 0) {
				// sos.write(bytes);
				bytesRead = ifsfis.read(bytes);
			}
			ifsfis.close();
			// sos.flush();
			// sos.close();
			// context.responseComplete();
		} catch (IOException e) {
			LOG.error("Exception " + e.getLocalizedMessage() + " -> see error log!");
		} catch (AS400SecurityException e) {
			LOG.error("Exception " + e.getLocalizedMessage() + " -> see error log!");
		}
	}

	/*
	 * Xml
	 */
	protected void openXml(String path) {
		// FacesContext context = FacesContext.getCurrentInstance();
		// HttpServletResponse response = (HttpServletResponse)
		// context.getExternalContext().getResponse();

		// response.setContentType("application/xml");

		String fileName = FilenameUtils.getName(path);

		String fileExtension = FilenameUtils.getExtension(fileName);

		// response.setContentType(MimePropertiesBean.getInstance().getMimeType(
		// fileExtension));

		// response.setHeader("Content-disposition", "attachment; filename=\"" +
		// fileName + "\"");

		try {
			// ServletOutputStream sos = response.getOutputStream();

			// Map<String, String> environment = System.getenv();
			// Set<String> keys = environment.keySet();
			// for (String key : keys) {
			// System.out.println("Key/value: " + key + " / "
			// + environment.get(key));
			// }

			// IFSFile ifsfile = new IFSFile(ApplicationHelper.getUserBean()
			// .getISeriesSession(this.jobNumber).getAs400(), path);
			// IFSFileInputStream ifsfis = new IFSFileInputStream(ifsfile);
			// byte[] bytes = new byte[1024];
			// int bytesRead = ifsfis.read(bytes);
			// while (bytesRead > 0) {
			// sos.write(bytes);
			// bytesRead = ifsfis.read(bytes);
			// }
			// ifsfis.close();
			// sos.flush();
			// sos.close();

			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);

			byte[] buffer = new byte[1024];
			int bytesRead = 0;

			while ((bytesRead = fis.read(buffer)) != -1) {
				// response.getOutputStream().write(buffer, 0, bytesRead);
			}

			fis.close();
			// response.getOutputStream().flush();
			// response.getOutputStream().close();
			//
			// context.responseComplete();
		} catch (IOException e) {
			LOG.error("Exception " + e.getLocalizedMessage() + " -> see error log!");
		}
	}

	/*
	 * sort
	 */
	/**
	 * @return the sortColumnName
	 */
	public String getSortColumnName() {
		return sortColumnName;
	}

	/**
	 * @param sortColumnName
	 *            the sortColumnName to set
	 */
	public void setSortColumnName(String sortColumnName) {
		this.sortColumnName = sortColumnName.toLowerCase();
	}

	@SuppressWarnings("unchecked")
	public void sort(Comparator comparator) {
		if (comparator instanceof AscendingComparator) {
			ascending = false;
		} else {
			ascending = true;
		}
		Collections.sort(actualList, comparator);
	}

	public void sort() {
		synchronized (this.getClass()) {
			// String scrolling =
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
			// .get("md:scrollToXY");

			String scrolling = null;

			if (scrolling != null) {
				// System.out.println("Scrolling from request: " + scrolling);
				setScrollToXY(scrolling);
				saveScrollPosition();
			}

			if (isAscending())
				sort(propertyAscendingComparator);
			else {
				sort(propertyDescendingComparator);
			}
			if (maxpages > 1) {
				doSort(sortColumnName, isAscending());
			} else {
				selectionInt = -1;
				resortList();
			}
		}
	}

	private void resortList() {
		int r = 0;
		List<IBean> beans = (List<IBean>) getList();
		for (Iterator<IBean> iterator = beans.iterator(); iterator.hasNext();) {
			IBean iBean = (IBean) iterator.next();
			iBean.setSelectionInt(r);
			r++;
		}
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	class AscendingComparator implements Comparator<Object>, Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		public int compare(Object object1, Object object2) {
			try {

				String VALUE = "value";
				java.lang.reflect.Field field1 = null;
				PageField pageField1 = null;
				String pageValue1 = null;
				java.lang.reflect.Field field2 = null;
				PageField pageField2 = null;
				String pageValue2 = null;

				try {
					field1 = object1.getClass().getDeclaredField(getSortColumnName());
					field1.setAccessible(true);
					pageField1 = (PageField) field1.get(object1);
					pageValue1 = BeanUtils.getProperty(pageField1, VALUE);
				} catch (Exception e) {
					// System.out.println(e);
				}

				try {
					field2 = object2.getClass().getDeclaredField(getSortColumnName());
					field2.setAccessible(true);
					pageField2 = (PageField) field2.get(object2);
					pageValue2 = BeanUtils.getProperty(pageField2, VALUE);
				} catch (Exception e) {
					// System.out.println(e);
				}

				try {
					if (pageField1 instanceof NumberField) {
						return Integer.valueOf(pageValue1) >= Integer.valueOf(pageValue2) ? -1 : 1;
					} else {
						return pageValue1.toLowerCase().compareTo(pageValue2.toLowerCase());
					}
				} catch (SecurityException e) {
					return 0;
				}
			} catch (Exception e) {
				// System.out.println(e);
			}
			return 0; // TODO
		}
	};

	class DescendingComparator implements Comparator<Object>, Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		public int compare(Object object1, Object object2) {
			try {

				String VALUE = "value";
				java.lang.reflect.Field field1 = null;
				PageField pageField1 = null;
				String pageValue1 = null;
				java.lang.reflect.Field field2 = null;
				PageField pageField2 = null;
				String pageValue2 = null;

				try {
					field1 = object1.getClass().getDeclaredField(getSortColumnName());
					field1.setAccessible(true);
					pageField1 = (PageField) field1.get(object1);
					pageValue1 = BeanUtils.getProperty(pageField1, VALUE);
				} catch (Exception e) {
					// System.out.println(e);
				}

				try {
					field2 = object2.getClass().getDeclaredField(getSortColumnName());
					field2.setAccessible(true);
					pageField2 = (PageField) field2.get(object2);
					pageValue2 = BeanUtils.getProperty(pageField2, VALUE);
				} catch (Exception e) {
					// System.out.println(e);
				}

				try {
					if (pageField1 instanceof NumberField) {
						return Integer.valueOf(pageValue2) >= Integer.valueOf(pageValue1) ? -1 : 1;
					} else {
						return pageValue2.toLowerCase().compareTo(pageValue1.toLowerCase());
					}
				} catch (SecurityException e) {
					return 0;
				}
			} catch (Exception e) {
				// System.out.println(e);
			}
			return 0; // TODO
		}
	};

	public void doSort(String fieldName, boolean ascending) {
		State requestState = new State();

		Operation operation = ApplicationHelper.createSortOperation(requestState, getGridId(), fieldName.toUpperCase(),
				ascending ? "A" : "D", this.responseState.getScreen(), this.responseState.getThreadString());

		operation.setJobNumber(getJobNumber());

		setRequestState(requestState);
		handleSort(operation);
	}

	// variable getters and setters
	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getScreenTitle() {
		return screenTitle;
	}

	public void setScreenTitle(String screenTitle) {
		this.screenTitle = screenTitle;
	}

	public String getRowFocus() {
		return rowFocus;
	}

	public void setRowFocus(String rowFocus) {
		this.rowFocus = rowFocus;
	}

	public String getScreenFocus() {
		return screenFocus;
	}

	public void setScreenFocus(String screenFocus) {
		this.screenFocus = screenFocus;
	}

	public String getFunctionClassName() {
		return functionClassName;
	}

	public int getMaxpages() {
		return maxpages;
	}

	public void setMaxpages(int maxpages) {
		this.maxpages = maxpages;
	}

	public String getBrowseField() {
		return browseField;
	}

	public void setBrowseField(String browseField) {
		this.browseField = browseField;
	}

	/**
	 * @return the hasBrowseButton
	 */
	public boolean hasBrowseButton() {
		return hasBrowseButton;
	}

	/**
	 * @param hasBrowseButton
	 *            the hasBrowseButton to set
	 */
	public void setHasBrowseButton(boolean hasBrowseButton) {
		this.hasBrowseButton = hasBrowseButton;
	}

	public boolean getBeanrendered() {
		return beanrendered;
	}

	public void setBeanrendered(boolean beanrendered) {
		this.beanrendered = beanrendered;
	}

	public boolean getBeanNotrendered() {
		return !beanrendered;
	}

	public String getEditFunction() {
		return EDIT;
	}

	public String getEditRowFunction() {
		return EDITROWFUNCTION;
	}

	public String getCopyRowFunction() {
		return COPYROWFUNCTION;
	}

	public boolean isMultiGrid() {
		return multiGrid;
	}

	public void setMultiGrid(boolean multiGrid) {
		this.multiGrid = multiGrid;
	}

	public Hashtable<String, IBackingBean> getGrids() {
		return grids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.backingbean.IBackingBean#getButtonsnottorender()
	 */
	public List<String> getButtonsnottorender() {
		return new ArrayList<String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.backingbean.IBackingBean#getButtonstorender()
	 */
	public List<String> getButtonstorender() {
		return new ArrayList<String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.backingbean.IBackingBean#getLinksasbuttons()
	 */
	public Vector<String> getLinksasbuttons() {
		return new Vector<String>();
	}

	public String browse() {
		setNewRequestCode(BROWSE);
		setScreenFocus(getBrowseField());
		return handleFunction();
	}

	// main methods
	// from ButtonBackingBean
	public String btnbtnPress() {
		startTime = System.currentTimeMillis();
		return linkBtnPress();
	}

	// button methods
	public String linkBtnPress() {
		try {
			// Vector<INavigation> linklistNav =
			// getButtonBean().getNavigation().getLinklist();

			// if (linklistNav != null && !linklistNav.isEmpty() &&
			// clickedButton != null && !"".equals(clickedButton)) {
			// boolean btnNameInLinklist = false;
			// for (Iterator<INavigation> iterator = linklistNav.iterator();
			// iterator.hasNext();) {
			// INavigation iNavigation = (INavigation) iterator.next();
			// if (iNavigation.getRequestCode().equals(clickedButton)) {
			// setNewRequestCode(SELECT);
			// this.setFunction(clickedButton.toUpperCase());
			// btnNameInLinklist = true;
			// break;
			// }
			// }
			// if (!btnNameInLinklist) {
			// setNewRequestCode(clickedButton.toUpperCase());
			// }
			// } else {
			// if (clickedButton == null || "".equals(clickedButton)) {
			// this.clickedButton =
			// getButtonBean().getNavigation().getRequestcode().get(0).getRequestCode();
			// }
			// setNewRequestCode(clickedButton.toUpperCase());
			// }

			// if (backingBean != null) {
			if (function != null && !"".equals(function)) {
				setFunction(function);
			}

			String outcome = handleFunction(clickedButton.toUpperCase());

			// is handled in the separate bean => control cases
			if (outcome == "DONE") {
				outcome = null;
			} else if (outcome == null) {
				outcome = handleFunction();
			}

			if (this.getClass().getSimpleName().startsWith(outcome)) {
				endTime = System.currentTimeMillis();
				return null;
			}

			return outcome;
			// }
		} catch (Exception e) {
			LOG.fatal(e);
		}
		return null;
	}

	/*
	 * a option from the linklist was executed
	 */
	public String linklistBtnPress() {
		try {
			if (clickedButton == null || "".equals(clickedButton)) {
				// this.clickedButton =
				// getButtonBean().getNavigation().getLinklist().get(0).getRequestCode();
			}
			setFunction(clickedButton.toUpperCase());
			setNewRequestCode(SELECT);

			// if (backingBean != null) {
			if (function != null && !"".equals(function)) {
				setFunction(function);
			}

			String outcome = handleFunction(clickedButton.toUpperCase());
			// is handled in the separate bean => control cases
			if (outcome == "DONE") {
				outcome = null;
			} else if (outcome == null) {
				outcome = handleFunction();
			}
			return outcome;
			// }
		} catch (Exception e) {
			LOG.fatal(e);
		}
		return null;
	}

	/*
	 * a button or requestCode was used to press
	 */
	// was here already
	public String btnPress() {
		String clickedButton = getBtnName();
		if (clickedButton == null || "".equals(clickedButton)) {
			clickedButton = SELECT;

			if (this.function == null || "".equals(this.function)) {
				this.setFunction(getLinklist().get(0));
				if (LOG.isInfoEnabled()) {
					LOG.info("is link with select and function: " + this.getFunction());
				}
			}
		} else {
			if (LOG.isInfoEnabled()) {
				LOG.info("is button: " + clickedButton);
			}
		}
		setNewRequestCode(clickedButton.toUpperCase());
		setBtnName(null);
		return handleFunction();
	}

	public String browserBack() {
		System.out.println("browser back");
		setBtnName("back");
		String outcome = btnPress();
		return null;
	}

	public String browserBack2() {
		String outcome = btnPress();

		// HttpServletResponse httpResponse = (HttpServletResponse)
		// FacesContext.getCurrentInstance().getExternalContext()
		// .getResponse();

		if (outcome != null) {
			// try {
			if (!outcome.contains(".jsf")) {
				// httpResponse.sendRedirect(outcome + ".jsf");
			} else {
				// httpResponse.sendRedirect(outcome);
			}
			// } catch (IOException e) {
			// if (LOG.isInfoEnabled()) {
			// LOG.fatal("sendRedirect error", e);
			// }
			// }
		}
		return outcome;
	}

	public String btnPress(String requestCode) {
		setNewRequestCode(requestCode.toUpperCase());
		return handleFunction();
	}

	public void handlePaging(Operation operation) {
		try {
			new PagingFunction(this, operation);
		} catch (XMLStreamException e) {
			LOG.error("XMLStreamException " + e.getLocalizedMessage() + " -> see error log!", e);
		} catch (IOException e) {
			LOG.error("IOException " + e.getLocalizedMessage() + " -> see error log!", e);
		} catch (Exception e) {
			LOG.error("Exception " + e.getLocalizedMessage() + " -> see error log!", e);
		}
	}

	public void handleSort(Operation operation) {
		try {
			new SortFunction(this, operation);
		} catch (XMLStreamException e) {
			LOG.error("XMLStreamException " + e.getLocalizedMessage() + " -> see error log!");
		} catch (IOException e) {
			LOG.error("IOException " + e.getLocalizedMessage() + " -> see error log!");
		} catch (Exception e) {
			LOG.error("Exception " + e.getLocalizedMessage() + " -> see error log!", e);
		}
	}

	public void moveResponseToRequestState() {
		setRequestState(this.responseState);
	}

	public String[] getNavSortSeq() {
		return null;
	}

	public String getTitleBeanName() {
		return GlobalBackingBeanManager.BEAN_NAME;
	}

	public Map<String, String> getSCROLLVALUES() {
		// TODO Auto-generated method stub
		return null;
	}

	public String handleFunction(String function) {
		// TODO Auto-generated method stub
		return null;
	}

	public String handleFunction() {
		startTime = System.currentTimeMillis();
		// String currentOutcome = getResponseState().getScreenThread();

		// String scrolling =
		// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
		// .get("md:scrollToXY");

		String scrolling = null;

		if (scrolling != null) {
			String requestCode = getRequestState().getRequestCode();
			if (rcToSaveScrollPositions.contains(requestCode)) {
				saveScrollPosition();
			}
		}

		// neu
		ApplUserBean userBean = ApplicationHelper.getUserBean();
		userBean.setRequestBackingBean(this);
		OutcomeObject outcomeObject = userBean.processFunction();

		if (outcomeObject != null) {
			String outcome = outcomeObject.getOutcome();
			String xlspth = outcomeObject.getXlspth();

			if (xlspth != null && !"".equals(xlspth)) {
				openReport(xlspth);
				return null;
			}

			// initialize
			setBtnName(null);

			if (this.getClass().getSimpleName().startsWith(outcome)) {
				this.responseOperation = userBean.getResponseOperation(this.getClass().getSimpleName(),
						this.getJobNumber(), true);
				this.responseState = this.responseOperation.getState();
				boolean ok = create();
				this.responseOperation.setTotalTime(System.currentTimeMillis() - startTime);
				return null;
			}

			return outcome;
		} else {
			LOG.error("outcomeObject was null, something went really wrong");
		}

		return null;
	}

	public String changepw() {
		try {
			ApplicationHelper.getUserBean().setCurrentJobNumber(getJobNumber());
			return "passwordChange";
		} catch (Exception e) {
		}
		return "passwordChange";
	}

	protected void saveScrollPosition() {
		// System.out.println("Save scroll position for: "
		// + this.getClass().getSimpleName() + "," + getJobNumber() + ","
		// + getScrollToXY());
		ApplicationHelper.getUserBean().saveScrollPositionFor(this.getClass().getSimpleName(), getJobNumber(),
				getScrollToXY());
	}

	public String logoff() {
		try {
			ApplicationHelper.getUserBean().logoff(this.jobNumber);
		} catch (Exception e) {
			LOG.fatal(e);
		}
		return "logoff";
	}

	public String close() {
		return logoff();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.backingbean.IBackingBean#getBEAN_NAME()
	 */
	public String getBEAN_NAME() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.backingbean.IBackingBean#getBrowse()
	 */
	public String getBrowse() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.backingbean.IBackingBean#updateLists()
	 */
	public void updateLists() {
		// TODO Auto-generated method stub

	}

	// public boolean hasFilter() {
	// return hasFilter;
	// }

	/**
	 * @return the hasLinklist
	 */
	public boolean getHasLinklist() {
		return hasLinklist;
	}

	/**
	 * @param hasLinklist
	 *            the hasLinklist to set
	 */
	public void setHasLinklist(boolean hasLinklist) {
		this.hasLinklist = hasLinklist;
	}

	/**
	 * @return the requestState
	 */
	public State getRequestState() {
		return this.requestState;
	}

	/**
	 * @param requestState
	 *            the requestState to set
	 */
	public void setRequestState(State requestState) {
		this.requestState = requestState;
	}

	/**
	 * @return the responseState
	 */
	public State getResponseState() {
		return this.responseState;
	}

	/**
	 * @param responseState
	 *            the responseState to set
	 */
	public void setResponseState(State responseState) {
		this.responseState = responseState;
	}

	/**
	 * @param responseOperation
	 *            the responseOperation to set
	 */
	public void setResponseOperation(Operation responseOperation) {
		this.responseOperation = responseOperation;
	}

	public void setNewRequestCode(String newRequestCode) {
		setRequestState(getResponseState());
		getRequestState().setRequestCode(newRequestCode);
	}

	/**
	 * @return
	 */
	public String getOutcome() {
		return this.responseState.getScreenThread();
	}

	public Vector<String> getErrorIds() {
		return errorIds;
	}

	public void setErrorIds(Vector<String> errorIds) {
		this.errorIds = errorIds;
	}

	public String getBtnName() {
		return this.btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
		this.clickedButton = btnName;
	}

	/**
	 * @return the linklist
	 */
	public Vector<String> getLinklist() {
		if (this.linklist == null) {
			this.linklist = new Vector<String>();
		}
		return linklist;
	}

	/**
	 * @param linklist
	 *            the linklist to set
	 */
	public void setLinklist(Vector<String> linklist) {
		this.linklist = linklist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.backingbean.IBackingBean#postCreateInit()
	 * 
	 * @implement this method if necessary
	 */
	public void whileGenerateBeanInit() {

	}

	/**
	 * @return the paginatorBean
	 */
	public PaginatorBackingBean getPaginatorBean() {
		return paginatorBean;
	}

	/**
	 * @param paginatorBean
	 *            the paginatorBean to set
	 */
	public void setPaginatorBean(PaginatorBackingBean paginatorBean) {
		this.paginatorBean = paginatorBean;
	}

	/**
	 * @return the buttonBean
	 */
	public ButtonBackingBean getButtonBean() {
		return buttonBean;
	}

	/**
	 * @param buttonBean
	 *            the buttonBean to set
	 */
	public void setButtonBean(ButtonBackingBean buttonBean) {
		this.buttonBean = buttonBean;
	}

	/**
	 * @return the clickedButton
	 */
	public String getClickedButton() {
		return clickedButton;
	}

	/**
	 * @param clickedButton
	 *            the clickedButton to set
	 */
	public void setClickedButton(String clickedButton) {
		this.clickedButton = clickedButton;
	}

	/**
	 * @return the jobNumber
	 */
	public String getJobNumber() {
		return jobNumber;
	}

	/**
	 * @param jobNumber
	 *            the jobNumber to set
	 */
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	/**
	 * @return the selectedGridId
	 */
	public String getSelectedGridId() {
		return selectedGridId;
	}

	/**
	 * @param selectedGridId
	 *            the selectedGridId to set
	 */
	public void setSelectedGridId(String selectedGridId) {
		this.selectedGridId = selectedGridId;
	}

	/**
	 * @return the scrollToXY
	 */
	public String getScrollToXY() {
		// System.out.println("get scrollToXY: " + this.scrollToXY);
		return this.scrollToXY;
	}

	/**
	 * @param scrollToXY
	 *            the scrollToXY to set
	 */
	public void setScrollToXY(String scrollToXY) {
		try {
			String[] scrollPositions = scrollToXY.split(",");
			this.scrollToX = Integer.valueOf(scrollPositions[0]);
			this.scrollToY = Integer.valueOf(scrollPositions[1]);

			// System.out.println("set ScrollBean: ScrollToX: " + this.scrollToX
			// + " / ScrollToY: " + scrollToY);

			// LOG.info("ScrollToX: " + this.scrollToX + " / ScrollToY: "
			// + scrollToY);
			this.scrollToXY = scrollToXY;
		} catch (Exception e) {
			LOG.info("scrollToXY: " + scrollToXY, e);
		}
	}

	/**
	 * @return the scrollToX
	 */
	public int getScrollToX() {
		return scrollToX;
	}

	/**
	 * @param scrollToX
	 *            the scrollToX to set
	 */
	public void setScrollToX(int scrollToX) {
		this.scrollToX = scrollToX;
	}

	/**
	 * @return the scrollToY
	 */
	public int getScrollToY() {
		return scrollToY;
	}

	/**
	 * @param scrollToY
	 *            the scrollToY to set
	 */
	public void setScrollToY(int scrollToY) {
		this.scrollToY = scrollToY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.md.mdcms.backingbean.IBackingBean#whileGenerateRequestInit()
	 */
	// public void whileGenerateRequestInit() {
	//
	// }

}
