package com.md.mdcms.iseries;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.ConnectionEvent;
import com.ibm.as400.access.ConnectionListener;
import com.ibm.as400.access.DataQueue;
import com.ibm.as400.access.DataQueueEntry;
import com.ibm.as400.access.QSYSObjectPathName;
import com.ibm.as400.access.SocketProperties;
import com.ibm.as400.data.ProgramCallDocument;
import com.md.mdcms.backingbean.ApplicationConfigurationBean;
import com.md.mdcms.backingbean.StartConfigurationBean;
import com.md.mdcms.model.User;
import com.md.mdcms.model.XmlResponseObject;
import com.md.mdcms.util.ResourceHandler;
import com.md.mdcms.util.UnicodeString;

public class ISeriesSession implements ISeries, Serializable {

	/** Log instance for this class. */
	private static final Log LOG = LogFactory.getLog(ISeriesSession.class);

	private static final long serialVersionUID = 1L;
	// class variables without getters and setters

	private static long requestNumber = 0;
	private static String DEFAULT_PGM_DTQ_LIB_NAME = "MDREP";

	private AS400 as400;
	private DataQueue clientRequestQueue;
	private DataQueue serverResponseQueue;
	private DataQueueEntry responseEntry;
	/*
	 * wait - The number of seconds to wait if the queue contains no entries.
	 * Negative one (-1) means to wait until an entry is available.
	 */
	private static final int waitSec = 300; // 300 seconds

	// class variables with getters and setters
	private String jobName;
	private String jobUser;
	private String jobNumber;
	private String userPID;
	private String userLanguage;
	private String sessionKey;

	// plugin
	private String user;
	private boolean mdxrefAllowed;
	private boolean mdcmsAllowed;
	private String buildDate;
	private String dateFormat;
	private String timeSep;
	private User mdcmsUser;

	private static String PLUS = "+";
	private static String BLANK = " ";
	private static String PASSWORD_EXPIRES = "passwordExpires";
	private static String TOCHANGEPASSWORD = "toChangePassword";
	private static String WRONGUSERPASSWORD = "wrongUserPassword";

	private static String PASSWORDINCORRECT = "Password is incorrect.";
	private static String PASSWORDINCORRECTDISABLED = "Password is incorrect. User ID will";
	private static String USERINCORRECT = "User ID is not known.";

	public AS400 getAs400() {
		return as400;
	}

	public void open(User user) throws PropertyVetoException, AS400SecurityException, IOException {

		LOG.info("ISERIES OPEN CONNECTION");

		this.mdcmsUser = user;

		String host = user.getRepositoryLocation().getHost();
		if (this.mdcmsUser.isMdsecUser()) {
			User sysUser = ApplicationConfigurationBean.getInstance().getSysUser();
			this.as400 = new AS400(host, sysUser.getUserId(), sysUser.getPassword());
		} else {
			this.as400 = new AS400(host, user.getUserId(), user.getPassword());
		}
		this.as400.getSocketProperties().setSoTimeout(300000);

		this.as400.setGuiAvailable(false);

		if (LOG.isInfoEnabled()) {
			LOG.info("Login proceed with userId : " + user.getUserId());
			LOG.info("                   host   : " + user.getRepositoryLocation().getHost());
		}

		this.as400.validateSignon();

		this.setUser(user.getUserId());
		LOG.info("ISERIES SIGNON OK");
		this.as400.addConnectionListener(new ConnectionListener() {

			public void disconnected(ConnectionEvent event) {
				LOG.debug("Disconnected: " + event.getSource().toString());
			}

			public void connected(ConnectionEvent event) {
				LOG.debug("Connected: " + event.getSource().toString());
			}
		});
		if (LOG.isDebugEnabled()) {
			showASSystem();
		}
	}

	/**
	 * change the password for a user profile
	 * 
	 * @param password
	 *            the password for the given user profile
	 * @return a string containing the error message. no error occured if
	 *         message is empty
	 */
	public String changePassword(String oldPassword, String newPassword) {
		try {
			this.as400.changePassword(oldPassword, newPassword);
			return ("");
		} catch (Exception e) {
			return (e.getMessage());
		}
	}

