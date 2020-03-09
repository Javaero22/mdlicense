package com.md.mdcms.backingbean;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.as400.access.AS400SecurityException;
import com.md.mdcms.base.ApplicationHelper;
import com.md.mdcms.base.Function;
import com.md.mdcms.base.IConstants;
import com.md.mdcms.base.IFunction;
import com.md.mdcms.base.INetworkUserProvider;
import com.md.mdcms.bean.MetanavBean;
import com.md.mdcms.bean.UserBean;
import com.md.mdcms.cms.function.MdFunction;
import com.md.mdcms.exception.MiddlewareException;
import com.md.mdcms.iseries.ISeries;
import com.md.mdcms.iseries.ISeriesSession;
import com.md.mdcms.model.LoginInformation;
import com.md.mdcms.model.Operation;
import com.md.mdcms.model.OutcomeObject;
import com.md.mdcms.model.RepositoryLocation;
import com.md.mdcms.model.ScrollPositionInformation;
import com.md.mdcms.model.State;
import com.md.mdcms.model.User;
import com.md.mdcms.model.XmlResponseObject;
import com.md.mdcms.util.FileUtil;
import com.md.mdcms.xml.RequestXml;
import com.md.mdcms.xml.ResponseXml;
import com.md.mdcms.xml.Xml;
import com.md.mdcms.xml.XmlWriter;

public class ApplUserBean extends UserBean implements IConstants, Xml {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6026725005375382970L;

	private static final Log LOG = LogFactory.getLog(ApplUserBean.class);

	private static final String UNABLETOCONNECTTOHOST = "Unable to connect to host; All connections timed out.";

	private static final String CSS_STANDARD = "standard.css";

	private static final String CSS_WIDE = "wide.css";

	private static final String CSS_WIDER = "wider.css";

	private Map<String, ISeries> userSessions;

	private String userLanguage;

	private User mdcmsUser;

	private String sessionId;

	private boolean showtime;

	private boolean fromChpass;

	private String[] filenames = new String[2];

	XmlWriter xmlRequestWriter;
	XmlWriter xmlResponseWriter;
	private boolean logXml;
	private boolean logXmlDdl;
	private StringBuffer fileName;

	private static int[][] dateFormatIndex = new int[3][2];
	private static int[] dateIndex = new int[3];
	private static String dateSeparator = "."; // default
	private static String dateFormat = null; // default

	// private Map<String, Vector<MiddlewareException>> exceptions;
	private Vector<MiddlewareException> exceptions;

	private String loginStatus = "";

	private IBackingBean requestBackingBean;
	private Map<String, Operation> responseOperations;
	private Map<String, Operation> responseOperationsRW0100;

	private Map<String, Operation> responseOperationsStore;

	private MetanavBean metanavBean;

	private Map<String, ScrollPositionInformation> scrollPositionSave;

	private String currentJobNumber;

	private String cssUsed = CSS_WIDE;

	public ApplUserBean() {
		super();
		if (LOG.isInfoEnabled()) {
			LOG.info("Initialization");
		}
	}

	public OutcomeObject processMdFunction() {
		synchronized (this) {
			long startTime = System.currentTimeMillis();

			IFunction function = null;
			OutcomeObject outcomeObject = new OutcomeObject();

			// FacesContext facesContext = FacesContext.getCurrentInstance();

			try {
				function = new MdFunction(this, this.requestBackingBean);

				Operation responseOperation = null;
				responseOperation = function.getResponseOperation();
				responseOperation.setJobNumber(this.requestBackingBean.getJobNumber());
				responseOperation.setReqResFileNames(this.filenames);

				// set the jobNumber into the request
				// HttpServletRequest request = (HttpServletRequest)
				// facesContext
				// .getExternalContext().getRequest();
				// request.setAttribute("jobNumber",
				// this.requestBackingBean.getJobNumber());

				outcomeObject.setOutcome(responseOperation.getScreenName());
				outcomeObject.setXlspth(responseOperation.getXlspth());

				placeResponseOperation(outcomeObject.getOutcome(), responseOperation);

				responseOperation.setTotalTime(System.currentTimeMillis() - startTime);

				if (LOG.isInfoEnabled()) {
					LOG.info("Total time/jobNumber: " + responseOperation.getTotalTime() + "/"
							+ responseOperation.getJobNumber());
				}

				return outcomeObject;
			} catch (XMLStreamException e) {
				// facesContext.addMessage("",
				// new MiddlewareException(e).asFacesMessage());
			} catch (IOException e) {
				// facesContext.addMessage("",
				// new MiddlewareException(e).asFacesMessage());
			}
			return null;
		}
	}

	public OutcomeObject processFunction() {
		synchronized (this) {
			long startTime = System.currentTimeMillis();

			IFunction function = null;
			OutcomeObject outcomeObject = new OutcomeObject();

			// FacesContext facesContext = FacesContext.getCurrentInstance();
			try {
				function = new Function(this.requestBackingBean);

				Operation responseOperation = null;
				responseOperation = function.getResponseOperation();
				responseOperation.setJobNumber(this.requestBackingBean.getJobNumber());

				outcomeObject.setOutcome(responseOperation.getScreenName());
				outcomeObject.setXlspth(responseOperation.getXlspth());

				LOG.info("Place responseOperation into responseOperations for outcome: " + outcomeObject.getOutcome());

				placeResponseOperation(outcomeObject.getOutcome(), responseOperation);

				responseOperation.setTotalTime(System.currentTimeMillis() - startTime);

				if (LOG.isInfoEnabled()) {
					LOG.info("Total time/jobNumber: " + responseOperation.getTotalTime() + "/"
							+ responseOperation.getJobNumber());
				}

				return outcomeObject;
			} catch (XMLStreamException e) {
				// facesContext.addMessage("", new
				// MiddlewareException(e).asFacesMessage());
				return null;
			} catch (IOException e) {
				// facesContext.addMessage("", new
				// MiddlewareException(e).asFacesMessage());
				return null;
			}
		}
	}

