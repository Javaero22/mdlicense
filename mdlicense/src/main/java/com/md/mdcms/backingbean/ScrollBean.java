package com.md.mdcms.backingbean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScrollBean {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(ScrollBean.class);

	public static String BEAN_NAME = "scrollBean";

	private String scrollToXY;
	private int scrollToX;
	private int scrollToY;

	public ScrollBean() {
		super();
	}

	public static ScrollBean getInstance() {
		// return (ScrollBean) JsfSupporter.findManagedBean(BEAN_NAME);
		return null;
	}

	public void reset() {
		// ((ScrollBean) JsfSupporter.findManagedBean(BEAN_NAME))
		// .setScrollToXY("0,0");
	}

	public void setScrollToXY(String scrollToXY) {
		try {
			String[] scrollPositions = scrollToXY.split(",");
			this.scrollToX = Integer.valueOf(scrollPositions[0]);
			this.scrollToY = Integer.valueOf(scrollPositions[1]);

			// System.out.println("ScrollBean: ScrollToX: " + this.scrollToX +
			// " / ScrollToY: "
			// + scrollToY);

			LOG.info("ScrollToX: " + this.scrollToX + " / ScrollToY: " + scrollToY);
			this.scrollToXY = scrollToXY;
		} catch (Exception e) {
			LOG.info("scrollToXY: " + scrollToXY, e);
		}
	}

	public String getScrollToXY() {
		return scrollToXY;
	}

	public int getScrollToX() {
		return scrollToX;
	}

	public void setScrollToX(int scrollToX) {
		this.scrollToX = scrollToX;
	}

	public int getScrollToY() {
		return scrollToY;
	}

	public void setScrollToY(int scrollToY) {
		this.scrollToY = scrollToY;
	}

}
