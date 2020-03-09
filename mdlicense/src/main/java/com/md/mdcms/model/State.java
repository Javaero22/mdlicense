package com.md.mdcms.model;

import java.io.Serializable;

import com.md.mdcms.base.IConstants;
import com.md.mdcms.xml.Xml;

public class State implements Serializable, IConstants, Xml {

	private static final long serialVersionUID = 1L;
	private String action;
	private String function;
	private String langId;
	private String requestCode;
	private String screen;
	private String thread;
	private String alpha1;
	private String alpha2;
	private String numeric1;
	private String numeric2;
	private String dateTime;
	private long timeUsedMiddlewareRequest;
	private long timeUsedMiddlewareResponse;
	private long timeUsedMiddleware;
	private long timeUsedMiddlewareTotal;
	private long timeUsedTotal;

	public State() {
		super();
	}

	public State(String action, String function, String langId, String requestCode, String screen, String thread) {
		super();
		this.action = action;
		this.function = function;
		this.langId = langId;
		this.requestCode = requestCode;
		this.screen = screen;
		this.thread = thread;
	}

	public State(State state) {
		super();
		this.action = state.action;
		this.function = state.function;
		this.langId = state.langId;
		this.requestCode = state.requestCode;
		this.screen = state.screen;
		this.thread = state.thread;
	}

	public State(String function, String thread) {
		super();
		this.function = function;
		this.thread = thread;
	}

	public State(Container container) {
		super();
		this.action = container.getFieldValue(ACTION);
		this.alpha1 = container.getFieldValue(ALPHA1);
		this.alpha2 = container.getFieldValue(ALPHA2);
		this.function = container.getFieldValue(FUNCTION);
		this.langId = container.getFieldValue(LANGID);
		this.numeric1 = container.getFieldValue(NUMERIC1);
		this.numeric2 = container.getFieldValue(NUMERIC2);
		this.requestCode = container.getFieldValue(REQUESTCODE);
		this.screen = container.getFieldValue(SCREEN);
		this.thread = container.getFieldValue(THREAD);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(action);
		sb.append(" / ");
		sb.append(function);
		sb.append(" / ");
		sb.append(requestCode);
		sb.append(" / ");
		sb.append(screen);
		sb.append(" / ");
		sb.append(thread);
		sb.append(" / ");
		sb.append(alpha1);
		sb.append(" / ");
		sb.append(alpha2);
		sb.append(" / ");
		sb.append(numeric1);
		sb.append(" / ");
		sb.append(numeric2);
		return sb.toString();
	}

	public Container getAsGlobalContainer() {
		Container cont = new Container(CONTAINERTYPEGLOBAL);
		cont.addField(ACTION, getAction() != null ? getAction() : BLANK);
		cont.addField(FUNCTION, getFunction() != null ? getFunction().toUpperCase() : BLANK);
		cont.addField(LANGID, getLangId());
		cont.addField(REQUESTCODE, getRequestCode() != null ? getRequestCode().toUpperCase() : BLANK);
		cont.addField(SCREEN, getScreen() != null ? getScreen() : BLANK);
		cont.addField(THREAD, getThreadString() != null ? getThreadString() : BLANK);
		if (this.alpha1 != null && !"".equals(this.alpha1)) {
			cont.addField(ALPHA1, getAlpha1());
		}
		if (this.alpha2 != null && !"".equals(this.alpha2)) {
			cont.addField(ALPHA2, getAlpha2());
		}
		if (this.numeric1 != null && !"".equals(this.numeric1)) {
			cont.addField(NUMERIC1, getNumeric1());
		}
		if (this.numeric2 != null && !"".equals(this.numeric2)) {
			cont.addField(NUMERIC2, getNumeric2());
		}
		return cont;
	}

