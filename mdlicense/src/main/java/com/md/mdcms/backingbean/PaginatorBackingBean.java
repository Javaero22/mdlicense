package com.md.mdcms.backingbean;

import java.util.ArrayList;
import java.util.List;

import com.md.mdcms.model.PaginatorItem;
import com.md.mdcms.model.State;

public class PaginatorBackingBean {

	public static final String BEAN_NAME = "paginatorBean";
	private int pagenr;
	private int maxpages;
	private List<PaginatorItem> pages;
	private String userSelectedPage;
	private String currentBackingBeanName;

	public PaginatorBackingBean() {
		super();
	}

	public boolean isBeanrendered() {
		if (maxpages > 1)
			return true;
		else
			return false;
	}

	public boolean isEndrendered() {
		if (pagenr < maxpages)
			return true;
		else
			return false;
	}

	public boolean isStartrendered() {
		if (pagenr > 1)
			return true;
		else
			return false;
	}

	public boolean isMiddlerendered() {
		if (maxpages > 1)
			return true;
		else
			return false;
	}

	public int getPagenr() {
		return pagenr;
	}

	public void setPagenr(int pagenr) {
		this.pagenr = pagenr;
	}

	public int getMaxpages() {
		return maxpages;
	}

	public void setMaxpages(int maxpages) {
		this.maxpages = maxpages;
	}

	public String getUserSelectedPage() {
		return userSelectedPage;
	}

	public void setUserSelectedPage(String userSelectedPage) {
		this.userSelectedPage = userSelectedPage;
	}

	public List<PaginatorItem> getPages() {
		pages = new ArrayList<PaginatorItem>();
		for (int i = 1; i <= maxpages; i++) {
			pages.add(new PaginatorItem(String.valueOf(i), i == pagenr));
		}
		return pages;
	}

	public void setPages(List<PaginatorItem> pages) {
		this.pages = pages;
	}

	public String getCurrentBackingBean() {
		return currentBackingBeanName;
	}

	public void setCurrentBackingBean(String currentBackingBeanName) {
		this.currentBackingBeanName = currentBackingBeanName;
	}

	public String linkPressed() {
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

		// IBackingBean currentBackingBean = (IBackingBean) JsfSupporter
		// .findManagedBean(currentBackingBeanName);

		State requestState = new State();
		// Operation operation = ApplicationHelper.createPagingOperation(
		// requestState, currentBackingBean.getGridId(), null, String
		// .valueOf(pagenr), currentBackingBean.getResponseState()
		// .getScreen(), currentBackingBean.getResponseState()
		// .getThreadString());
		//
		// currentBackingBean.setRequestState(requestState);
		// currentBackingBean.handlePaging(operation);

		return null;
	}

}
