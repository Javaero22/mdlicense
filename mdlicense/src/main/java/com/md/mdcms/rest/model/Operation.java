package com.md.mdcms.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.md.mdcms.base.IConstants;
import com.md.mdcms.model.Container;
import com.md.mdcms.rest.base.MdUtil;
import com.md.mdcms.xml.Xml;

@Root(strict = false)
public class Operation implements Serializable, Xml, IConstants {

	/**
	* 
	*/
	private static final long serialVersionUID = -123975909507804556L;

	@ElementList(inline = true)
	private List<Container> containers;

	private volatile String jobNumber;
	private volatile String[] reqResFileNames;

	private volatile long middlewareRequestTime;
	private volatile long middlewareResponseTime;
	private volatile long totalTime;

	private volatile long timeStored;

	public Operation() {
		super();
	}

	public Operation(List<Container> containers) {
		super();
		this.containers = containers;
	}

	public Operation(Container container) {
		super();
		this.containers = new ArrayList<Container>();
		this.containers.add(container);
	}

	public Container getContainer(String type) {
		for (Container container : containers) {
			if (type.equals(container.getType()))
				return container;
		}
		return null;
	}

	public List<Container> getContainers() {
		return containers;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}

	public void addAll(List<Container> requestContainer) {
		this.containers.addAll(requestContainer);
	}

	public void add(Container container) {
		if (this.containers == null)
			this.containers = new ArrayList<Container>();

		this.containers.add(container);
	}

	public String getScreenName() {
		Container container = getContainer(CONTAINERTYPEGLOBAL);
		if (container != null) {
			return container.getFieldValue(SCREEN);
		}
		return null;
	}

	public State getState() {
		Container container = getContainer(CONTAINERTYPEGLOBAL);
		if (container != null) {
			State state = container.createState();
			return state;
		}
		return null;
	}

	public String getXlspth() {
		Container container = getContainer(CONTAINERTYPESCREEN);
		if (container != null) {
			return container.getFieldValue(XLSPTH);
		}
		return null;
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
	 * @return the middlewareRequestTime
	 */
	public long getMiddlewareRequestTime() {
		return middlewareRequestTime;
	}

	/**
	 * @param middlewareRequestTime
	 *            the middlewareRequestTime to set
	 */
	public void setMiddlewareRequestTime(long middlewareRequestTime) {
		this.middlewareRequestTime = middlewareRequestTime;
	}

	/**
	 * @return the middlewareResponseTime
	 */
	public long getMiddlewareResponseTime() {
		return middlewareResponseTime;
	}

	/**
	 * @param middlewareResponseTime
	 *            the middlewareResponseTime to set
	 */
	public void setMiddlewareResponseTime(long middlewareResponseTime) {
		this.middlewareResponseTime = middlewareResponseTime;
	}

	/**
	 * @return the totalTime
	 */
	public long getTotalTime() {
		return totalTime;
	}

	public long getTimeStored() {
		return timeStored;
	}

	public void setTimeStored(long timeStored) {
		this.timeStored = timeStored;
	}

	/**
	 * @param totalTime
	 *            the totalTime to set
	 */
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public void setReqResFileNames(String[] filenames) {
		this.reqResFileNames = filenames;
	}

	public String getFileName(int i) {
		return this.reqResFileNames[i];
	}

}