	public String getScreenThread() {
		if (this.screen != null) {
			// return this.screen + getThreadForBean();
			return this.screen;
		} else {
			return null;
		}
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * @param function
	 *            the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * @return the langId
	 */
	public String getLangId() {
		return langId;
	}

	/**
	 * @param langId
	 *            the langId to set
	 */
	public void setLangId(String langId) {
		this.langId = langId;
	}

	/**
	 * @return the requestCode
	 */
	public String getRequestCode() {
		return requestCode;
	}

	/**
	 * @param requestCode
	 *            the requestCode to set
	 */
	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	/**
	 * @return the screen
	 */
	public String getScreen() {
		return screen;
	}

	/**
	 * @param screen
	 *            the screen to set
	 */
	public void setScreen(String screen) {
		this.screen = screen;
	}

	/**
	 * @return the thread
	 */
	public String getThreadForBean() {
		if (this.thread != null && !"".equals(this.thread) && !"0".equals(this.thread)) {
			return "_".concat(this.thread);
		}
		return "";
	}

	/**
	 * @return the thread
	 */
	public String getThreadString() {
		if (this.thread != null && !"".equals(this.thread) && !"0".equals(this.thread)) {
			return this.thread;
		}
		return "";
	}

	public int getThreadNumber() {
		if (this.thread != null && !"".equals(this.thread)) {
			return Integer.valueOf(this.thread);
		} else {
			return 0;
		}
	}

	/**
	 * @param thread
	 *            the thread to set
	 */
	public void setThread(String thread) {
		this.thread = thread;
	}

	/**
	 * @return the alpha1
	 */
	public String getAlpha1() {
		return alpha1;
	}

	/**
	 * @param alpha1
	 *            the alpha1 to set
	 */
	public void setAlpha1(String alpha1) {
		this.alpha1 = alpha1;
	}

	/**
	 * @return the alpha2
	 */
	public String getAlpha2() {
		return alpha2;
	}

	/**
	 * @param alpha2
	 *            the alpha2 to set
	 */
	public void setAlpha2(String alpha2) {
		this.alpha2 = alpha2;
	}

	/**
	 * @return the numeric1
	 */
	public String getNumeric1() {
		return numeric1;
	}

	/**
	 * @param numeric1
	 *            the numeric1 to set
	 */
	public void setNumeric1(String numeric1) {
		this.numeric1 = numeric1;
	}

	/**
	 * @return the numeric2
	 */
	public String getNumeric2() {
		return numeric2;
	}

	/**
	 * @param numeric2
	 *            the numeric2 to set
	 */
	public void setNumeric2(String numeric2) {
		this.numeric2 = numeric2;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime
	 *            the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return the timeUsedMiddlewareRequest
	 */
	public long getTimeUsedMiddlewareRequest() {
		return timeUsedMiddlewareRequest;
	}

	/**
	 * @param timeUsedMiddlewareRequest
	 *            the timeUsedMiddlewareRequest to set
	 */
	public void setTimeUsedMiddlewareRequest(long timeUsedMiddlewareRequest) {
		this.timeUsedMiddlewareRequest = timeUsedMiddlewareRequest;
	}

	/**
	 * @return the timeUsedMiddlewareResponse
	 */
	public long getTimeUsedMiddlewareResponse() {
		return timeUsedMiddlewareResponse;
	}

	/**
	 * @param timeUsedMiddlewareResponse
	 *            the timeUsedMiddlewareResponse to set
	 */
	public void setTimeUsedMiddlewareResponse(long timeUsedMiddlewareResponse) {
		this.timeUsedMiddlewareResponse = timeUsedMiddlewareResponse;
	}

	/**
	 * @return the timeUsedMiddleware
	 */
	public long getTimeUsedMiddleware() {
		return timeUsedMiddleware;
	}

	/**
	 * @param timeUsedMiddleware
	 *            the timeUsedMiddleware to set
	 */
	public void setTimeUsedMiddleware(long timeUsedMiddleware) {
		this.timeUsedMiddleware = timeUsedMiddleware;
	}

	/**
	 * @return the timeUsedMiddlewareTotal
	 */
	public long getTimeUsedMiddlewareTotal() {
		return timeUsedMiddlewareTotal;
	}

	/**
	 * @param timeUsedMiddlewareTotal
	 *            the timeUsedMiddlewareTotal to set
	 */
	public void setTimeUsedMiddlewareTotal(long timeUsedMiddlewareTotal) {
		this.timeUsedMiddlewareTotal = timeUsedMiddlewareTotal;
	}

	/**
	 * @return the timeUsedTotal
	 */
	public long getTimeUsedTotal() {
		return timeUsedTotal;
	}

	/**
	 * @param timeUsedTotal
	 *            the timeUsedTotal to set
	 */
	public void setTimeUsedTotal(long timeUsedTotal) {
		this.timeUsedTotal = timeUsedTotal;
	}

}