	public String changePasswordMdsec(String oldPassword, String newPassword) {

		LOG.info("Change password for MDSEC userType");

		// hostEnvironment from the conf-faces-config.xml
		String hostEnvironment = ApplicationConfigurationBean.getInstance().getHostEnv();

		if (LOG.isInfoEnabled()) {
			LOG.info("ISERIES HOST ENVIRONMENT : " + hostEnvironment);
		}

		try {
			String program = "mdreppwd";
			String pgmParms = program + ".parms.";

			// call iSeries program to prepare session on server and get session
			// properties
			ProgramCallDocument pcml = new ProgramCallDocument(this.as400, "com.md.mdcms.iseries." + program);
			pcml.setPath(program, "/QSYS.LIB/" + DEFAULT_PGM_DTQ_LIB_NAME + hostEnvironment + ".LIB/"
					+ program.toUpperCase() + ".PGM");
			pcml.setValue(pgmParms + "userId", this.mdcmsUser.getUserId());
			pcml.setValue(pgmParms + "oldPw", oldPassword);
			pcml.setValue(pgmParms + "newPw", newPassword);

			pcml.callProgram(program);

			String severity = String.valueOf(pcml.getValue(pgmParms + "severity"));
			String message = String.valueOf(pcml.getValue(pgmParms + "message"));
			if ("30".equals(severity)) {
				if (LOG.isErrorEnabled()) {
					LOG.error("PCML called, severity : " + severity + " / message: " + message);
				}
				return message;
			}
			return ("");
		} catch (Exception e) {
			LOG.fatal(e);
			return (e.getMessage());
		}
	}