	private void placeResponseOperation(String outcome, Operation responseOperation) {

		LOG.info("Place responseOperation into responseOperations for outcome: " + outcome + " / jobNumber: "
				+ this.requestBackingBean.getJobNumber());

		// System.out
		// .println("Place responseOperation into responseOperations for
		// outcome: "
		// + outcome
		// + " / jobNumber: "
		// + this.requestBackingBean.getJobNumber());

		responseOperation.setTimeStored(System.currentTimeMillis());

		// if (CLASS_NAME_RW0100.startsWith(outcome)) {
		// getResponseOperationsRW0100().put(this.requestBackingBean.getJobNumber(),
		// responseOperation);
		// } else {
		getResponseOperations().put(this.requestBackingBean.getJobNumber(), responseOperation);
		// }
	}

	public String getSessionId() {
		if (showtime) {
			if (this.sessionId == null) {
				// this.sessionId = ((HttpSession)
				// FacesContext.getCurrentInstance().getExternalContext()
				// .getSession(false)).getId();
			}
			return this.sessionId;
		} else {
			return "";
		}
	}

	public String loginReset() {
		return null;
	}

	public String changeCss() {
		return null;
	}

	public String getUserLanguageCode() {
		if (getUserLanguage().equals("E")) {
			return "3";
		} else if (getUserLanguage().equals("D"))
			return "0";
		if (getUserLanguage().equals("F"))
			return "1";
		else
			return "3"; // default is english
	}

	public String getErrorId() {
		// Iterator<?> iterator =
		// FacesContext.getCurrentInstance().getClientIdsWithMessages();
		//
		// while (iterator.hasNext()) {
		// Object clientId = iterator.next();
		// if (clientId != null) {
		// return clientId.toString();
		// }
		// }
		return "none";
	}

	public String getBuildDate(String jobNumber) {
		return getUserSessions().get(jobNumber).getBuildDate();
	}

	public LoginInformation login(User mdcmsUser) {

		LOG.info("START LOGIN");
		ISeries iSeries = new ISeriesSession();
		LoginInformation loginInformation = new LoginInformation();

		boolean pass = false;
		int addressCount = 0;

		loginInformation.setLoginMessage("");
		while (!pass) {
			RepositoryLocation repLoc = RepositoryLocation.getAddress(addressCount);

			if (repLoc != null) {
				this.mdcmsUser = new User();
				this.mdcmsUser.setUserType(mdcmsUser.getUserType());
				this.mdcmsUser.setRepositoryLocation(repLoc);
				Integer sessionId = mdcmsUser.toSessionId();
				this.mdcmsUser.setSessionId(sessionId);
				String userId = mdcmsUser.getUserId();

				/*
				 * is iseries user
				 */
				if (mdcmsUser.isIseriesUser()) {
					if (LOG.isInfoEnabled()) {
						LOG.info("Is iSeries user and NetworkUserName is set : " + userId);
					}

					userId = userId.toUpperCase();
					this.mdcmsUser.setNetworkUserId(userId);
					this.mdcmsUser.setNetworkUserName(userId);
				} else if (mdcmsUser.isMdsecUser()) {
					/*
					 * is mdsec
					 */
					userId = userId.toUpperCase();
					this.mdcmsUser.setNetworkUserId(userId);
					this.mdcmsUser.setNetworkUserName(userId);
				} else {
					/*
					 * is external
					 */
					INetworkUserProvider networkUserProvider = ApplicationHelper.getNetworkUserProvider();

					if (networkUserProvider != null) {
						this.mdcmsUser.setNetworkUserId(networkUserProvider.getNetworkUserId());
						this.mdcmsUser.setNetworkUserName(networkUserProvider.getNetworkUserName());
						if (LOG.isInfoEnabled()) {
							LOG.info("NetworkUserId obtained from system : " + this.mdcmsUser.getNetworkUserId());
							LOG.info("NetworkUserName obtained from system: " + this.mdcmsUser.getNetworkUserName());
						}
					} else {
						LOG.error("NetworkUserProvider not implemented or not existing");
					}
				}
				this.mdcmsUser.setUserId(userId);
				this.mdcmsUser.setPassword(mdcmsUser.getPassword());

				try {
					iSeries.open(this.mdcmsUser);
					pass = true;
					loginInformation.setLoginMessage(iSeriesInitialize(iSeries));

					if (loginInformation.getLoginMessage().equals(ApplicationConfigurationBean.ISERIES)) {
						iSeries.close();
						iSeries.open(this.mdcmsUser);
						loginInformation.setLoginMessage(iSeriesInitialize(iSeries));
					}
					loginInformation.setJobNumber(iSeries.getJobNumber());
				} catch (AS400SecurityException e2) {
					pass = true;
					if (e2.getReturnCode() == AS400SecurityException.PASSWORD_EXPIRED) {
						LOG.info("PASSWORD EXPIRED");
						loginInformation.setLoginMessage("pw_exp;iseries");

						// default
						getUserSessions().put(PASSWORD_EXPIRED, iSeries);
					} else {
						LOG.info("SIGNON NOK : " + e2.getMessage());
						loginInformation.setLoginMessage(e2.getMessage());
					}
				} catch (Exception e2) {
					LOG.fatal("SIGNON NOK : " + e2.getMessage());
					loginInformation.setLoginMessage(e2.getMessage());
					addressCount++;
				}

				if (!loginInformation.getLoginMessage().startsWith("")) {
					if (!loginInformation.getLoginMessage().startsWith("pw_exp")) {
						this.mdcmsUser.setUserId("");
					}
					this.mdcmsUser.setPassword("");
				}
				// no more repositoryLocation available = null;
			} else {
				pass = true;
				MiddlewareException ex = new MiddlewareException("", "30",
						UNABLETOCONNECTTOHOST + " " + ApplicationConfigurationBean.getInstance().getHost());
				// ApplicationHelper.getUserBean().addException("0", ex);
				ApplicationHelper.getUserBean().addException(ex);

				loginInformation.setLoginMessage("userLoginInit");
			}
		}
		return loginInformation;
	}

