package com.md.mdcms.base;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.IBackingBean;
import com.md.mdcms.bean.IBean;
import com.md.mdcms.exception.MiddlewareException;
import com.md.mdcms.model.Container;
import com.md.mdcms.model.Field;
import com.md.mdcms.model.Grid;
import com.md.mdcms.model.IHtmlField;
import com.md.mdcms.model.Item;
import com.md.mdcms.model.Operation;
import com.md.mdcms.model.PageField;
import com.md.mdcms.model.Row;
import com.md.mdcms.model.StringField;
import com.md.mdcms.model.XmlResponseObject;
import com.md.mdcms.util.MdUtil;
import com.md.mdcms.util.UnicodeString;
import com.md.mdcms.xml.RequestXml;
import com.md.mdcms.xml.ResponseXml;

public class Function extends FunctionBase {

	/** Log instance for this class. */
	private static final Log LOG = LogFactory.getLog(Function.class);

	public Function() {
		super();
	}

	public Function(IBackingBean backingBean) throws XMLStreamException, IOException {
		super();
		this.reqBean = backingBean;
		getData(backingBean.getJobNumber());
	}

	/*
	 * this is the heart of the lifecycle from the RequestManagerBean to
	 * generate the ResponseManagerBean
	 */
	protected void getData(String jobNumber) throws XMLStreamException, IOException {

		long curTime;
		long startTime = 0;
		long lapTime = 0;
		long middlewareTime = 0;
		long totalTime = 0;

		curTime = System.currentTimeMillis();
		startTime = curTime;
		lapTime = curTime;

		String xmlRequestString = prepareRequestXml(this.reqBean);
		lapTime = System.currentTimeMillis();

		XmlResponseObject xmlResponseObject = ApplicationHelper.getUserBean().runMiddleware(jobNumber,
				reqBean.getRequestState(), xmlRequestString, false);
		String xmlResponseString = xmlResponseObject.getXmlResponseString();

		boolean transactionStatus = true;
		if (xmlResponseString.startsWith(EXCEPTION)) {
			addMiddlewareException(xmlResponseString);
		} else {
			curTime = System.currentTimeMillis();
			middlewareTime = curTime - lapTime;
			lapTime = curTime;

			if (prepareResponseXml(xmlResponseString)) {

				// if (backingBeanFunction != null) {
				// boolean ok = backingBeanFunction.create();
				// } else {
				// MiddlewareException exc = new MiddlewareException("",
				// FacesMessage.SEVERITY_ERROR.toString(), SCREEN_UC
				// + " " + screenName + " is not defined");
				// ApplicationHelper.getUserBean().addException(exc);
				//
				// getMiddlewareExceptions().add(exc);
				//
				// return;
				// }
				// JsfSupporter.setManagedBean(screenName + BACKINGBEAN,
				// backingBeanFunction);
			} else {
				// responseStateSave = responseState;
				transactionStatus = false;
				LOG.info("getData-1");
			}
		}

		curTime = System.currentTimeMillis();
		totalTime = curTime - startTime;
		if (LOG.isWarnEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("<transaction>");
			sb.append(NEWLINE);
			sb.append("  <status>");
			sb.append(NEWLINE);
			sb.append("    ");
			sb.append(transactionStatus ? "good" : "bad");
			sb.append("  </status>");
			sb.append("  <user>");
			sb.append(ApplicationHelper.getUserBean().getUserId());
			sb.append("  </user>");
			sb.append(NEWLINE);
			sb.append("  <time>");
			// sb.append(Functions.dateTime());
			sb.append("  </time>");
			sb.append(NEWLINE);
			// if (responseStateSave != null) {
			// sb.append(" <state>");
			// if (responseStateSave.getAction() != null) {
			// sb.append(" <action>");
			// sb.append(responseStateSave.getAction());
			// sb.append(" </action>");
			// sb.append(NEWLINE);
			// }
			// if (responseStateSave.getFunction() != null) {
			// sb.append(" <function>");
			// sb.append(responseStateSave.getFunction());
			// sb.append(" </function>");
			// sb.append(NEWLINE);
			// }
			// if (responseStateSave.getRequestCode() != null) {
			// sb.append(" <requestcode>");
			// sb.append(responseStateSave.getRequestCode());
			// sb.append(" </requestcode>");
			// sb.append(NEWLINE);
			// }
			// if (responseStateSave.getScreen() != null) {
			// sb.append(" <screen>");
			// sb.append(responseStateSave.getScreen());
			// sb.append(" </screen>");
			// sb.append(NEWLINE);
			// }
			// sb.append(" </state>");
			// }
			sb.append(NEWLINE);
			sb.append("  <timeused>");
			sb.append(NEWLINE);
			sb.append("    <timemiddleware>");
			sb.append(middlewareTime);
			sb.append("    </timemiddleware>");
			sb.append(NEWLINE);
			sb.append("  <timeused>");
			sb.append("    <timetotal>");
			sb.append(totalTime);
			sb.append("    </timetotal>");
			sb.append(NEWLINE);
			sb.append("</transaction>");
			sb.append(NEWLINE);
			LOG.warn(sb.toString());
		}

		// try {
		// responseStateSave.setTimeUsedMiddlewareTotal(middlewareTime);
		// responseStateSave.setTimeUsedTotal(totalTime);
		//
		// responseStateSave.setTimeUsedMiddlewareRequest(xmlResponseObject
		// .getMiddlewareRequestTime());
		// responseStateSave.setTimeUsedMiddleware(xmlResponseObject
		// .getMiddlewareTime());
		// responseStateSave.setTimeUsedMiddlewareResponse(xmlResponseObject
		// .getMiddlewareResponseTime());
		//
		// } catch (Exception e) {
		//
		// }

		// String userLogKey =
		// ApplicationHelper.getUserBean().getISeriesSession()
		// .getSessionKey();
		// ((JSSTATBackingBeanManager) JsfHelper
		// .findManagedBean(JSSTATBackingBeanManager.BEAN_NAME)).add(
		// userLogKey, responseStateSave);

		// StringBuffer sb = new StringBuffer();
		// sb.append(middlewareTime);
		// sb.append(SLASH);
		// sb.append(totalTime);
		// backingBeanFunction.setCreateTime(sb.toString());
	}

