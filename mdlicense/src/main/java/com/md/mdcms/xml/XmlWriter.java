package com.md.mdcms.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlWriter implements Runnable {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(XmlWriter.class);

	private String request;
	private String response;
	private String requestfilename;
	private String responsefilename;
	private int keepRate = 10;
	private String userId;
	private String path;
	private int type;
	private boolean ok;

	/**
	 * @param path
	 * @param userId
	 * @param keepRate
	 */
	public XmlWriter(String path, String userId, int keepRate) {
		super();
		this.path = path;
		this.userId = userId;
		this.keepRate = Integer.valueOf(keepRate);
	}

	public XmlWriter(int type, String path, String reqFileName,
			String resFileName, String xmlReq, String xmlRes, String userId,
			int keepRate) {
		super();
		this.path = path;
		this.requestfilename = reqFileName;
		this.responsefilename = resFileName;
		this.request = xmlReq;
		this.response = xmlRes;
		this.userId = userId;
		this.keepRate = keepRate;
		this.type = type;
	}

	public void run() {
		switch (type) {
		case 1:
			writeRequest();
			deleteRequestFiles();
			break;

		case 2:
			writeResponse();
			deleteResponseFiles();
			break;

		}
	}

	private void writeRequest() {
		BufferedWriter br;
		FileOutputStream requestFileStream = null;
		try {
			ok = true;
			requestFileStream = new FileOutputStream(
					new File(path + userId + File.separator + "req"
							+ File.separator + requestfilename));
		} catch (FileNotFoundException e) {
			new File(path + userId + File.separator + "req").mkdirs();
			ok = false;
		}
		try {
			if (!ok)
				requestFileStream = new FileOutputStream(new File(path + userId
						+ File.separator + "req" + File.separator
						+ requestfilename));
			br = new BufferedWriter(new OutputStreamWriter(requestFileStream,
					"UTF-8"));
			br.write(request);
			br.flush();
			br.close();
		} catch (IOException e) {
			LOG.fatal("XmlWriter ", e);
		}
	}

	private void writeResponse() {
		BufferedWriter br;
		FileOutputStream responseFileStream = null;
		try {
			ok = true;
			responseFileStream = new FileOutputStream(new File(path + userId
					+ File.separator + "res" + File.separator
					+ responsefilename));
		} catch (FileNotFoundException e) {
			new File(path + userId + File.separator + "res").mkdirs();
			ok = false;
		}
		try {
			if (!ok)
				responseFileStream = new FileOutputStream(new File(path
						+ userId + File.separator + "res" + File.separator
						+ responsefilename));
			br = new BufferedWriter(new OutputStreamWriter(responseFileStream,
					"UTF-8"));
			br.write(response);
			br.flush();
			br.close();
		} catch (IOException e) {
			LOG.fatal("XmlWriter ", e);
		}
	}

	private void deleteRequestFiles() {
		File file = new File(path + userId + File.separator + "req");
		File fileToDelete;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			fileToDelete = files[0];
			if (files.length > keepRate) {
				for (int i = 1; i < files.length; i++) {
					if (fileToDelete.lastModified() > files[i].lastModified())
						fileToDelete = files[i];
				}
				fileToDelete.delete();
			}
		}
	}

	private void deleteResponseFiles() {
		File file = new File(path + userId + File.separator + "res");
		File fileToDelete;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			fileToDelete = files[0];
			if (files.length > keepRate) {
				for (int i = 1; i < files.length; i++) {
					if (fileToDelete.lastModified() > files[i].lastModified())
						fileToDelete = files[i];
				}
				fileToDelete.delete();
			}
		}
	}

	public String getResponse() {
		return response;
	}

	public String getResponsefilename() {
		return responsefilename;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public void setResponsefilename(String responsefilename) {
		this.responsefilename = responsefilename;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRequest() {
		return request;
	}

	public String getRequestfilename() {
		return requestfilename;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public void setRequestfilename(String requestfilename) {
		this.requestfilename = requestfilename;
	}

}