	public String mdsecPreOpen(User mdcmsUser) {

		LOG.info("START MDSEC OPEN");
		ISeries iSeries = new ISeriesSession();

		boolean pass = false;
		int addressCount = 0;

		String iSeriesMessage = "";
		while (!pass) {
			RepositoryLocation repLoc = RepositoryLocation.getAddress(addressCount);

			if (repLoc != null) {
				this.mdcmsUser = new User();
				this.mdcmsUser.setUserType(mdcmsUser.getUserType());
				this.mdcmsUser.setRepositoryLocation(repLoc);
				Integer sessionId = mdcmsUser.toSessionId();
				this.mdcmsUser.setSessionId(sessionId);
				String userId = mdcmsUser.getUserId();

				userId = userId.toUpperCase();
				this.mdcmsUser.setNetworkUserId(userId);
				this.mdcmsUser.setNetworkUserName(userId);

				this.mdcmsUser.setUserId(userId);
				this.mdcmsUser.setPassword(mdcmsUser.getPassword());

				try {
					iSeries.open(this.mdcmsUser);
					pass = true;
					iSeries.close();
				} catch (AS400SecurityException e2) {
					pass = true;
					if (e2.getReturnCode() == AS400SecurityException.PASSWORD_EXPIRED) {
						LOG.info("PASSWORD EXPIRED");
						iSeriesMessage = "pw;System password expired.";
					} else {
						LOG.info("SIGNON NOK : " + e2.getMessage());
						iSeriesMessage = e2.getMessage();
						if (iSeriesMessage != null) {
							iSeriesMessage = "iseries;System " + iSeriesMessage;
						}
					}
				} catch (Exception e2) {
					LOG.info("SIGNON NOK : " + e2.getMessage());
					iSeriesMessage = "ex;" + e2.getMessage();
					addressCount++;
				}
			} else {
				pass = true;
				MiddlewareException ex = new MiddlewareException("", "30",
						UNABLETOCONNECTTOHOST + " " + ApplicationConfigurationBean.getInstance().getHost());
				// ApplicationHelper.getUserBean().addException("0", ex);
				ApplicationHelper.getUserBean().addException(ex);

				iSeriesMessage = "userLoginInit";
			}
		}
		return iSeriesMessage;
	}

	public LoginInformation loginFromChange() {
		String iSeriesMessage = "";
		ISeries iSeries = new ISeriesSession();
		LoginInformation loginInformation = new LoginInformation();
		try {
			iSeries.open(mdcmsUser);
		} catch (PropertyVetoException e) {
			iSeriesMessage = e.getMessage();
			LOG.fatal(e);
		} catch (AS400SecurityException e) {
			iSeriesMessage = e.getMessage();
			LOG.fatal(e);
		} catch (IOException e) {
			iSeriesMessage = e.getMessage();
			LOG.fatal(e);
		}
		if (!iSeriesMessage.equals("")) {
			loginInformation.setLoginMessage(iSeriesMessage);
			return loginInformation;
		}

		iSeriesMessage = iSeriesInitialize(iSeries);
		loginInformation.setJobNumber(iSeries.getJobNumber());
		return loginInformation;
	}

