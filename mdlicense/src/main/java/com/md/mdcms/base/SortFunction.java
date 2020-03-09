package com.md.mdcms.base;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.IBackingBean;
import com.md.mdcms.bean.IBean;
import com.md.mdcms.model.Grid;
import com.md.mdcms.model.Operation;
import com.md.mdcms.util.MdUtil;
import com.md.mdcms.xml.RequestXml;
import com.md.mdcms.xml.ResponseXml;

public class SortFunction extends Function {

	/** Log instance for this class. */
	private static final Log LOG = LogFactory.getLog(SortFunction.class);

	public SortFunction(IBackingBean backingBean, Operation requestOperation) throws XMLStreamException, IOException {
		super();
		this.reqBean = backingBean;
		this.requestOperation = requestOperation;
		doSort();
	}

	private void doSort() throws XMLStreamException, IOException {

		long startTime = System.currentTimeMillis();

		String xmlRequestString = RequestXml.generateXmlRequestString(requestOperation);

		String xmlResponseString = ApplicationHelper.getUserBean().runMiddleware(this.requestOperation.getJobNumber(),
				this.reqBean.getRequestState(), xmlRequestString, false).getXmlResponseString();

		if (xmlResponseString.startsWith(EXCEPTION)) {
			// MiddlewareException exc = new MiddlewareException("",
			// FacesMessage.SEVERITY_ERROR.toString(), xmlResponseString);
			// ApplicationHelper.getUserBean().addException(exc);
		} else {
			Operation responseOperation = ResponseXml.generateOperation(xmlResponseString);

			IBackingBean backingBean = null;
			IBean bean = null;

			try {
				if (this.reqBean != null) {
					Grid grid;
					if (this.reqBean.isMultiGrid()) {
						String gridId = this.reqBean.getSelectedGridId();
						backingBean = this.reqBean.getGrids().get(gridId);

						bean = (IBean) MdUtil.createBackingBeanNew(backingBean);

						grid = responseOperation.getContainer(CONTAINERTYPESCREEN).getGrid(gridId);
						grid.fillRowResponseValues(this.reqBean, backingBean, bean, grid);
					} else {
						bean = (IBean) MdUtil.createBackingBeanNew(this.reqBean);
						grid = responseOperation.getContainer(CONTAINERTYPESCREEN).getGrids().get(0);
						grid.fillRowResponseValues(this.reqBean, this.reqBean, bean, grid);
					}
					setPaginator(grid);
				} else {
					// MiddlewareException exc = new MiddlewareException("",
					// FacesMessage.SEVERITY_ERROR.toString(), SCREEN_UC
					// + " " + gridBackingBeanName
					// + " is not defined");
					// ApplicationHelper.getUserBean().addException(exc);
					// this.reqBean.setResponseState(this.reqBean
					// .getSavedResponseState());
					return;
				}
			} catch (Exception e) {
				LOG.error("getData -> Exception ", e);
			}

			responseOperation.setTotalTime(System.currentTimeMillis() - startTime);

			this.reqBean.setResponseOperation(responseOperation);
		}
	}

	/*
	 * paginator
	 */
	protected void setPaginator(Grid grid) {
		if (this.reqBean.isMultiGrid()) {
			String gridId = grid.getId();
			IBackingBean backingBean = reqBean.getGrids().get(gridId);

			int maxPages = Integer.parseInt(grid.getMaxpages());
			int pageNr = Integer.parseInt(grid.getPagenr());
			backingBean.getPaginatorBean().setPagenr(pageNr);
			backingBean.getPaginatorBean().setMaxpages(maxPages);
		} else {
			int maxPages = Integer.parseInt(grid.getMaxpages());
			this.reqBean.setMaxpages(maxPages);
			int pageNr = Integer.parseInt(grid.getPagenr());

			this.reqBean.getPaginatorBean().setPagenr(pageNr);
			this.reqBean.getPaginatorBean().setMaxpages(maxPages);
		}
	}

}