	/*
	 * Everything R E S P O N S E R E S P O N S E Everything R E S P O N S E
	 */

	protected boolean prepareResponseXml(String xmlResponseString) throws XMLStreamException, IOException {

		this.responseOperation = null;
		try {
			responseOperation = ResponseXml.generateOperation(xmlResponseString);
			if (responseOperation == null)
				return false;

		} catch (Exception e1) {
			MiddlewareException exc = new MiddlewareException("", "30", e1.getMessage());
			ApplicationHelper.getUserBean().addException(exc);

			getMiddlewareExceptions().add(exc);
			return false;
		}

		screenName = this.responseOperation.getScreenName();
		LOG.debug("Next screen: " + screenName);

		// State responseState = new State();
		// List<Field> vgf = getContainer(CONTAINERTYPEGLOBAL).getFields();
		// /* ResponseState */
		// for (Iterator<Field> iter = vgf.iterator(); iter.hasNext();) {
		// Field field = (Field) iter.next();
		// setValueInBean(responseState, field.getId().toLowerCase(),
		// field.getValue());
		// }
		// responseState.setDateTime(Functions.dateTime());

		// responseStateSave = responseState;

		if (!handleContainerMessageList()) {
			return false;
		}

		return true;
	}

	/*
	 * Preparation of the screen container for the requestXml String
	 */
	protected String prepareRequestXml(IBackingBean requestBean) throws XMLStreamException, IOException {

		Operation requestOperation = new Operation();

		requestOperation.add(requestBean.getRequestState().getAsGlobalContainer());

		Container screenContainer = new Container(CONTAINERTYPESCREEN);
		screenContainer.setCaption(reqBean.getCaption());

		// reqBean.whileGenerateRequestInit();

		String requestCode = this.reqBean.getRequestState().getRequestCode();
		if (requestCode != null && !requestCode.equals(BLANK)) {

			/*
			 * add the rows to the grid if the list exists
			 */
			screenContainer.setFields(extractSingleFields(reqBean));

			/*
			 * extract the grid row fields out of the manager bean's list
			 */

			/*
			 * FIELDS
			 */
			try {
				Grid currentGrid = new Grid();
				currentGrid.addRows(extractGridFieldRows(reqBean, reqBean.getGridId(), false));
				currentGrid.setId(reqBean.getGridId());
				screenContainer.add(currentGrid);
			}
			/*
			 * ATTRIBUTES
			 */
			catch (Exception e) {
				// currentGrid.addRows(extractGridRows(reqBean, reqBean
				// .getGridId(), false));
			}

			if (reqBean.isMultiGrid()) {
				screenContainer.add(getRequestGrids());
			}
			requestOperation.add(screenContainer);
		}
		return RequestXml.generateXmlRequestString(requestOperation);
	}