	private String iSeriesInitialize(ISeries iSeries) {

		String iSeriesMessage = iSeries.initialize();

		if (this.mdcmsUser.isMdsecUser()) {
			if (ApplicationConfigurationBean.ISERIES.equals(iSeriesMessage.toLowerCase())) {
				this.mdcmsUser.setUserType(ApplicationConfigurationBean.ISERIES);
				return ApplicationConfigurationBean.ISERIES;
			} else if (ApplicationConfigurationBean.EXPIRED.equals(iSeriesMessage)) {
				getUserSessions().put(PASSWORD_EXPIRED, iSeries);
				return "pw_exp;mdsec";
			}
		}

		boolean takenFromConfigFile = false;
		String dateFormat = ApplicationConfigurationBean.getInstance().getDateFormat();
		if (dateFormat != null && !"".equals(dateFormat)) {
			mdcmsUser.setDateFormat(dateFormat);
			takenFromConfigFile = true;
		} else {
			mdcmsUser.setDateFormat(iSeries.getDateFormat());
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("Used dateFormat from i5 : " + mdcmsUser.getDateFormat());
			if (takenFromConfigFile)
				LOG.info("dateFormat taken from config file");
		}

		String timeSeparator = ApplicationConfigurationBean.getInstance().getTimeSeparator();
		if (timeSeparator != null && !"".equals(timeSeparator)) {
			mdcmsUser.setTimeSep(timeSeparator);
			takenFromConfigFile = true;
		} else {
			mdcmsUser.setTimeSep(iSeries.getTimeSep());
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("Used timeSep from i5    : " + mdcmsUser.getTimeSep());
			if (takenFromConfigFile)
				LOG.info("timeSeparator taken from config file");
		}

		if (iSeriesMessage.equals("")) {
			computeDateInfo();

			this.setLoggedOn(true);

			getUserSessions().put(iSeries.getJobNumber(), iSeries);

			if (LOG.isWarnEnabled()) {
				StringBuffer sb = new StringBuffer();
				sb.append("<loggedin>");
				sb.append("  <env>");
				sb.append(ApplicationConfigurationBean.getInstance().getHostEnv());
				sb.append("  </env>");
				sb.append("  <user>");
				sb.append(mdcmsUser.getUserId());
				sb.append("  </user>");
				sb.append("  <time>");
				// sb.append(Functions.dateTime());
				sb.append("  </time>");
				sb.append("</loggedin>");
				LOG.warn(sb.toString());
			}

			Locale defLocale = new Locale("en", "US");
			if ("D".equals(getUserLanguage())) {
				defLocale = new Locale("de", "CH");
			}
			if ("F".equals(getUserLanguage())) {
				defLocale = new Locale("fr", "CH");
			}
			if ("I".equals(getUserLanguage())) {
				defLocale = new Locale("it", "CH");
			}
			this.setLocale(defLocale);

			// showGC();

			if (StartConfigurationBean.getInstance().isTest()) {
				Properties applicationProperties = new Properties();
				boolean fileRead = false;
				int xmlkeep = 10;
				int createFileTryCount = 0;

				while (!fileRead && createFileTryCount < 3) {

					try {
						File propertiesFile = new File(
								StartConfigurationBean.getInstance().getXmlfilepath() + MD_WORKFLOW_PROPERTIES);
						if (propertiesFile.exists()) {
							LOG.info(MD_WORKFLOW_PROPERTIES + " located in " + propertiesFile.getAbsolutePath() + " / "
									+ propertiesFile.getCanonicalPath());

							applicationProperties.load(new FileInputStream(propertiesFile));

							String user = mdcmsUser.getNetworkUserId().toLowerCase();

							LOG.info("Load data for user: " + user);

							String xmllog = applicationProperties.getProperty("xmllog").trim();
							LOG.info("xmllog: " + xmllog);
							if (xmllog.equalsIgnoreCase("all") || xmllog.contains(user)) {
								LOG.info("logXml set to true");
								this.logXml = true;
							}

							String xmllogddl = applicationProperties.getProperty("xmllogddl").trim();
							LOG.info("xmllogddl: " + xmllogddl);
							if (xmllogddl.equalsIgnoreCase("all") || xmllogddl.contains(user)) {
								LOG.info("logXmlDdl set to true");
								this.logXmlDdl = true;
							}

							if (this.logXml) {
								String showtime = applicationProperties.getProperty("showtime").trim();
								LOG.info("showtime: " + showtime);
								if (showtime.equalsIgnoreCase("all") || showtime.contains(user)) {
									this.showtime = true;
								}
							}

							String xmlkeepstring = applicationProperties.getProperty("xmlkeep_" + user);
							LOG.info("xmlkeep: " + xmlkeepstring);

							if (xmlkeepstring != null) {
								xmlkeep = Integer.parseInt(xmlkeepstring.trim());
							}

							fileRead = true;

						} else {
							FileUtil.createInitialApplProps();
							createFileTryCount++;
						}
					} catch (Exception e1) {
						FileUtil.createInitialApplProps();
						createFileTryCount++;
					}
				}

				String userName = mdcmsUser.getNetworkUserId();
				if (userName == null || "".equals(userName)) {
					userName = mdcmsUser.getUserId();
				}
				if (userName == null || "".equals(userName)) {
					userName = "dummy";
				}

				xmlRequestWriter = new XmlWriter(StartConfigurationBean.getInstance().getXmlfilepath(),
						mdcmsUser.getNetworkUserId(), xmlkeep);

				xmlResponseWriter = new XmlWriter(StartConfigurationBean.getInstance().getXmlfilepath(),
						mdcmsUser.getNetworkUserId(), xmlkeep);
			}

		}
		return iSeriesMessage;
	}

	public String doLogoff(String jobNumber) {
		logoff(jobNumber);
		return "logoff";
	}

	public void logoff(String jobNumber) {

		// LOG --- LOG --- LOG --- LOG
		if (LOG.isWarnEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("<loggedoff>");
			sb.append("  <env>");
			sb.append(ApplicationConfigurationBean.getInstance().getHostEnv());
			sb.append("  </env>");
			sb.append("  <user>");
			sb.append(mdcmsUser.getUserId());
			sb.append("  </user>");
			sb.append("  <time>");
			// sb.append(Functions.dateTime());
			sb.append("  </time>");
			sb.append("</loggedoff>");
			LOG.warn(sb.toString());
		}
		// LOG --- LOG --- LOG --- LOG

		// HttpSession session = (HttpSession)
		// FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		// boolean removed =
		// ApplicationConfigurationBean.getInstance().getSessionIds().remove(session.getId());
		// LOG.info("Session: " + session.getId() + " removed: " + removed);
		getUserSessions().get(jobNumber).close();
		getUserSessions().remove(jobNumber);

		// if
		// (!ApplicationConfigurationBean.getInstance().getSessionIds().contains(session.getId()))
		// {
		setLoggedOn(false);
		// }
	}

	public void logoffFromTimeout() {

		// LOG --- LOG --- LOG --- LOG
		if (LOG.isWarnEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("<loggedoff>");
			sb.append("  <env>");
			sb.append(ApplicationConfigurationBean.getInstance().getHostEnv());
			sb.append("  </env>");
			sb.append("  <user>");
			sb.append("from Timeout");
			sb.append("  </user>");
			sb.append("  <time>");
			// sb.append(Functions.dateTime());
			sb.append("  </time>");
			sb.append("</loggedoff>");
			LOG.warn(sb.toString());
		}
		// LOG --- LOG --- LOG --- LOG

		// getISeriesSession().close();

		setLoggedOn(false);

	}

