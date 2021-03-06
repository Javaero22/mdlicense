package com.md.mdcms.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;

public class ErrorDisplay {
	private static final long serialVersionUID = 3123969847287207137L;
	private static final String BEAN_NAME = ErrorDisplay.class.getName();

	public String getInfoMessage() {
		return "Info message</b>.";
	}

	public String getStackTrace() {
		// FacesContext context = FacesContext.getCurrentInstance();
		// Map requestMap = context.getExternalContext().getRequestMap();
		// Throwable ex = (Throwable) requestMap
		// .get("javax.servlet.error.exception");

		StringWriter writer = new StringWriter();
		// PrintWriter pw = new PrintWriter(writer);
		// fillStackTrace(ex, pw);

		return writer.toString();
	}

	private void fillStackTrace(Throwable ex, PrintWriter pw) {
		if (null == ex) {
			return;
		}

		ex.printStackTrace(pw);

		if (ex instanceof ServletException) {
			Throwable cause = ((ServletException) ex).getRootCause();

			if (null != cause) {
				pw.println("Root Cause:");
				fillStackTrace(cause, pw);
			}
		} else {
			Throwable cause = ex.getCause();

			if (null != cause) {
				pw.println("Cause:");
				fillStackTrace(cause, pw);
			}
		}
	}
}