	/**
	 * initialize the iSeries session. this includes setting the library list
	 * and retrieving all relevent session information.
	 * 
	 * @return a string containing the error message. no error occured if
	 *         message is empty
	 */
	public String initialize() {
		LOG.info("ISERIES INITIALIZE");

		// hostEnvironment from the conf-faces-config.xml
		String hostEnvironment = ApplicationConfigurationBean.getInstance().getHostEnv();
		if (LOG.isInfoEnabled()) {
			LOG.info("ISERIES HOST ENVIRONMENT : " + hostEnvironment);
		}

		try {
			String program = "mdrepcon";
			String pgmParms = program + ".parms.";

			// call iSeries program to prepare session on server and get session
			// properties
			ProgramCallDocument pcml = new ProgramCallDocument(this.as400, "com.md.mdcms.iseries." + program);
			pcml.setPath(program, "/QSYS.LIB/" + DEFAULT_PGM_DTQ_LIB_NAME + hostEnvironment + ".LIB/"
					+ program.toUpperCase() + ".PGM");
			pcml.setValue(pgmParms + "environment", hostEnvironment);
			pcml.setValue(pgmParms + "client", ApplicationConfigurationBean.getInstance().getClient());

			if (mdcmsUser.getUserId() == null) {
				mdcmsUser.setUserId("dummy");
			}

			if (this.mdcmsUser.isIseriesUser()) {
				pcml.setValue(pgmParms + "userPID", mdcmsUser.getUserId());

				if (ApplicationConfigurationBean.getInstance().getClient().equals("CUSTOM_W")) {
					pcml.setValue(pgmParms + "jobName", "ADM");
					pcml.setValue(pgmParms + "jobUser", "QGPL");
				}

			} else if (this.mdcmsUser.isExternalUser()) {
				if (LOG.isInfoEnabled()) {
					LOG.info("External user (NetworkUser) : " + mdcmsUser.getNetworkUserId());
				}

				/*
				 * networkUserId can't be set to null
				 */
				String networkUserId = mdcmsUser.getNetworkUserId();
				if (networkUserId == null) {
					networkUserId = "";
				}
				pcml.setValue(pgmParms + "userPID", networkUserId);

				User sysUser = ApplicationConfigurationBean.getInstance().getSysUser();
				if (LOG.isInfoEnabled()) {
					LOG.info("External user (SystemUser) : " + sysUser.getUserId());
				}
				pcml.setValue(pgmParms + "userExternal", "Y");
				pcml.setValue(pgmParms + "systemUser", sysUser.getUserId());

			} else if (this.mdcmsUser.isMdsecUser()) {
				pcml.setValue(pgmParms + "userPID", mdcmsUser.getUserIdAndPw());
				User sysUser = ApplicationConfigurationBean.getInstance().getSysUser();
				if (LOG.isInfoEnabled()) {
					LOG.info("Mdsec user (SystemUser) : " + sysUser.getUserId());
				}
				pcml.setValue(pgmParms + "userExternal", "S");
				pcml.setValue(pgmParms + "systemUser", sysUser.getUserId());

				// if (ApplicationConfigurationBean.getInstance().getClient()
				// .equals("CUSTOM_W")) {
				// pcml.setValue(pgmParms + "jobName", "ADM");
				// pcml.setValue(pgmParms + "jobUser", "QGPL");
				// }
			}

			/*
			 * Thread handling - if session already exists use threads
			 */
			// HttpSession session = (HttpSession) FacesContext
			// .getCurrentInstance().getExternalContext()
			// .getSession(false);
			String threads = "1";
			// if (ApplicationConfigurationBean.getConf().getSessionIds()
			// .contains(session.getId())) {
			// threads = StartConfigurationBean.getInstance().getThreads();
			// }
			if (threads == null) {
				threads = "";
			}
			pcml.setValue(pgmParms + "threads", threads);
			if (LOG.isInfoEnabled()) {
				LOG.info("PCML => threads : " + pcml.getValue(pgmParms + "threads"));
			}
			/*
			 * Thread handling - end
			 */

			/*
			 * version check
			 */
			pcml.setValue(pgmParms + "version", StartConfigurationBean.getInstance().getVersion());

			pcml.callProgram(program);

			LOG.debug("===>>> START  - iSeriesSession infos -  START <<<===");
			String returnMessage = String.valueOf(pcml.getValue(pgmParms + "returnMessage"));
			if (!returnMessage.equals("")) {
				if (LOG.isInfoEnabled()) {
					LOG.info("PCML called, message : " + returnMessage);
				}
				return (returnMessage);
			}
			setJobName(String.valueOf(pcml.getValue(pgmParms + "jobName")));
			LOG.debug("jobName: " + getJobName());
			setJobNumber(String.valueOf(pcml.getValue(pgmParms + "jobNumber")));
			LOG.debug("jobNumber: " + getJobNumber());

			setJobUser(String.valueOf(pcml.getValue(pgmParms + "jobUser")));
			LOG.debug("jobUser: " + getJobUser());

			if (String.valueOf(pcml.getValue(pgmParms + "mdxref")).equals("N")) {
				setMdxrefAllowed(false);
			} else {
				setMdxrefAllowed(true);
			}
			LOG.debug("mdxrefAllowed: " + isMdxrefAllowed());

			if (String.valueOf(pcml.getValue(pgmParms + "mdcms")).equals("N")) {
				this.setMdcmsAllowed(false);
			} else {
				this.setMdcmsAllowed(true);
			}
			LOG.debug("mdcmsAllowed: " + isMdcmsAllowed());

			if (LOG.isInfoEnabled()) {
				LOG.info("PCML => buildDate : " + pcml.getValue(pgmParms + "buildDate"));
			}
			setBuildDate(String.valueOf(pcml.getValue(pgmParms + "buildDate")));
			setDateFormat(String.valueOf(pcml.getValue(pgmParms + "dateFormat")));
			setTimeSep(String.valueOf(pcml.getValue(pgmParms + "timeSep")));

			setSessionKey(mdcmsUser.getUserId().toLowerCase() + this.jobNumber);

			// prepare data queue objects
			String queueName = "MD" + jobNumber + "CQ";
			QSYSObjectPathName queuePath = new QSYSObjectPathName(DEFAULT_PGM_DTQ_LIB_NAME + hostEnvironment, queueName,
					"DTAQ");
			this.clientRequestQueue = new DataQueue(this.as400, queuePath.getPath());

			queueName = "MD" + jobNumber + "SQ";
			queuePath = new QSYSObjectPathName(DEFAULT_PGM_DTQ_LIB_NAME + hostEnvironment, queueName, "DTAQ");
			this.serverResponseQueue = new DataQueue(this.as400, queuePath.getPath());
			if (LOG.isInfoEnabled()) {
				LOG.info("PCML call was successfull");
			}

			if (mdcmsUser.isMdsecUser()) {
				String userControl = String.valueOf(pcml.getValue(pgmParms + "userControl"));
				if (!"".equals(userControl)) {
					LOG.debug("userControl: " + userControl);
					return userControl;
				}
			}

			LOG.debug("===>>> END  - iSeriesSession infos -  END <<<===");

			return ("");
		} catch (Exception e) {
			LOG.fatal(e);
			return (e.getMessage());
		}
	}