	public String changePassword(String jobNumber, String oldpassword, String newpassword) {

		ISeries iSeries = null;
		if (jobNumber != null) {
			iSeries = getUserSessions().get(jobNumber);
		} else {
			// password expired
			iSeries = getUserSessions().get(PASSWORD_EXPIRED);
			// Map<String, ISeries> userSessions = getUserSessions();
			// for (String key : userSessions.keySet()) {
			// iSeries = userSessions.get(key);
			// break;
			// }
		}

		if (iSeries != null) {
			if (mdcmsUser.isIseriesUser()) {
				return iSeries.changePassword(oldpassword, newpassword);
			} else if (mdcmsUser.isMdsecUser()) {
				return iSeries.changePasswordMdsec(oldpassword, newpassword);
			}
		} else {
			// LoginInformation loginInformation = login(mdcmsUser);
		}
		return null;
	}

	public XmlResponseObject runMiddlewareDdl(State requestState, String xmlRequestString, boolean ddl) {

		Collection<ISeries> iSeriesCollection = getUserSessions().values();
		Vector<String> jobNumberToRemove = new Vector<String>();

		XmlResponseObject xmlResponseObject = null;

		for (ISeries iSeries : iSeriesCollection) {
			try {
				writeRequestXml(iSeries.getJobNumber(), requestState, xmlRequestString, ddl);

				xmlResponseObject = iSeries.runMiddleware(xmlRequestString);

				if (xmlResponseObject.getXmlResponseString().startsWith("EXCEPTION")) {
					jobNumberToRemove.add(iSeries.getJobNumber());
				} else {
					writeResponseXml(iSeries.getJobNumber(), requestState, xmlResponseObject, ddl);

					break;
				}
			} catch (Exception e) {
				LOG.fatal(e);
				jobNumberToRemove.add(iSeries.getJobNumber());
			}

			if (!jobNumberToRemove.isEmpty()) {
				// FacesContext facesContext =
				// FacesContext.getCurrentInstance();
				// ExternalContext extContext =
				// facesContext.getExternalContext();
				// HttpSession session = (HttpSession)
				// extContext.getSession(false);
				for (String jobNumber : jobNumberToRemove) {
					getUserSessions().remove(jobNumber);
					// ApplicationConfigurationBean.getInstance().getSessionIds().remove(session.getId());
				}
			}
		}

		return xmlResponseObject;
	}

	public XmlResponseObject runMiddleware(String jobNumber, State requestState, String xmlRequestString, boolean ddl) {

		writeRequestXml(jobNumber, requestState, xmlRequestString, ddl);

		ISeries iSeries = getUserSessions().get(jobNumber);
		XmlResponseObject xmlResponseObject = iSeries.runMiddleware(xmlRequestString);

		writeResponseXml(jobNumber, requestState, xmlResponseObject, ddl);

		return xmlResponseObject;
	}

	private void writeRequestXml(String jobNumber, State requestState, String xmlRequestString, boolean ddl) {
		if (doLogXmls(ddl)) {
			if (!xmlRequestString.contains("RW0930")) {
				synchronized (xmlRequestWriter) {
					filenames[0] = getFileName(jobNumber, requestState, true, ddl);
					xmlRequestWriter.setRequest(xmlRequestString);
					xmlRequestWriter.setRequestfilename(filenames[0]);
					xmlRequestWriter.setType(1);
					new Thread(xmlRequestWriter).start();
				}
			}
		}
	}

	private void writeResponseXml(String jobNumber, State requestState, XmlResponseObject xmlResponseObject,
			boolean ddl) {
		if (doLogXmls(ddl)) {
			if (!xmlResponseObject.getXmlResponseString().contains("RW0930")) {
				synchronized (xmlResponseWriter) {
					filenames[1] = getFileName(jobNumber, requestState, false, ddl);
					xmlResponseWriter.setType(2);
					xmlResponseWriter.setResponse(xmlResponseObject.getXmlResponseString());
					xmlResponseWriter.setResponsefilename(filenames[1]);
					new Thread(xmlResponseWriter).start();
				}
			}
		}
	}

	private boolean doLogXmls(boolean ddl) {
		if (ddl) {
			return logXmlDdl;
		} else {
			return logXml;
		}
	}

	private String getFileName(String jobNumber, State requestState, boolean isReq, boolean ddl) {
		fileName = new StringBuffer();
		if (isReq) {
			fileName.append("RQ_");
			fileName.append(jobNumber);
			fileName.append("_");
			if (!ddl) {
				fileName.append(requestState.getFunction().replace("*", ""));
				fileName.append("_");
				try {
					fileName.append(requestState.getRequestCode().replace("*", ""));
					fileName.append("_");
				} catch (Exception e) {
				}
			} else {
				fileName.append("DDL_");
				fileName.append(requestState.getRequestCode().replace("*", ""));
				fileName.append("_");
			}

		} else {
			fileName.append("RP_");
			fileName.append(jobNumber);
			fileName.append("_");
			if (!ddl) {
				fileName.append(requestState.getFunction().replace("*", ""));
				fileName.append("_");
				try {
					String rc = requestState.getRequestCode();
					fileName.append(rc.replace("*", ""));
					fileName.append("_");
				} catch (Exception e) {
				}
			} else {
				fileName.append("DDL_");
				String rc = requestState.getRequestCode();
				fileName.append(rc.replace("*", ""));
				fileName.append("_");
			}
		}
		fileName.append(System.currentTimeMillis());
		fileName.append(".xml");
		return fileName.toString();
	}