	protected List<Grid> getRequestGrids() {
		String className = reqBean.get("BEAN_NAME").toString().substring(0, 6);
		List<Grid> requestGrids = new ArrayList<Grid>();
		IBackingBean gridManReqBean = null;
		Grid currentGrid = null;

		Enumeration<String> gridIds = reqBean.getGrids().keys();

		while (gridIds.hasMoreElements()) {
			String gridId = (String) gridIds.nextElement();

			IBackingBean backingBeanManager = reqBean.getGrids().get(gridId);
			String beanName = backingBeanManager.getClass().getSimpleName();

			if (!className.equals(beanName)) {
				gridManReqBean = backingBeanManager;

				if (gridManReqBean != null) {
					currentGrid = new Grid(gridId);

					String gridNr = gridId.substring(gridId.length() - 2, gridId.length());

					/*
					 * extract the grid row fields out of the manager bean's
					 * list
					 */
					/*
					 * FIELDS
					 */
					try {
						currentGrid.addRows(extractGridFieldRows(gridManReqBean, gridNr, true));
					} catch (Exception e) {
					}
				}
				requestGrids.add(currentGrid);
			}
		}

		return requestGrids;
	}

	public List<Field> extractSingleFields(IBackingBean manBean) {

		String fieldName;

		List<Field> sf = new ArrayList<Field>();

		/*
		 * fill the screen container with all the singleFields from the
		 * managerBackingBean
		 */
		for (java.lang.reflect.Field field : reqBean.getClass().getDeclaredFields()) {
			fieldName = field.getName();
			Field singleField = null;

			/*
			 * FIELDS
			 */
			try {
				IHtmlField pageField = (IHtmlField) reqBean.get(fieldName);
				if (pageField != null || pageField instanceof PageField) {
					singleField = handlePageField(pageField, fieldName);
				} else {
				}
			} catch (Exception e) {
			}

			/*
			 * FIELDS AND ATTRIBUTES
			 */
			if (singleField != null) {
				sf.add(singleField);
			}
		}
		return sf;
	}