	public Vector<Object> passwordExpirationInformation() {
		Vector<Object> pei = new Vector<Object>();
		GregorianCalendar passwordExpiration = getPasswordExpiration();

		if (passwordExpiration == null) {
			pei.add(false);
		} else {
			long diff = passwordExpiration.getTimeInMillis() - System.currentTimeMillis();
			// 7 Tage x 24 h x 60 min x 60 sek x 1000 millisekunden
			if (diff < 604800000) {
				pei.add(true);
				StringBuffer date = new StringBuffer();
				date.append(passwordExpiration.get(Calendar.DAY_OF_MONTH));
				date.append(".");
				date.append(passwordExpiration.get(Calendar.MONTH) + 1);
				date.append(".");
				date.append(passwordExpiration.get(Calendar.YEAR));
				date.append(". ");
				pei.add(date.toString());
			} else {
				pei.add(false);
			}
		}

		return pei;
	}

	/**
	 * get password expiration date
	 * 
	 * @param date
	 *            password expires, null if never expires
	 * @return a calendar containing the date or null
	 */
	public GregorianCalendar getPasswordExpiration() {
		try {
			return (this.as400.getPasswordExpirationDate());
		} catch (Exception e) {
			return (null);
		}
	}

	/**
	 * send xml request to middleware and get xml response from middleware
	 * 
	 * @param a
	 *            string containing the xmlRequest
	 * @return a string containing the xmlResponse
	 */
	public synchronized XmlResponseObject runMiddleware(String xmlRequest) {

		long curTime;
		long startTime = 0;
		long lapTime = 0;
		startTime = System.currentTimeMillis();
		requestNumber++;
		LOG.info("RQ: " + requestNumber + "=== runMiddleware startTime taken: " + startTime + " ====");

		XmlResponseObject xmlResponseObject = new XmlResponseObject();
		short onCount = 0;

		while (onCount < 2) {
			try {
				// write request to client queue
				int i = 0;
				StringBuilder queueData = new StringBuilder();
				// String queueData = "";
				String xmlResponse = "";

				while (i < xmlRequest.length()) {
					if (i + 32000 < xmlRequest.length()) {
						queueData.append(PLUS);
						queueData.append(xmlRequest.substring(i, i + 32000));
						// queueData = "+" + xmlRequest.substring(i, i + 32000);
					} else {
						queueData.append(BLANK);
						queueData.append(xmlRequest.substring(i, xmlRequest.length()));
						// queueData = " " + xmlRequest.substring(i,
						// xmlRequest.length());
					}
					this.clientRequestQueue.write(queueData.toString());
					// this.clientRequestQueue.write(queueData);
					queueData = new StringBuilder();
					i += 32000;
				}

				curTime = System.currentTimeMillis();
				xmlResponseObject.setMiddlewareRequestTime(curTime - startTime);
				LOG.info("RQ: " + requestNumber + "=== runMiddleware req sent:        " + (curTime - startTime)
						+ " ====");
				lapTime = curTime;

				// get response from server queue
				boolean moreData = true;
				String queueDataString = "";
				int responseLoopCount = 1;
				long responseTotalTime = 0;
				while (moreData) {
					/*
					 * The number of seconds to wait if the queue contains no
					 * entries. Negative one (-1) means to wait until an entry
					 * is available.
					 */
					this.responseEntry = this.serverResponseQueue.read(waitSec);

					curTime = System.currentTimeMillis();
					responseTotalTime = responseTotalTime - lapTime + curTime;
					LOG.info("RQ: " + requestNumber + "=== runMiddleware res get : " + responseLoopCount + " :    "
							+ (curTime - lapTime) + " ====");
					responseLoopCount++;
					lapTime = curTime;

					if (this.responseEntry == null) {
						xmlResponse = "EXCEPTION: " + ResourceHandler.getMessageResourceString("noResponseFromServer");
						moreData = false;
					} else {
						queueDataString = this.responseEntry.getString();
						if (queueDataString.length() < 2) {
							if (xmlResponse.length() < 1) {
								xmlResponse = "EXCEPTION: "
										+ ResourceHandler.getMessageResourceString("insufficientResponseFromServer");
							}
							moreData = false;
						} else {
							xmlResponse += queueDataString.substring(1, queueDataString.length());
							if (queueDataString.substring(0, 1).equals(" ")) {
								moreData = false;
							}
						}
					}
				}

				curTime = System.currentTimeMillis();
				xmlResponseObject.setMiddlewareResponseTime(responseTotalTime);
				LOG.info(
						"RQ: " + requestNumber + "=== runMiddleware response total:  " + (responseTotalTime) + " ====");

				xmlResponseObject.setMiddlewareTime(curTime - startTime);
				LOG.info("RQ: " + requestNumber + "=== runMiddleware total:           " + (curTime - startTime)
						+ " ====");

				/*
				 * 20120221 -> translate response
				 */
				xmlResponse = UnicodeString.convertResponse(xmlResponse);
				xmlResponseObject.setXmlResponseString(xmlResponse);

				return (xmlResponseObject);
			} catch (Exception e) {
				xmlResponseObject.setXmlResponseString("EXCEPTION: " + e.getMessage());
				LOG.info("Exception: " + e.getMessage());
				curTime = System.currentTimeMillis();
				LOG.info("RQ: " + requestNumber + "=== runMiddleware time since start total:  " + (curTime - startTime)
						+ " ====");
				onCount++;
			}
		}
		return xmlResponseObject;
	}