	public void addToLoginStatus(String loginStatus) {
		this.loginStatus = this.loginStatus + loginStatus;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public boolean loginStatusContains(String charSequence) {
		if (this.loginStatus.contains(charSequence)) {
			return true;
		} else {
			return false;
		}
	}

	public void clearLoginStatus() {
		this.loginStatus = "";
	}

	// public Map<String, Vector<MiddlewareException>> getExceptions() {
	// if (exceptions == null) {
	// exceptions = new HashMap<String, Vector<MiddlewareException>>();
	// }
	// return exceptions;
	// }

	// public void addException(String jobNumber, MiddlewareException exception)
	// {
	// /*
	// * if same exception already exists but no id is registered filter it
	// * out
	// */
	// Vector<MiddlewareException> exceptions = getExceptions().get(jobNumber);
	// if (exceptions != null && !exceptions.isEmpty()) {
	// if (exception.getId() == null || "".equals(exception.getId())) {
	// for (MiddlewareException middlewareException : exceptions) {
	// if (exception.getMessage().equals(
	// middlewareException.getMessage())) {
	// return;
	// }
	// }
	// }
	// getExceptions().get(jobNumber).add(exception);
	// } else {
	// Vector<MiddlewareException> middlewareExceptions = new
	// Vector<MiddlewareException>();
	// middlewareExceptions.add(exception);
	// getExceptions().put(jobNumber, middlewareExceptions);
	// }
	// }

	public void addException(MiddlewareException exception) {
		/*
		 * if same exception already exists but no id is registered filter it
		 * out
		 */
		if (exception.getId() == null || "".equals(exception.getId())) {
			for (MiddlewareException mwe : getExceptions()) {
				if (exception.getMessage().equals(mwe.getMessage())) {
					return;
				}
			}
		}
		getExceptions().add(exception);
	}

	public Vector<MiddlewareException> getExceptions() {
		if (this.exceptions == null) {
			this.exceptions = new Vector<MiddlewareException>();
		}
		return this.exceptions;
	}

	public void removeExceptions() {
		this.exceptions.removeAllElements();
	}

	public void removeExceptions(String jobNumber) {
		this.exceptions.remove(jobNumber);
	}

	public ISeries getISeriesSession(String jobNumber) {
		return getUserSessions().get(jobNumber);
	}

	public String getUserLanguage() {
		if (this.userLanguage != null && !"".equals(this.userLanguage)) {
			return this.userLanguage;
		}
		return "E";
	}

	public boolean plausiMdcmsUser() {
		String userId = this.mdcmsUser.getUserId();

		if (userId != null && !"".equals(userId)) {
			return true;
		}
		return false;
	}

	private void showGC() {
		List<GarbageCollectorMXBean> list = ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean bean : list) {
			System.out.println("Name: " + bean.getName());
			System.out.println("Number of collections: " + bean.getCollectionCount());
			System.out.println("Collection time: " + bean.getCollectionTime());
			System.out.println("Pool names");

			for (String name : bean.getMemoryPoolNames()) {
				System.out.println("\t" + name);
			}

			System.out.println();
		}
	}

	private void computeDateInfo() {
		String dateFormat = this.mdcmsUser.getDateFormat();
		/*
		 * day indexes
		 * 
		 * @dateFormat[0][0] -> startIndex
		 * 
		 * @dateFormat[0][1] -> endIndex
		 */
		dateFormatIndex[0][0] = dateFormat.indexOf("D");
		dateFormatIndex[0][1] = dateFormatIndex[0][0] + 2;
		LOG.info("dateFormatIndexDD " + dateFormatIndex[0][0] + " - " + dateFormatIndex[0][1]);
		/*
		 * month index
		 * 
		 * @dateFormat[1][0] -> startIndex
		 * 
		 * @dateFormat[1][1] -> endIndex
		 */
		dateFormatIndex[1][0] = dateFormat.indexOf("M");
		dateFormatIndex[1][1] = dateFormatIndex[1][0] + 2;
		LOG.info("dateFormatIndexMM " + dateFormatIndex[1][0] + " - " + dateFormatIndex[1][1]);
		/*
		 * year index
		 * 
		 * @dateFormat[2][0] -> startIndex
		 * 
		 * @dateFormat[2][1] -> endIndex
		 */
		dateFormatIndex[2][0] = dateFormat.indexOf("Y");
		dateFormatIndex[2][1] = dateFormatIndex[2][0] + 4;
		LOG.info("dateFormatIndexYYYY " + dateFormatIndex[2][0] + " - " + dateFormatIndex[2][1]);

		/*
		 * get the date separator
		 */
		if (dateFormatIndex[2][0] > dateFormatIndex[1][0]) {
			ApplUserBean.dateSeparator = dateFormat.substring(dateFormatIndex[2][0] - 1, dateFormatIndex[2][0]);
		} else {
			ApplUserBean.dateSeparator = dateFormat.substring(dateFormatIndex[1][0] - 1, dateFormatIndex[1][0]);
		}

		LOG.info("dateSeparator is: " + ApplUserBean.dateSeparator);

		computeDateIndex();

	}

	/*
	 * the positioning of the date parts
	 * 
	 * @dateIndex[0] -> 'dd' position
	 * 
	 * @dateIndex[1] -> 'mm' position
	 * 
	 * @dateIndex[2] -> 'yyyy' position
	 */
	private void computeDateIndex() {
		if (dateFormatIndex[0][0] < dateFormatIndex[1][0]) {
			if (dateFormatIndex[1][0] < dateFormatIndex[2][0]) {
				dateIndex[0] = 0;
				dateIndex[1] = 1;
				dateIndex[2] = 2;
				dateFormat = "DMY";
			} else {
				if (dateFormatIndex[0][0] < dateFormatIndex[2][0]) {
					dateIndex[0] = 0;
					dateIndex[1] = 2;
					dateIndex[2] = 1;
					dateFormat = "DYM";
				} else {
					dateIndex[0] = 1;
					dateIndex[1] = 2;
					dateIndex[2] = 0;
					dateFormat = "YDM";
				}
			}
		} else {
			if (dateFormatIndex[1][0] < dateFormatIndex[2][0]) {
				if (dateFormatIndex[0][0] < dateFormatIndex[2][0]) {
					dateIndex[0] = 1;
					dateIndex[1] = 0;
					dateIndex[2] = 2;
					dateFormat = "MDY";
				} else {
					dateIndex[0] = 2;
					dateIndex[1] = 0;
					dateIndex[2] = 1;
					dateFormat = "MYD";
				}
			} else {
				dateIndex[0] = 2;
				dateIndex[1] = 1;
				dateIndex[2] = 0;
				dateFormat = "YMD";
			}
		}
		LOG.info("dateIndexDD: " + dateIndex[0] + " / dateIndexMM: " + dateIndex[1] + " / dateIndexYYYY: "
				+ dateIndex[2]);
		LOG.info("dateFormat : " + dateFormat);
	}

