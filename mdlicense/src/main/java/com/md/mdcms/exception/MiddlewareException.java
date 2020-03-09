package com.md.mdcms.exception;

public class MiddlewareException extends Throwable {

	private static final long serialVersionUID = -3683129754098894899L;

	private String id;
	private String as400severity;
	private String message;

	public MiddlewareException() {
		super();
	}

	public MiddlewareException(String id, String as400severity, String message) {
		this.id = id;
		this.as400severity = as400severity;
		this.message = message;
	}

	public MiddlewareException(Exception e) {
		this.id = "";
		this.as400severity = "40";
		this.message = e.getMessage();
	}

	// public FacesMessage asFacesMessage() {
	// FacesMessage facesMessage = new FacesMessage(getFacesSeverity(),
	// message, message);
	// return facesMessage;
	// }

	// public Severity getFacesSeverity() {
	// if (as400severity.equals("10")) {
	// return FacesMessage.SEVERITY_INFO;
	// } else if (as400severity.equals("20")) {
	// return FacesMessage.SEVERITY_WARN;
	// } else if (as400severity.equals("30")) {
	// return FacesMessage.SEVERITY_ERROR;
	// } else if (as400severity.equals("40")) {
	// return FacesMessage.SEVERITY_FATAL;
	// }
	// return FacesMessage.SEVERITY_INFO;
	// }

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the as400severity
	 */
	public String getAs400severity() {
		return as400severity;
	}

	/**
	 * @param as400severity
	 *            the as400severity to set
	 */
	public void setAs400severity(String as400severity) {
		this.as400severity = as400severity;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