	/**
	 * cleanly close all iSeries services
	 */
	public void close() {
		try {
			if (this.clientRequestQueue != null) {
				this.clientRequestQueue.write("E");
			}
			if (this.as400 != null) {
				this.as400.disconnectAllServices();
			}
		} catch (Exception e) {
		}
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getJobUser() {
		return jobUser;
	}

	public void setJobUser(String jobUser) {
		this.jobUser = jobUser;
	}

	public String getUserLanguage() {
		return userLanguage;
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	public String getUserPID() {
		return userPID;
	}

	public void setUserPID(String userPID) {
		this.userPID = userPID;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public boolean isMdxrefAllowed() {
		return mdxrefAllowed;
	}

	public void setMdxrefAllowed(boolean mdxrefAllowed) {
		this.mdxrefAllowed = mdxrefAllowed;
	}

	public boolean isMdcmsAllowed() {
		return mdcmsAllowed;
	}

	public void setMdcmsAllowed(boolean mdcmsAllowed) {
		this.mdcmsAllowed = mdcmsAllowed;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getTimeSep() {
		return timeSep;
	}

	public void setTimeSep(String timeSep) {
		this.timeSep = timeSep;
	}

	private void showASSystem() {
		LOG.debug(">===< AS400 System settings>=====<");
		LOG.debug("CCSID                       : " + this.as400.getCcsid());
		LOG.debug("DDMRDB                      : " + this.as400.getDDMRDB());
		LOG.debug("GSSName                     : " + this.as400.getGSSName());
		LOG.debug("GSSOption                   : " + this.as400.getGSSOption());
		try {
			LOG.debug("JobCCSIDEncoding            : " + this.as400.getJobCCSIDEncoding());
		} catch (AS400SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.debug("NLV                         : " + this.as400.getNLV());
		try {
			LOG.debug("Release                     : " + this.as400.getRelease());
		} catch (AS400SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.debug("SystemName                  : " + this.as400.getSystemName());
		try {
			LOG.debug("Version                     : " + this.as400.getVersion());
		} catch (AS400SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			LOG.debug("VRM                         : " + this.as400.getVRM());
		} catch (AS400SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.debug("Locale                      : " + this.as400.getLocale());
		try {
			LOG.debug("PreviousSignonDate          : " + this.as400.getPreviousSignonDate());
		} catch (AS400SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SocketProperties socketProps = this.as400.getSocketProperties();
		LOG.debug(">===< SocketProperties for AS400 >=====<");
		LOG.debug("====> IsKeepAlive            : " + socketProps.isKeepAlive() + "<====");
		LOG.debug("====> isKeepAliveSet         : " + socketProps.isKeepAliveSet() + "<====");
		LOG.debug("====> isReceiveBufferSizeSet : " + socketProps.isReceiveBufferSizeSet() + "<====");
		LOG.debug("====> isSendBufferSizeSet    : " + socketProps.isSendBufferSizeSet() + "<====");
		LOG.debug("====> isSoLingerSet          : " + socketProps.isSoLingerSet() + "<====");
		LOG.debug("====> isSoTimeoutSet         : " + socketProps.isSoTimeoutSet() + "<====");
		LOG.debug("====> isTcpNoDelay           : " + socketProps.isTcpNoDelay() + "<====");
		LOG.debug("====> isTcpNoDelaySet        : " + socketProps.isTcpNoDelaySet() + "<====");
		LOG.debug("====> getReceiveBufferSize   : " + socketProps.getReceiveBufferSize() + "<====");
		LOG.debug("====> getSendBufferSize      : " + socketProps.getSendBufferSize() + "<====");
		LOG.debug("====> SoLinger               : " + socketProps.getSoLinger() + "<====");
		LOG.debug("====> SoTimeout              : " + socketProps.getSoTimeout() + " <====");
		LOG.debug(">===< SocketProperties for AS400 >=====<");
		LOG.debug(">===< AS400 System settings>=====<");
	}

}