	public boolean isFromChpass() {
		return fromChpass;
	}

	public void setFromChpass(boolean fromChpass) {
		this.fromChpass = fromChpass;
	}

	public String[] getFilenames() {
		return filenames;
	}

	public void setFilenames(String[] filenames) {
		this.filenames = filenames;
	}

	public String getUserId() {
		return mdcmsUser.getUserId();
	}

	public void setPassword(String newpassword) {
		mdcmsUser.setPassword(newpassword);
	}

	public void setUserId(String userId) {
		mdcmsUser.setUserId(userId);
	}

	/**
	 * @return the mdcmsUser
	 */
	public User getMdcmsUser() {
		return mdcmsUser;
	}

	/**
	 * @return the showtime
	 */
	public boolean isShowtime() {
		return showtime;
	}

	/**
	 * @return the dateFormatIndex
	 */
	public int[][] getDateFormatIndex() {
		return dateFormatIndex;
	}

	/**
	 * @return the dateIndex
	 */
	public int[] getDateIndex() {
		return dateIndex;
	}

	/**
	 * @return the dateSeparator
	 */
	public String getDateSeparator() {
		return dateSeparator;
	}

	/**
	 * @return the jsDateSeparator
	 */
	public String getJsDateSeparator() {
		String dateSeparatorForJavascript = GlobalBackingBeanManager.getInstance().getDateseparators()
				.get(dateSeparator);
		LOG.info("Date separator recognition for javascript: " + dateSeparatorForJavascript);
		return dateSeparatorForJavascript;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public String geti5DateFormat() {
		return mdcmsUser.getDateFormat();
	}

	public String getJsDateFormat() {
		String dateFormatForJavascript = GlobalBackingBeanManager.getInstance().getDateformats().get(dateFormat);
		LOG.info("Date format recognition for javascript: " + dateFormatForJavascript);
		return dateFormatForJavascript;
	}

	public int getDateIndexDD() {
		return dateIndex[0];
	}

	public int getDateIndexMM() {
		return dateIndex[1];
	}

	public int getDateIndexYYYY() {
		return dateIndex[2];
	}

	public int getFirstDayOfWeek() {
		// 'weekstart' : 1, // first day of week: 0-Su or 1-Mo
		return ApplicationConfigurationBean.getInstance().getFirstDayOfWeek().equals("Sunday") ? 0 : 1;
	}

	public String removeThread(String jobNumber, String thread) {
		String screen = null;

		State sessionLogoffState = new State(FUNCTION_THREADEND, thread);

		String xmlRequestString = null;
		try {
			xmlRequestString = RequestXml
					.generateXmlRequestString(new Operation(sessionLogoffState.getAsGlobalContainer()));

			XmlResponseObject xmlResponseObject = runMiddleware(jobNumber, sessionLogoffState, xmlRequestString, false);

			// XmlResponseObject responseObject = iSeries
			// .runMiddleware(xmlRequestString);

			Operation responseOperation = ResponseXml.generateOperation(xmlResponseObject.getXmlResponseString());
			screen = responseOperation.getContainer(CONTAINERTYPEGLOBAL).getField(SCREEN).getValue();
		} catch (XMLStreamException e) {
			LOG.fatal(e);
		} catch (IOException e) {
			LOG.fatal(e);
		} catch (Exception e) {
			LOG.fatal(e);
		}

		LOG.info("LOGOFF: screen: " + screen + " / " + thread);

		return screen;
	}

	public void setRequestBackingBean(IBackingBean requestBackingBean) {
		this.requestBackingBean = requestBackingBean;
	}

	public Operation getResponseOperation(String className, String jobNumber, boolean removeFromList) {

		Operation returnResponseOperation = null;

		if (jobNumber != null) {
			// if (className.equals(CLASS_NAME_RW0100)) {
			// returnResponseOperation =
			// getResponseOperationsRW0100().get(jobNumber);
			//
			// if (returnResponseOperation == null) {
			// returnResponseOperation =
			// GlobalBackingBeanManager.getInstance().getHelperOperation()
			// .get(jobNumber);
			//
			// GlobalBackingBeanManager.getInstance().getHelperOperation().remove(jobNumber);
			// }
			//
			// return returnResponseOperation;
			// } else {
			returnResponseOperation = getResponseOperations().get(jobNumber);
			// }
		}

		// if (className.startsWith("RW0101")) {
		// removeFromList = false;
		// }

		if (returnResponseOperation != null) {
			if (className.startsWith(returnResponseOperation.getScreenName())) {
				LOG.info("Got 100% right responseOperation for className && jobNumber: " + className);
				// if (removeFromList) {
				// getResponseOperations().remove(jobNumber);
				// }
				return returnResponseOperation;
			}
			LOG.info("No responseOperation found for className: " + className);
		}

		Map<String, Operation> responseOperations = getResponseOperations();
		Collection<Operation> operations = responseOperations.values();
		for (Operation operation : operations) {

			// screen name the same and oldest
			if (className.startsWith(operation.getScreenName())) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Got responseOperation for className/jobNumber/time: " + className + " / "
							+ operation.getJobNumber() + "/ " + operation.getTimeStored());
				}

				if (returnResponseOperation != null) {
					if (returnResponseOperation.getTimeStored() < operation.getTimeStored()) {
						if (LOG.isInfoEnabled()) {
							LOG.info("Time stored is newer for jobNumber: " + operation.getJobNumber());
						}
						returnResponseOperation = operation;
					}
				} else {
					returnResponseOperation = operation;
				}

				// if (removeFromList) {
				// getResponseOperations().remove(
				// responseOperation.getJobNumber());
				// }
			}

