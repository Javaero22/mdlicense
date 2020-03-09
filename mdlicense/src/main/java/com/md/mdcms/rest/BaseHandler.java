package com.md.mdcms.rest;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.ApplUserBean;
import com.md.mdcms.backingbean.ApplicationConfigurationBean;
import com.md.mdcms.base.IConstants;
import com.md.mdcms.model.Container;
import com.md.mdcms.model.Grid;
import com.md.mdcms.model.Operation;
import com.md.mdcms.model.State;
import com.md.mdcms.rest.model.IBackingBean;
import com.md.mdcms.xml.Xml;

public class BaseHandler implements IConstants, Xml {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(LoginHandler.class);

	protected HttpServletRequest request;

	protected ApplUserBean userBean;
	protected Operation responseOperation;
	protected String jobNumber;
	protected State responseState;

	protected IBackingBean backingBeanManager;

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

	protected boolean loginCheckSuccessfull() {
		if (userBean.isLoggedOn()) {
			if (this.responseOperation != null) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Start create: " + this.getClass().getName() + " create Start");
				}

				com.md.mdcms.rest.model.State state = new com.md.mdcms.rest.model.State();
				try {
					BeanUtils.copyProperties(state, this.responseState);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				backingBeanManager.setResponseState(state);

				return true;
			} else {
				userBean.addToLoginStatus("noResponseOperation");
				LOG.error("BackingBean - no responseOperation for : " + this.getClass().getName());
				// errorOutcome = "contactAdmin.jsf";

				// String jobNumber = (String) ((HttpServletRequest)
				// FacesContext.getCurrentInstance().getExternalContext()
				// .getRequest()).getAttribute("jobNumber");

				// if (jobNumber == null) {
				// jobNumber = (String) ((HttpServletRequest)
				// FacesContext.getCurrentInstance().getExternalContext()
				// .getRequest()).getParameter("jobNumber");
				// }
				//
				// if (jobNumber != null) {
				// ApplicationHelper.getUserBean().doLogoff(jobNumber);
				// }
			}
		} else {
			LOG.error("BackingBean needs login: " + this.getClass().getName() + "/ loggedOn? " + userBean.isLoggedOn());
			// errorOutcome = "userLoginInit.jsf";
		}
		return false;
	}

	public boolean create() {
		long startTime = System.currentTimeMillis();

		if (this.responseOperation != null) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Construct: " + backingBeanManager.getClass().getName() + " - jobNumber: " + this.jobNumber);
			}

			long timeAlreadyUsed = this.responseOperation.getTotalTime();

			/*
			 * 1 - needs to be before the setScreenResponseValues and
			 * setFunctionResponseCodes because of the browserendered stuff
			 */
			Container reqCont = this.responseOperation.getContainer(REQUESTCODELIST);
			if (reqCont != null) {
				reqCont.fillRequestCodeList(backingBeanManager, true);
				// setButtonBean(reqCont.fillRequestCodeList(this));
			}

			Container cont = this.responseOperation.getContainer(CONTAINERTYPESCREEN);
			if (cont != null) {
				backingBeanManager.setScreenTitle(cont.getCaption().getValue());

				/*
				 * 2 - needs to be after the setRequestResponseCodes because of the
				 * browserendered stuff
				 */
				cont.fillScreenFields(backingBeanManager, true);

				// in case one grid is grid of MasterBackingBean
				if (cont.getGrids() != null && !cont.getGrids().isEmpty()) {
					Grid grid = cont.getGrids().get(0);

					// if (isMultiGrid()) {
					// Enumeration<String> gridKeys = getGrids().keys();
					//
					// while (gridKeys.hasMoreElements()) {
					// String gridKey = (String) gridKeys.nextElement();
					// IBackingBean gridBean = getGrids().get(gridKey);
					//
					// if (gridBean.getClass().getSimpleName().toLowerCase()
					// .startsWith(getResponseState().getScreen().toLowerCase()))
					// {
					// grid = cont.getGrid(gridKey);
					// break;
					// }
					// }
					// }

					// IBean bean = (IBean) MdUtil.createBackingBeanNew(this);
					//
					// if (bean != null) {
					// setGridId(grid.getId());
					// setCaption(cont.getCaption().getValue());
					// grid.fillHeaderItems(this);
					// grid.fillRowResponseValues(this, this, bean, grid);
					// setPaginatorBean(setPaginator(grid, this));

					/*
					 * 2 - needs to be after the setRequestResponseCodes because of the
					 * browserendered stuff
					 */
					/*
					 * THIS THIS THIS?????
					 */
					// grid.fillFunctionResponseCodes(backingBean);
					// }

					/* needs this position */
					// grid.fillLinklist(this);
					//
					// if (isMultiGrid()) {
					// generateGrids(this);
					// }
				}

				// whileGenerateBeanInit();

				// setScrollToXY(userBean.getScrollPositionSaveFor(this.getClass().getSimpleName(),
				// getJobNumber()));
			} else {
				// setGridId("Grid");
				// setCaption("Caption");
				// setScreenTitle("Screen title");
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

	protected boolean iSeriesPropertiesAvailable() {
		boolean noError = true;
		// ApplUserBean applUserBean = ApplicationHelper.getUserBean();

		/*
		 * host
		 */
		if (ApplicationConfigurationBean.getInstance().isNotHostAvailable()) {
			// applUserBean.addToLoginStatus("hostNotSetContactAdmin");
			noError = false;
		}

		/*
		 * userType
		 */
		if (ApplicationConfigurationBean.getInstance().isNotCorrectUserType()) {
			// applUserBean.addToLoginStatus("userTypeSetIncorrect");
			noError = false;
		}
		return noError;
	}

}
