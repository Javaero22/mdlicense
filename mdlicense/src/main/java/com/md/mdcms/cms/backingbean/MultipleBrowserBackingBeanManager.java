package com.md.mdcms.cms.backingbean;

public class MultipleBrowserBackingBeanManager extends MdBackingBean {

	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "multipleBrowserBackingBean";

	private String emaillink;
	private boolean linkrendered;

	public MultipleBrowserBackingBeanManager() {
		super();

		// FacesContext context = FacesContext.getCurrentInstance();
		// ExternalContext externalContext = context.getExternalContext();

		// Map<String, String> requestParameterMap =
		// externalContext.getRequestParameterMap();

		// String fullPath = ApplicationConfigurationBean.getConf()
		// .getServerContextPath()
		// + externalContext.getRequestContextPath();

		// String fullPath = externalContext.getRequestContextPath();
		//
		// String link = requestParameterMap.get("link");
		// if (link != null && !link.equals("") && "true".equals(link)) {
		// StringBuffer sb = new StringBuffer();
		// sb.append(fullPath);
		// sb.append("/pages/userLoginInit.jsf?link=true");
		//
		// String screen = requestParameterMap.get("screen");
		// if (screen != null && !"".equals(screen)) {
		// sb.append("&screen=");
		// sb.append(screen);
		// }
		// String projectid = requestParameterMap.get("projectId");
		// if (projectid != null && !"".equals(projectid)) {
		// sb.append("&projectId=");
		// sb.append(projectid);
		// }
		// String taskid = requestParameterMap.get("taskId");
		// if (taskid != null && !"".equals(taskid)) {
		// sb.append("&taskId=");
		// sb.append(taskid);
		// }
		// String subtaskid = requestParameterMap.get("subtaskId");
		// if (subtaskid != null && !"".equals(subtaskid)) {
		// sb.append("&subtaskId=");
		// sb.append(subtaskid);
		// }
		// String location = requestParameterMap.get("location");
		// if (location != null && !"".equals(location)) {
		// sb.append("&location=");
		// sb.append(location);
		// }
		// String application = requestParameterMap.get("application");
		// if (application != null && !"".equals(application)) {
		// sb.append("&application=");
		// sb.append(application);
		// }
		// String rfp = requestParameterMap.get("RFP");
		// if (rfp != null && !"".equals(rfp)) {
		// sb.append("&RFP=");
		// sb.append(rfp);
		// }
		// emaillink = sb.toString();
		// this.linkrendered = true;
		// }
	}

	/**
	 * @return the emaillink
	 */
	public String getEmaillink() {
		return emaillink;
	}

	/**
	 * @param emaillink
	 *            the emaillink to set
	 */
	public void setEmaillink(String emaillink) {
		this.emaillink = emaillink;
	}

	/**
	 * @return the linkrendered
	 */
	public boolean isLinkrendered() {
		return linkrendered;
	}

	/**
	 * @param linkrendered
	 *            the linkrendered to set
	 */
	public void setLinkrendered(boolean linkrendered) {
		this.linkrendered = linkrendered;
	}

}
