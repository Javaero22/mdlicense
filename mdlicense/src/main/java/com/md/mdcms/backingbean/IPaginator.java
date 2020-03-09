package com.md.mdcms.backingbean;

public interface IPaginator {

	public void setPagenr(int pageNr);

	public void setMaxpages(int maxPages);

	public void setCurrentBackingBean(IBackingBean backingBean);

}