			// if (jobNumber != null &&
			// operation.getJobNumber().equals(jobNumber)) {
			// responseOperation = operation;
			// if (removeFromList) {
			// getResponseOperations().remove(jobNumber);
			// }
			// return responseOperation;
			// }
		}
		if (returnResponseOperation != null) {
			return returnResponseOperation;
		}

		Map<String, Operation> helperOperations = GlobalBackingBeanManager.getInstance().getHelperOperation();
		operations = helperOperations.values();
		for (Operation operation : operations) {
			if (className.startsWith(operation.getScreenName())) {
				returnResponseOperation = operation;
				LOG.info("Got responseOperation for className: " + className);
				GlobalBackingBeanManager.getInstance().getHelperOperation().remove(operation.getJobNumber());

				return returnResponseOperation;
			}
		}

		LOG.info("No responseOperation found for className/jobNumber: " + className + "/" + jobNumber);
		return null;
	}

	/**
	 * @return the responseOperation
	 */
	public Map<String, Operation> getResponseOperations() {
		if (this.responseOperations == null) {
			this.responseOperations = new HashMap<String, Operation>();
		}
		return this.responseOperations;
	}

	public Map<String, Operation> getResponseOperationsStore() {
		if (this.responseOperationsStore == null) {
			this.responseOperationsStore = new HashMap<String, Operation>();
		}
		return responseOperationsStore;
	}

	public Map<String, Operation> getResponseOperationsRW0100() {
		if (this.responseOperationsRW0100 == null) {
			this.responseOperationsRW0100 = new HashMap<String, Operation>();
		}
		return responseOperationsRW0100;
	}

	/**
	 * @return the metanavBean
	 */
	public MetanavBean getMetanavBean() {
		if (this.metanavBean == null) {
			this.metanavBean = new MetanavBean();
		}
		return metanavBean;
	}

	/**
	 * @param metanavBean
	 *            the metanavBean to set
	 */
	public void setMetanavBean(MetanavBean metanavBean) {
		this.metanavBean = metanavBean;
	}

	/**
	 * @return the userSessions
	 */
	public Map<String, ISeries> getUserSessions() {
		if (this.userSessions == null) {
			this.userSessions = new HashMap<String, ISeries>();
		}
		return userSessions;
	}

	/**
	 * @param userSessions
	 *            the userSessions to set
	 */
	public void setUserSessions(Map<String, ISeries> userSessions) {
		this.userSessions = userSessions;
	}

	/**
	 * @param userLanguage
	 *            the userLanguage to set
	 */
	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	/**
	 * @return the scrollPositionSave
	 */
	public Map<String, ScrollPositionInformation> getScrollPositionSave() {
		if (this.scrollPositionSave == null) {
			this.scrollPositionSave = new HashMap<String, ScrollPositionInformation>();
		}
		return this.scrollPositionSave;
	}

	/**
	 * @param scrollPositionSave
	 *            the scrollPositionSave to set
	 */
	public void setScrollPositionSave(Map<String, ScrollPositionInformation> scrollPositionSave) {
		this.scrollPositionSave = scrollPositionSave;
	}

	public void saveScrollPositionFor(String simpleName, String jobNumber, String scrollToXY) {
		getScrollPositionSave().put(simpleName, new ScrollPositionInformation(jobNumber, scrollToXY));

	}

	public String getScrollPositionSaveFor(String simpleName, String jobNumber) {
		String returnScrollPosition = "0,0";
		boolean found = false;
		if (this.scrollPositionSave != null) {
			for (String key : this.scrollPositionSave.keySet()) {
				if (key.equals(simpleName)) {
					ScrollPositionInformation spi = this.scrollPositionSave.get(key);
					if (spi.getJobNumber().equals(jobNumber)) {
						returnScrollPosition = spi.getScrollToXY();
						found = true;
					}
				}
			}
		}

		if (found) {
			this.scrollPositionSave = null;
		}

		return returnScrollPosition;
	}

	public void setCurrentJobNumber(String jobNumber) {
		this.currentJobNumber = jobNumber;
	}

	/**
	 * @return the currentJobNumber
	 */
	public String getCurrentJobNumber() {
		return currentJobNumber;
	}

	public boolean isWiderCssVisible() {
		if (this.cssUsed.equals(CSS_WIDER)) {
			return false;
		}
		return true;
	}

	public boolean isWideCssVisible() {
		if (this.cssUsed.equals(CSS_WIDE)) {
			return false;
		}
		return true;
	}

	public boolean isStandardCssVisible() {
		if (this.cssUsed.equals(CSS_STANDARD)) {
			return false;
		}
		return true;
	}

	public String getCssUsed() {
		return cssUsed;
	}

	public void setCssUsed(String cssUsed) {
		this.cssUsed = cssUsed;
	}

	public String getCssStandard() {
		return CSS_STANDARD;
	}

	public String getCssWide() {
		return CSS_WIDE;
	}

	public String getCssWider() {
		return CSS_WIDER;
	}

}