	private Field handlePageField(IHtmlField pageField, String fieldName) {

		String focus;
		boolean fieldAdded = false;
		String valueObject;
		DecimalFormat decimalFormat = new DecimalFormat(DECFORM);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		decimalFormat.setDecimalFormatSymbols(dfs);
		// FieldState fieldState;
		Field singleField = null;

		if (reqBean.getScreenFocus().equals(fieldName)) {
			pageField.setFocus(TRUE);
			focus = TRUE;
		} else {
			pageField.setFocus(null);
			focus = null;
		}

		/*
		 * only if field is editable attributes: - editable - visible of the
		 * singleFields
		 */
		// if (reqBean.getFieldStates() != null) {
		// fieldState = reqBean.getFieldStates().get(fieldName);
		// // field is editable or not visible
		// if (fieldState != null
		// && (fieldState.isEditable() || !fieldState.isVisible())) {

		if (pageField.isRequestImportant()) {
			/*
			 * value of the singleField
			 */
			// if (pageField instanceof AmountField) {
			// valueObject = pageField.getValue() != null ? decimalFormat
			// .format(pageField.getAmount()) : null;
			if (pageField instanceof StringField) {
				// valueObject =
				// UnicodeConverter.forXml(String.valueOf(pageField
				// .getValue()));
				valueObject = UnicodeString.convert(String.valueOf(pageField.getValue()));
			} else {
				valueObject = String.valueOf(pageField.getValue());
			}

			if (valueObject == null)
				valueObject = BLANK;

			Field xmlField = new Field();
			try {
				BeanUtils.copyProperties(xmlField, pageField);
				xmlField.setValue(valueObject);
				xmlField.setEditable(String.valueOf(pageField.getEditable()));
				xmlField.setVisible(pageField.getVisible());
				xmlField.setTooltip(null);
				singleField = xmlField;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fieldAdded = true;
			// }// if(fieldState != null &&
		} // if(reqBean.getFieldStates() != null){

		if (!fieldAdded && Boolean.valueOf(pageField.getUpdate())) {
			/*
			 * value of the singleField
			 */
			// if (pageField instanceof AmountField) {
			// valueObject = pageField.getValue() != null ? decimalFormat
			// .format(pageField.getValue()) : null;
			// } else {
			valueObject = (String) pageField.getValue();
			// }
			if (valueObject == null)
				valueObject = BLANK;
			Field xmlField = new Field();
			try {
				BeanUtils.copyProperties(xmlField, pageField);
				xmlField.setValue(valueObject);
				xmlField.setTooltip(null);
				singleField = xmlField;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fieldAdded = true;
		}

		if (!fieldAdded && focus != null) {
			/*
			 * value of the singleField
			 */
			// if (pageField instanceof AmountField) {
			// valueObject = pageField.getValue() != null ? decimalFormat
			// .format(pageField.getValue()) : null;
			// } else {
			valueObject = (String) pageField.getValue();
			// }
			if (valueObject == null)
				valueObject = BLANK;

			Field xmlField = new Field();
			try {
				BeanUtils.copyProperties(xmlField, pageField);
				xmlField.setValue(valueObject);
				xmlField.setTooltip(null);
				singleField = xmlField;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fieldAdded = true;
		} // else if(focus != null){
		fieldAdded = false;

		return singleField;
	}

	protected List<Row> extractGridFieldRows(IBackingBean manBean, String gridNr, boolean isGrid) {
		List<?> beans = ((IBackingBean) manBean).getList();
		List<Row> rows = new ArrayList<Row>();

		if (beans != null && !beans.isEmpty()) {
			List<Item> row = new ArrayList<Item>();
			boolean rowToWrite;
			String fieldName;
			Object value;
			DecimalFormat decimalFormat = new DecimalFormat(DECFORM);
			boolean isSelectedRow;
			String selectionIntString = "";
			String rownrString = "";
			String rowFocus = manBean.getRowFocus();

			// FUNCTION for sflopt field
			String function = reqBean.getFunction();

			if (function == null) {
				function = BLANK;
			}
			if (function == null || "".equals(function)) {
				// function = ((ButtonBackingBean) JsfSupporter
				// .findManagedBean(ButtonBackingBean.BEAN_NAME))
				// .getFunction();
			}

			// for (Iterator<?> iter = beans.iterator(); iter.hasNext();) {
			// IBean bean = (IBean) iter.next();
			for (Object beanObject : beans) {
				IBean bean = (IBean) beanObject;

				rowToWrite = false;

				java.lang.reflect.Field[] getFields = bean.getClass().getDeclaredFields();

				/*
				 * selectionInt of the field
				 */
				selectionIntString = getValueFromBean(bean, "selectionInt");
				rownrString = getValueFromBean(bean, ROWNR);

				int selectionInt = Integer.valueOf(selectionIntString);

				int selectedRow = manBean.getSelectionInt();

				if (selectionInt == selectedRow) {
					isSelectedRow = true;
					if (function != null && !"".equals(function)) {
						rowToWrite = true;
						row.add(new Item(null, null, SFLOPT, function, null));
					}
				} else
					isSelectedRow = false;

				/*
				 * iterate over the fields of the bean -> row
				 */
				for (java.lang.reflect.Field f : getFields) {
					fieldName = f.getName();

					try {

						if (isGrid) {
							/*
							 * formarly extra grid functions but can be included
							 * - edit - copy
							 */
							if (fieldName.startsWith("edit") && isSelectedRow
									&& manBean.getFunction().equals(EDITROWFUNCTION)) {
								row.add(new Item(null, TRUE, EDIT + gridNr, TRUE, null));
							}
							if (fieldName.startsWith("copy") && isSelectedRow
									&& manBean.getFunction().equals(COPYROWFUNCTION)) {
								row.add(new Item(null, TRUE, COPY + gridNr, TRUE, null));
							}
						}

						if (isSelectedRow) {

							/*
							 * formarly extra grid functions but can be included
							 * - edit - copy
							 */
							if (EDIT.equals(fieldName) && manBean.getRowFocus().equals(EDIT)) {
								row.add(new Item(FALSE, TRUE, EDIT, TRUE, null));
							} else if (COPY.equals(fieldName) && manBean.getRowFocus().equals(COPY)) {
								row.add(new Item(FALSE, TRUE, COPY, TRUE, null));
							}
						} // if (isSelectedRow) {

						/*
						 * only if eligible
						 */
						if (variableEligible(fieldName)) {
							IHtmlField pageField = (IHtmlField) bean.get(fieldName);

							// is editable and/or not visible
							if (pageField != null && pageField.isRequestImportant()) {

								rowToWrite = true;

								// if (pageField instanceof AmountField) {
								// value = pageField.getValue() != null ?
								// decimalFormat
								// .format(pageField.getValue())
								// : null;
								// } else {
								value = pageField.getValue();
								// }

								/*
								 * value of the row
								 */
								if (value == null)
									value = BLANK;

								row.add(new Item(pageField.getEditable(),
										isSelectedRow && reqBean.getRowFocus().equals(fieldName) ? TRUE : null,
										fieldName.toUpperCase(), value.toString(), pageField.getVisible()));
								// is selected row && hasFocus
							} else if (isSelectedRow && reqBean.getRowFocus().equals(fieldName)) {

								rowToWrite = true;

								// if (pageField instanceof AmountField) {
								// value = pageField.getValue() != null ?
								// decimalFormat
								// .format(pageField.getValue())
								// : null;
								// } else {
								value = pageField.getValue();
								// }

								/*
								 * value of the row
								 */
								if (value == null)
									value = BLANK;

								row.add(new Item(pageField.getEditable(), TRUE, fieldName.toUpperCase(),
										value.toString(), pageField.getVisible()));
							} // else if (isSelectedRow
						} // if (variableEligible(fieldName)) {
					} catch (Exception e) {
						LOG.error("During field property lookup - fieldName: " + fieldName, e);
					}
				} // for (java.lang.reflect.Field f : getFields) {

				if (rowToWrite) {

					ArrayList<Item> clonedItems = (ArrayList<Item>) ((ArrayList<Item>) row).clone();

					rows.add(new Row(rownrString, clonedItems));

					row.clear();
				}

			} // for fields
		} // for rows
		return rows;
	}

	private boolean variableEligible(String fieldName) {
		if (!variablesToAvoid.contains(fieldName) && doesntEndWith(fieldName) && doesntStartWith(fieldName)) {
			return true;
		}
		return false;
	}

	private boolean doesntEndWith(String fieldName) {
		for (int i = 0; i < variableToAvoidEndsWith.length; i++) {
			if (fieldName.endsWith(variableToAvoidEndsWith[i]))
				return false;
		}
		return true;
	}

	private boolean doesntStartWith(String fieldName) {
		for (int i = 0; i < variableToAvoidStartsWith.length; i++) {
			if (fieldName.startsWith(variableToAvoidStartsWith[i]))
				return false;
		}
		return true;
	}

	// /* ==> 3 not critical but to recode */
	protected boolean handleContainerMessageList() {
		Container cont = getContainer(CONTAINERMESSAGELIST);
		boolean notSevere = true;
		if (cont != null) {
			if (cont.getArrays().get(0).getArrayItems().size() > 0) {
				// FacesContext context = FacesContext.getCurrentInstance();
				List<Item> messages = cont.getArrays().get(0).getArrayItems();
				for (int i = 0; i < messages.size(); i++) {

					boolean errorAdded = false;
					String id = "";

					/* if more then one field is in error mode */
					if (messages.get(i).getFieldId() != null && messages.get(i).getFieldId().contains(",")) {
						String[] fieldIds = messages.get(i).getFieldId().split(",");
						for (int f = 0; f < fieldIds.length; f++) {

							errorAdded = false;
							id = fieldIds[f];

							if (id == null || "".equals(id)) {
								MiddlewareException me = new MiddlewareException("md:dummy",
										messages.get(i).getSeverity(), messages.get(i).getValue());

								ApplicationHelper.getUserBean().addException(me);

								getMiddlewareExceptions().add(me);

								// FacesMessage message = new FacesMessage(
								// me.getFacesSeverity(), me
								// .getMessage(), me.getMessage());
								// context.addMessage(me.getId(), message);

								errorAdded = true;
							} else {
								id = id.toLowerCase();
								// Map<String, String> requestParameterMap =
								// context
								// .getExternalContext()
								// .getRequestParameterMap();
								// Set<String> clientIds = requestParameterMap
								// .keySet();

								// for (Iterator<String> iter = clientIds
								// .iterator(); iter.hasNext();) {
								// String clientId = (String) iter.next();
								// if (clientId.toLowerCase().endsWith(id)) {
								//
								// MiddlewareException me = new
								// MiddlewareException(
								// clientId.toLowerCase(),
								// messages.get(i).getSeverity(),
								// messages.get(i).getValue());
								//
								// ApplicationHelper.getUserBean()
								// .addException(me);
								//
								// getMiddlewareExceptions().add(me);
								//
								// // FacesMessage message = new
								// // FacesMessage(
								// // me.getFacesSeverity(),
								// // me.getMessage(), me
								// // .getMessage());
								// // context.addMessage(me.getId(),
								// // message);
								//
								// errorAdded = true;
								// }
								// }

								if (!errorAdded) {
									MiddlewareException me = new MiddlewareException("md:" + id,
											messages.get(i).getSeverity(), messages.get(i).getValue());

									ApplicationHelper.getUserBean().addException(me);

									getMiddlewareExceptions().add(me);

									// FacesMessage message = new
									// FacesMessage(me
									// .getFacesSeverity(), me
									// .getMessage(), me.getMessage());
									// context.addMessage(me.getId(), message);

								}

							}
						}
					} else {

						errorAdded = false;
						id = messages.get(i).getFieldId();

						if (id == null || "".equals(id)) {
							MiddlewareException me = new MiddlewareException("md:dummy", messages.get(i).getSeverity(),
									messages.get(i).getValue());

							ApplicationHelper.getUserBean().addException(me);

							getMiddlewareExceptions().add(me);

							// FacesMessage message = new FacesMessage(me
							// .getFacesSeverity(), me.getMessage(), me
							// .getMessage());
							// context.addMessage(me.getId(), message);

						} else {
							id = id.toLowerCase();
							// Map<String, String> requestParameterMap =
							// context.getExternalContext()
							// .getRequestParameterMap();
							// Set<String> clientIds =
							// requestParameterMap.keySet();

							// for (Iterator<String> iter =
							// clientIds.iterator(); iter.hasNext();) {
							// String clientId = (String) iter.next();
							// if (clientId.endsWith(id)) {
							//
							// MiddlewareException me = new
							// MiddlewareException(clientId.toLowerCase(),
							// messages.get(i).getSeverity(),
							// messages.get(i).getValue());
							//
							// ApplicationHelper.getUserBean().addException(me);
							//
							// getMiddlewareExceptions().add(me);
							//
							// // FacesMessage message = new
							// // FacesMessage(me
							// // .getFacesSeverity(), me
							// // .getMessage(), me.getMessage());
							// // context.addMessage(me.getId(), message);
							//
							// errorAdded = true;
							// }
							// }

							if (!errorAdded) {
								MiddlewareException me = new MiddlewareException("md:" + id,
										messages.get(i).getSeverity(), messages.get(i).getValue());

								ApplicationHelper.getUserBean().addException(me);

								getMiddlewareExceptions().add(me);

								// FacesMessage message = new FacesMessage(me
								// .getFacesSeverity(), me.getMessage(),
								// me.getMessage());
								// context.addMessage(me.getId(), message);

							}
						}
					}

					// TEST ONLY
					// System.out.println("add Error FUSER");
					// MiddlewareException me = new MiddlewareException(
					// "FUSER", "30","md:FUSER ERROR");
					//
					// ApplicationHelper.getUserBean()
					// .addException(me);

					if (Integer.parseInt(messages.get(i).getSeverity()) >= 40)
						notSevere = false;

				}
			}
		}
		return notSevere;
	}

	protected void generateBean(IBackingBean backingBean, IBean bean) {

		/*
		 * 1 - needs to be before the setScreenResponseValues and
		 * setFunctionResponseCodes because of the browserendered stuff
		 */
		Container reqCont = getContainer(REQUESTCODELIST);
		if (reqCont != null) {
			// reqCont.fillRequestCodeList(backingBean);
		}

		Container cont = getContainer(CONTAINERTYPESCREEN);
		if (cont != null) {
			setValueInBean(backingBean, SCREENTITLE, cont.getCaption().getValue());

			// set the page title
			// setValueInBean(JsfSupporter.findManagedBean(reqBean.getTitleBeanName()),
			// SCREENTITLE,
			// cont.getCaption().getValue());

			/*
			 * 2 - needs to be after the setRequestResponseCodes because of the
			 * browserendered stuff
			 */
			// cont.fillScreenFields(backingBean);

			if (cont.getGrids() != null && !cont.getGrids().isEmpty()) {

				Grid grid = cont.getGrids().get(0);
				if (backingBean.isMultiGrid()) {
					String className = backingBean.get("BEAN_NAME").toString().substring(0, 6);

					Enumeration<String> gridKeys = backingBean.getGrids().keys();
					while (gridKeys.hasMoreElements()) {
						String gridKey = (String) gridKeys.nextElement();

						IBackingBean backingBeanManager = backingBean.getGrids().get(gridKey);
						String gridValue = backingBeanManager.getClass().getSimpleName();

						if (className.equals(gridValue)) {
							grid = cont.getGrid(gridKey);
							break;
						}
					}
				}

				if (bean != null) {
					setValueInBean(backingBean, GRIDID, grid.getId());
					setValueInBean(backingBean, CAPTION, cont.getCaption().getValue());
					grid.fillHeaderItems(backingBean);
					grid.fillRowResponseValues(backingBean, backingBean, bean, grid);
					// setPaginator(grid, backingBean);

					/*
					 * 2 - needs to be after the setRequestResponseCodes because
					 * of the browserendered stuff
					 */
					// grid.fillFunctionResponseCodes(backingBean);
				}

				/* needs this position */
				grid.fillLinklist(backingBean);

				if (backingBean.isMultiGrid()) {
					generateGrids(backingBean);
				}
			}

			backingBean.whileGenerateBeanInit();

		} else {
			setValueInBean(backingBean, GRIDID, "Here should the gridId appear");
			setValueInBean(backingBean, CAPTION, "Here should the caption appear");
			setValueInBean(backingBean, SCREENTITLE, "Here should the screenTitle appear");
		}
	}

	protected void generateGrids(IBackingBean backingBean) {
		IBackingBean gmbb = null;
		IBean gbb = null;
		String gridId;
		Container cont = getContainer(CONTAINERTYPESCREEN);
		List<Grid> grids = cont.getGrids();

		for (int i = 0; i < grids.size(); i++) {
			gridId = grids.get(i).getId();

			IBackingBean backingBeanManager = backingBean.getGrids().get(gridId);

			try {
				gmbb = (IBackingBean) MdUtil.createBackingBeanManagerNew(backingBeanManager);
				if (gmbb != null) {
					gbb = (IBean) MdUtil.createBackingBeanNew(backingBeanManager);
					gmbb.setGridId(gridId);
					generateBeans(backingBean, gmbb, gbb, grids.get(i));
				} else {
					// MiddlewareException exc = new MiddlewareException("",
					// FacesMessage.SEVERITY_ERROR.toString(),
					// "screen " + screenName + " is not defined");
					// ApplicationHelper.getUserBean().addException(exc);
					//
					// getMiddlewareExceptions().add(exc);
				}
			} catch (Exception e) {
				LOG.fatal("generateGrids -> Exception ", e);
			}
			// JsfSupporter.findManagedBean(gridBeanName + BACKINGBEAN);
			// JsfSupporter.setManagedBean(gridBeanName + BACKINGBEAN, gmbb);
		}
	}

	protected void generateBeans(IBackingBean masterBackingBean, IBackingBean gridManagerBackingBean,
			IBean gridBackingBean, Grid grid) {
		grid.fillHeaderItems(gridManagerBackingBean);
		// if (grid.getRows() != null && grid.getRows().getRows() != null
		// && !grid.getRows().getRows().isEmpty()) {
		grid.fillRowResponseValues(masterBackingBean, gridManagerBackingBean, gridBackingBean, grid);
		// }
		generatePaginator(grid, gridManagerBackingBean, masterBackingBean);
	}

	// private void reorgLinklist(String[] navSortSeq) {
	// Vector<INavigation> links = ApplicationHelper.getUserBean()
	// .getRequestcodeNavigation();
	// Vector<INavigation> sortedLinks = null;
	// if (links != null && !links.isEmpty()) {
	// sortedLinks = new Vector<INavigation>();
	//
	// for (int i = 0; i < navSortSeq.length; i++) {
	//
	// NavigationNode unsortedElement = null;
	//
	// for (Iterator<INavigation> sortIter = links.iterator(); sortIter
	// .hasNext();) {
	// NavigationNode element = (NavigationNode) sortIter.next();
	// unsortedElement = new NavigationNode(element);
	// if (navSortSeq[i].equals(SEPSINGLE)) {
	// sortedLinks.add(new NavigationNode("SEP", SEPSINGLE));
	// break;
	// }
	// if (navSortSeq[i].equals(element.getRequestCode())) {
	// sortedLinks.add(unsortedElement);
	// links.remove(element);
	// break;
	// }
	// }
	// }
	//
	// for (Iterator<INavigation> iter = links.iterator(); iter.hasNext();) {
	// NavigationNode element = (NavigationNode) iter.next();
	// sortedLinks.add(element);
	// }
	// }
	// ApplicationHelper.getUserBean().setRequestcodeNavigation(sortedLinks);
	// }

	// protected void setPaginator(Grid grid, IBackingBean backingBean) {
	// setValueInBean(backingBean, "maxpages", grid.getMaxpages());
	// int maxPages = Integer.parseInt(grid.getMaxpages());
	// int pageNr = Integer.parseInt(grid.getPagenr());

	// PaginatorBackingBean pbb = (PaginatorBackingBean) JsfSupporter
	// .findManagedBean(PaginatorBackingBean.BEAN_NAME);
	// pbb = new PaginatorBackingBean();
	// pbb.setPagenr(pageNr);
	// pbb.setMaxpages(maxPages);
	// pbb.setCurrentBackingBean(backingBean.getBackingBeanName());

	// JsfSupporter.setManagedBean(PaginatorBackingBean.BEAN_NAME, pbb);
	// }

	protected void generatePaginator(Grid grid, IBackingBean backingBean, IBackingBean masterBackingBean) {
		setValueInBean(backingBean, "maxpages", grid.getMaxpages());
		int pagenr = Integer.parseInt(grid.getPagenr());
		setValueInBean(backingBean, "pagenr", pagenr);
		// setValueInBean(backingBean, "currentBackingBean", masterBackingBean);
	}

}
