package com.md.mdcms.backingbean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.net.ssl.SSLHandshakeException;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.as400.access.IFSFile;
import com.md.mdcms.base.ApplicationHelper;
import com.md.mdcms.base.IConstants;
import com.md.mdcms.model.Person;
import com.md.mdcms.model.PhoneNumber;
import com.md.mdcms.model.User;
import com.md.mdcms.util.MdUtil;
import com.thoughtworks.xstream.XStream;

public class ApplicationConfigurationBean implements IConstants {

	private static ApplicationConfigurationBean instance = null;

	public static final String BEAN_NAME = "applicationConfiguration";

	private static final String TEXT_COFAULT_1 = "Corporate identity problem 1. Please verify contents of folder: /co/";
	private static final String TEXT_COFAULT_2 = "Corporate identity problem 2. Please verify contents of folder: /co/";
	private static final String TEXT_COFAULT_3 = "Corporate identity problem 3. Please verify contents of folder: /co/";
	private static boolean corporateIdentityIsValid = false;

	/** Log instance for this class. */
	private static final Log LOG = LogFactory.getLog(ApplicationConfigurationBean.class);

	// private static Properties systemProperties;

	private final static Vector<String> VALID_FIRSTDAYOFWEEK = new Vector<String>();

	static {
		VALID_FIRSTDAYOFWEEK.add("Sunday");
		VALID_FIRSTDAYOFWEEK.add("Monday");
	}

	private final static String DEFAULT_FIRSTDAYOFWEEK = "Monday";
	private final static String DEFAULT_CORPORATEIDENTITY = "md";

	/*
	 * former iSeriesProperties
	 */
	public static final String EXTERNAL = "external";
	public static final String ISERIES = "iseries";
	public static final String MDSEC = "mdsec";
	public static final String ALL_USER_TYPES = EXTERNAL + ISERIES + MDSEC;
	public static final String EXPIRED = "EXPIRED";

	private static final String itEnv = "SIT";
	private String version;

	private String client;
	private String userType;
	private String userUser;
	private String host;
	private String hostEnv;
	private String systemUser;
	private String systemPw;

	private String[] hosts;

	/*
	 * former iSeriesProperties
	 */

	private String appPropPrefix;
	private String environment;
	private Boolean ldapconverttocn;
	private String ldapserver;
	private String ldapprinciple;
	private String ldappassword;
	private String ldapsearchbase;
	private String dateFormat;
	private String timeSeparator;
	private String gmtOffsetForTS;
	private String locationForTS;
	private String firstDayOfWeek;
	private String ireflink;
	private String iref;
	private String logoff;
	private String contactLabel;
	private String contactLink;
	private String contactPhone;
	private String networkUserProviderClass;
	private String corporateIdentity;
	private String ldapDomainController;

	private String loginHelperClass;
	private String helpFileLocation;

	private Vector<String> sessionIds;

	private ApplicationConfigurationBean(ServletContext context) {
		super();
		Properties systemProperties = System.getProperties();

		if (LOG.isDebugEnabled()) {
			Enumeration<Object> emem = systemProperties.keys();
			while (emem.hasMoreElements()) {
				String key = (String) emem.nextElement();
				LOG.debug("Properties Key: " + key + " / value: " + systemProperties.get(key));
			}
		}

		setAppPropPrefix("mdt.");

		setClient("CUSTOM_W");
		setContactLabel("Contact for Assistance");
		setContactLink("http://www.midrangedynamics.com/contact/");
		setContactPhone("");
		setCorporateIdentity(DEFAULT_CORPORATEIDENTITY);

		setDateFormat("");

		setEnvironment("MD");

		setFirstDayOfWeek(DEFAULT_FIRSTDAYOFWEEK);

		setGmtOffsetForTS("+0200");

		setHost("mrdyn61.mdcms.ch;66.159.188.139");
		setHostEnv("");

		setIref("Ref");
		setIreflink("");

		setLdapconverttocn(false);
		setLdapDomainController("");
		setLdappassword("");
		setLdapprinciple("");
		setLdapsearchbase("");
		setLdapserver("");
		setLocationForTS("Zurich");
		setLogoff("http://www.midrangedynamics.com");

		setNetworkUserProviderClass("com.md.mdcms.base.MdNetworkUserProvider");

		setSystemPw("TURDTVM=");
		setSystemUser("mdconnect");

		setTimeSeparator("");

		setUserType("iseries");
		setUserUser("system");

		setVersion("");
	}

	private ApplicationConfigurationBean(ServletContext context, int number) {
		super();
		Properties systemProperties = System.getProperties();

		if (LOG.isDebugEnabled()) {
			Enumeration<Object> emem = systemProperties.keys();
			while (emem.hasMoreElements()) {
				String key = (String) emem.nextElement();
				System.out.println("Properties Key: " + key + " / value: " + systemProperties.get(key));
			}
		}

		String catalina_home = systemProperties.getProperty("catalina.home");
		StringBuffer applConfFilePath = new StringBuffer();
		applConfFilePath.append(catalina_home);
		applConfFilePath.append(File.separatorChar);
		applConfFilePath.append("webapps");
		applConfFilePath.append(context.getContextPath());
		applConfFilePath.append(File.separatorChar);
		applConfFilePath.append("WEB-INF");
		applConfFilePath.append(File.separatorChar);
		applConfFilePath.append("md-test-faces-config.xml");

		LOG.debug("ApplicationConfiguration file path: " + applConfFilePath.toString());

		setEnvironment("TESTING");

		Person person = new Person();
		person.setFirstname("René");
		person.setName("Unternährer");

		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setCode(2);
		phoneNumber.setNumber("076 575 22 33");

		person.setPhoneNumber(phoneNumber);

		XStream xstream = new XStream();

		File inConfigFile = new File(applConfFilePath.toString());
		if (inConfigFile.exists()) {
			try {
				Object obj = (ApplicationConfigurationBean) xstream.fromXML(inConfigFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ApplicationConfigurationBean applicationConfigurationBean =
		// (ApplicationConfigurationBean) xstream
		// .fromXML(inConfigFile);

		System.out.println(xstream.toXML(this));

		System.out.println(xstream.toXML(person));

		String xml = xstream.toXML(person);

		File outFile = new File("person.xml");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outFile);
			fos.write("?xml version=\"1.0\"?>".getBytes(("UTF-8")));
			byte[] bytes = xml.getBytes("UTF-8");
			fos.write(bytes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
					System.out.println("outfile path: " + outFile.getAbsolutePath());
				} catch (IOException e) {

				}
			}
		}

		// systemProperties = new Properties();
		// URL coPropsUrl = getCoPropertiesUrl();
		// if (coPropsUrl != null) {
		// LOG.info("URL of co properties file is: " + coPropsUrl.toString());
		// URLConnection connection;
		// try {
		// connection = coPropsUrl.openConnection();
		// systemProperties.load(connection.getInputStream());
		//
		// LOG.info("Loading systemProperties from connection: "
		// + coPropsUrl.toString());
		// Field[] fields = ApplicationConfigurationBean.class
		// .getDeclaredFields();
		// for (int i = 0; i < fields.length; i++) {
		// Field field = fields[i];
		// String key = field.getName();
		// String value = systemProperties.getProperty(key);
		// LOG.debug("Key: " + key + " / Value: " + value);
		// }
		// } catch (IOException e) {
		// LOG.info("Connection to url: " + coPropsUrl.toString()
		// + " can't be opened!", e);
		// }
		// }
	}

	public static ApplicationConfigurationBean getInstance(ServletContext context) {
		if (instance == null) {
			synchronized (ApplicationConfigurationBean.class) {
				if (instance == null) {
					instance = new ApplicationConfigurationBean(context);
				}
			}
		}
		return instance;
	}

	public static ApplicationConfigurationBean getInstance() {
		if (instance == null) {
			synchronized (ApplicationConfigurationBean.class) {
				if (instance == null) {
					instance = new ApplicationConfigurationBean(null);
				}
			}
		}
		return instance;
	}

	private URL getCoPropertiesUrl() {
		String corporateIdentity = getCorporateIdentity();
		String servletRequestUrl = ApplicationHelper.getServletRequestURL();
		URL url = null;
		BufferedReader br = null;
		try {
			url = new URL(servletRequestUrl + "co" + IFSFile.separatorChar + corporateIdentity + IFSFile.separatorChar
					+ "properties" + IFSFile.separatorChar + "systemProperties.txt");
			URLConnection connection = url.openConnection();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = br.readLine();
			if (line == null) {
				LOG.info("no systemProperties.txt file for corporateIdentity: " + corporateIdentity);
				return null;
			}
			br.close();
		} catch (MalformedURLException e) {
			LOG.fatal(e);
			return null;
		} catch (IOException e) {
			LOG.fatal(e);
			return null;
		}
		return url;
	}

	/*
	 * former iSeriesProperties
	 */
	public boolean isIseriesUser() {
		if (ISERIES.equals(getUserType())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isMdsecUser() {
		if (MDSEC.equals(getUserType())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isExternalUser() {
		if (EXTERNAL.equals(getUserType())) {
			return true;
		} else {
			return false;
		}
	}

	public User getSysUser() {
		User systemUser = new User(getSystemUser(), getSystemPw());
		return systemUser;
	}

	/**
	 * @return the client
	 */
	public String getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(String client) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "client", client);
		this.client = sysvar;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "userType", userType);
		this.userType = sysvar;
	}

	/**
	 * @return the userUser
	 */
	public String getUserUser() {
		return userUser;
	}

	/**
	 * @param userUser
	 *            the userUser to set
	 */
	public void setUserUser(String userUser) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "userUser", userUser);
		this.userUser = sysvar;
	}

	/**
	 * @return the helpFileLocation
	 */
	public String getHelpFileLocation() {
		return helpFileLocation;
	}

	/**
	 * @param helpFileLocation
	 *            the helpFileLocation to set
	 */
	public void setHelpFileLocation(String helpFileLocation) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "helpFileLocation", helpFileLocation);
		this.helpFileLocation = sysvar;
	}

	/**
	 * @return the loginHelper
	 */
	public String getLoginHelperClass() {
		return loginHelperClass;
	}

	/**
	 * @param loginHelper
	 *            the loginHelper to set
	 */
	public void setLoginHelperClass(String loginHelperClass) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "loginHelper", loginHelperClass);
		this.loginHelperClass = sysvar;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "host", host);
		this.host = sysvar;
	}

	/**
	 * @return the hostEnv
	 */
	public String getHostEnv() {
		if (hostEnv == null) {
			hostEnv = "";
		}
		return hostEnv;
	}

	/**
	 * @param hostEnv
	 *            the hostEnv to set
	 */
	public void setHostEnv(String hostEnv) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "hostEnv", hostEnv);
		this.hostEnv = sysvar;
	}

	/**
	 * @return the systemUser
	 */
	public String getSystemUser() {
		return systemUser;
	}

	/**
	 * @param systemUser
	 *            the systemUser to set
	 */
	public void setSystemUser(String systemUser) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "systemUser", systemUser);
		this.systemUser = sysvar;
	}

	/**
	 * @return the systemPw
	 */
	public String getSystemPw() {
		return systemPw;
	}

	/**
	 * @param systemPw
	 *            the systemPw to set
	 */
	public void setSystemPw(String systemPw) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "systemPw", systemPw);
		this.systemPw = sysvar;
	}

	/**
	 * @return the itenv
	 */
	public static String getItenv() {
		return itEnv;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the hosts
	 */
	public String[] getHosts() {
		if (this.hosts == null) {
			String host = getHost();
			if (host != null) {
				String[] hosts = host.split(";");
				this.hosts = hosts;
			}
		}
		return hosts;
	}

	public String getHosts(int addressCount) {
		String[] hosts = getHosts();
		if (hosts != null && hosts.length > addressCount) {
			return hosts[addressCount];
		}
		return null;
	}

	/**
	 * @return
	 */
	public boolean isNotHostAvailable() {
		String hosts = getHost();
		if (hosts == null || "".equals(hosts)) {
			return true;
		}
		return false;
	}

	/**
	 * @return
	 */
	public boolean isNotCorrectUserType() {
		if (ALL_USER_TYPES.contains(getUserType())) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public boolean hasNoSystemUserPassword() {
		String systemUser = getSystemUser();
		String encryptedPw = getSystemPw();
		if (LOG.isInfoEnabled()) {
			LOG.info("External verify sysUser and sysPw : " + systemUser + "/" + encryptedPw);
		}
		if (systemUser == null || "".equals(systemUser) || encryptedPw == null || "".equals(encryptedPw)) {
			return true;
		}
		return false;
	}

	public boolean getHasContactInfos() {
		if (!"".equals(getContactLabel()) || !"".equals(getContactPhone())) {
			return true;
		}
		return false;
	}

	public boolean getHasContactLink() {
		if (!"".equals(getContactLink())) {
			return true;
		}
		return false;
	}

	/**
	 * @return the appPropPrefix
	 */
	public String getAppPropPrefix() {
		if (this.appPropPrefix == null) {
			this.appPropPrefix = "";
		}
		return appPropPrefix;
	}

	/**
	 * @return the environment
	 */
	public String getEnvironment() {
		if (this.environment == null) {
			this.environment = "";
		}
		return environment;
	}

	/**
	 * @return the ireflink
	 */
	public String getIreflink() {
		if (this.ireflink == null) {
			this.ireflink = "";
		}
		return ireflink;
	}

	/**
	 * @return the iref
	 */
	public String getIref() {
		if (this.iref == null) {
			this.iref = "";
		}
		return iref;
	}

	/**
	 * @return the logoff
	 */
	public String getLogoff() {
		if (this.logoff == null) {
			this.logoff = "";
		}
		return logoff;
	}

	/**
	 * @return the locationForTS
	 */
	public String getLocationForTS() {
		if (locationForTS != null && !"".equals(locationForTS)) {
			return ("(" + locationForTS + ")");
		}
		return "";
	}

	/**
	 * @return the firstDayOfWeek
	 */
	public String getFirstDayOfWeek() {
		if (firstDayOfWeek != null && !"".equals(firstDayOfWeek)) {
			if (VALID_FIRSTDAYOFWEEK.contains(firstDayOfWeek)) {
				return firstDayOfWeek;
			} else {
				StringBuffer sb = new StringBuffer();
				sb.append("firstDayOfWeek provided in SystemProperties or in ApplicationConfiguration is not valid: ");
				sb.append(firstDayOfWeek);
				sb.append(" / Valid values are: ");
				for (Iterator<String> iterator = VALID_FIRSTDAYOFWEEK.iterator(); iterator.hasNext();) {
					sb.append(iterator.next());
				}
				sb.append(" / firstDayOfWeek will be set to " + DEFAULT_FIRSTDAYOFWEEK);
				LOG.error(sb.toString());
				firstDayOfWeek = DEFAULT_FIRSTDAYOFWEEK;
			}
		}
		firstDayOfWeek = DEFAULT_FIRSTDAYOFWEEK;
		return firstDayOfWeek;
	}

	public int getCalFirstDayOfWeek() {
		return getFirstDayOfWeek().equals(DEFAULT_FIRSTDAYOFWEEK) ? Calendar.MONDAY : Calendar.SUNDAY;
	}

	/**
	 * @return the ldapconverttocn
	 */
	public Boolean getLdapconverttocn() {
		return ldapconverttocn;
	}

	/**
	 * @return the ldapserver
	 */
	public String getLdapserver() {
		if (this.ldapserver == null) {
			this.ldapserver = "";
		}
		return ldapserver;
	}

	/**
	 * @return the ldapprinciple
	 */
	public String getLdapprinciple() {
		if (this.ldapprinciple == null) {
			this.ldapprinciple = "";
		}
		return ldapprinciple;
	}

	/**
	 * @return the ldappassword
	 */
	public String getLdappassword() {
		if (this.ldappassword == null) {
			this.ldappassword = "";
		}
		return ldappassword;
	}

	/**
	 * @return the ldapsearchbase
	 */
	public String getLdapsearchbase() {
		if (this.ldapsearchbase == null) {
			this.ldapsearchbase = "";
		}
		return ldapsearchbase;
	}

	/**
	 * @return the ldapDomainController
	 */
	public String getLdapDomainController() {
		if (this.ldapDomainController == null) {
			this.ldapDomainController = "";
		}
		return ldapDomainController;
	}

	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		if (this.dateFormat == null) {
			this.dateFormat = "";
		}
		return dateFormat;
	}

	/**
	 * @return the timeSeparator
	 */
	public String getTimeSeparator() {
		if (this.timeSeparator == null) {
			this.timeSeparator = "";
		}
		return timeSeparator;
	}

	/**
	 * @return the gmtOffsetForTS
	 */
	public String getGmtOffsetForTS() {
		return gmtOffsetForTS;
	}

	/**
	 * @return the contactLabel
	 */
	public String getContactLabel() {
		if (this.contactLabel == null) {
			this.contactLabel = "";
		}
		return contactLabel;
	}

	/**
	 * @return the contactLink
	 */
	public String getContactLink() {
		if (this.contactLink == null) {
			this.contactLink = "";
		}
		return contactLink;
	}

	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		if (this.contactPhone == null) {
			this.contactPhone = "";
		}
		return contactPhone;
	}

	/**
	 * @return the fullContextPath
	 */
	// public String getFullContextPath() {
	// if (this.fullContextPath == null)
	// this.fullContextPath = "";
	// return fullContextPath;
	// }

	/**
	 * @return the serverContextPath
	 */
	// public String getServerContextPath() {
	// if (this.serverContextPath == null) {
	// String fullContextPath = getFullContextPath();
	// if (!"".equals(fullContextPath)) {
	// /*
	// * 012345678 https://abc:8080/
	// */
	// int index = fullContextPath.indexOf("/", 8);
	// if (index > 0) {
	// this.serverContextPath = fullContextPath
	// .substring(0, index);
	// } else {
	// this.serverContextPath = fullContextPath;
	// }
	// } else {
	// this.serverContextPath = "";
	// }
	// }
	//
	// return serverContextPath;
	// }

	/**
	 * @return the networkUserProviderClass
	 */
	public String getNetworkUserProviderClass() {
		if (this.networkUserProviderClass == null) {
			this.networkUserProviderClass = "";
		}
		return networkUserProviderClass;
	}

	/**
	 * @return the corporateIdentity
	 */
	public String getCorporateIdentity() {
		if (!corporateIdentityIsValid) {
			if (corporateIdentity != null && !"".equals(corporateIdentity)) {
				corporateIdentityIsValid = true;

				/*
				 * has to be reworked
				 */
				// if (isValidCorporateIdentity(corporateIdentity)) {
				// corporateIdentityIsValid = true;
				// } else {
				// StringBuffer sb = new StringBuffer();
				// sb.append("corporateIdentity provided in SystemProperties or
				// in ApplicationConfiguration is not valid: ");
				// sb.append(corporateIdentity);
				// sb.append(" / the folders for corporate identity must be
				// provided with css, images and xhtml");
				// sb.append(" / firstDayOfWeek will be set to "
				// + DEFAULT_CORPORATEIDENTITY);
				// LOG.error(sb.toString());
				// corporateIdentity = DEFAULT_CORPORATEIDENTITY;
				// }
				/*
				 * has to be reworked
				 */
			} else {
				corporateIdentityIsValid = true;
				corporateIdentity = DEFAULT_CORPORATEIDENTITY;
			}
		}
		return corporateIdentity;
	}

	/**
	 * @param corporateIdentity2
	 * @return
	 */
	private boolean isValidCorporateIdentity(String corporateIdentity) {
		// http://localhost:8080/mdWorkflowJSF12/
		// try {
		String servletRequestUrl = ApplicationHelper.getServletRequestURL();
		BufferedReader br = null;
		try {
			URL url = new URL(servletRequestUrl + "co" + IFSFile.separatorChar + corporateIdentity
					+ IFSFile.separatorChar + "css" + IFSFile.separatorChar + "base.css");
			URLConnection connection = url.openConnection();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = br.readLine();
			if (line == null) {
				LOG.fatal("main.css missing for corporateIdentity: " + corporateIdentity
						+ " / create files in co folder or correct in conf-faces-config.xml");
				return false;
			}
			url = new URL(servletRequestUrl + "co" + IFSFile.separatorChar + corporateIdentity + IFSFile.separatorChar
					+ "xhtml" + IFSFile.separatorChar + "main.xhtml");
			connection = url.openConnection();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			line = br.readLine();
			if (line == null) {
				LOG.fatal("main.xhtml missing for corporateIdentity: " + corporateIdentity
						+ " / create files in co folder or correct entry in conf-faces-config.xml");
				return false;
			}
		} catch (MalformedURLException e) {
			LOG.fatal(e);
			// FacesContext context = FacesContext.getCurrentInstance();
			// MiddlewareException mExc = new MiddlewareException("", "30",
			// TEXT_COFAULT_1 + corporateIdentity + ".");
			// ApplicationHelper.getUserBean().addException(mExc);
			// FacesMessage facesMessage = new FacesMessage(
			// mExc.getFacesSeverity(), mExc.getMessage(),
			// mExc.getMessage());
			// context.addMessage(mExc.getId(), facesMessage);
			return false;
		} catch (IOException e) {
			LOG.fatal(e);
			if (e instanceof SSLHandshakeException) {
				// FacesContext context = FacesContext.getCurrentInstance();
				// MiddlewareException mExc = new MiddlewareException("", "10",
				// TEXT_COFAULT_3 + corporateIdentity + ".");
				// ApplicationHelper.getUserBean().addException(mExc);
				// FacesMessage facesMessage = new FacesMessage(
				// mExc.getFacesSeverity(), mExc.getMessage(),
				// mExc.getMessage());
				// context.addMessage(mExc.getId(), facesMessage);
				return true;
			} else {
				// FacesContext context = FacesContext.getCurrentInstance();
				// MiddlewareException mExc = new MiddlewareException("", "30",
				// TEXT_COFAULT_2 + corporateIdentity + ".");
				// ApplicationHelper.getUserBean().addException(mExc);
				// FacesMessage facesMessage = new FacesMessage(
				// mExc.getFacesSeverity(), mExc.getMessage(),
				// mExc.getMessage());
				// context.addMessage(mExc.getId(), facesMessage);
				return false;
			}
		}
		return true;
	}

	/**
	 * @param appPropPrefix
	 *            the appPropPrefix to set
	 */
	public void setAppPropPrefix(String appPropPrefix) {
		this.appPropPrefix = appPropPrefix;
	}

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(String environment) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "environment", environment);
		this.environment = sysvar;
	}

	/**
	 * @param ldapconverttocn
	 *            the ldapconverttocn to set
	 */
	public void setLdapconverttocn(Boolean ldapconverttocn) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "ldapconverttocn", ldapconverttocn ? TRUE : FALSE);
		this.ldapconverttocn = Boolean.valueOf(sysvar);
	}

	/**
	 * @param ldapserver
	 *            the ldapserver to set
	 */
	public void setLdapserver(String ldapserver) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "ldapserver", ldapserver);
		this.ldapserver = sysvar;
	}

	/**
	 * @param ldapprinciple
	 *            the ldapprinciple to set
	 */
	public void setLdapprinciple(String ldapprinciple) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "ldapprinciple", ldapprinciple);
		this.ldapprinciple = sysvar;
	}

	/**
	 * @param ldappassword
	 *            the ldappassword to set
	 */
	public void setLdappassword(String ldappassword) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "ldappassword", ldappassword);
		this.ldappassword = sysvar;
	}

	/**
	 * @param ldapsearchbase
	 *            the ldapsearchbase to set
	 */
	public void setLdapsearchbase(String ldapsearchbase) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "ldapsearchbase", ldapsearchbase);
		this.ldapsearchbase = sysvar;
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "dateFormat", dateFormat);
		this.dateFormat = sysvar;
	}

	/**
	 * @param timeSeparator
	 *            the timeSeparator to set
	 */
	public void setTimeSeparator(String timeSeparator) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "timeSeparator", timeSeparator);
		this.timeSeparator = sysvar;
	}

	/**
	 * @param gmtOffsetForTS
	 *            the gmtOffsetForTS to set
	 */
	public void setGmtOffsetForTS(String gmtOffsetForTS) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "gmtOffsetForTS", gmtOffsetForTS);
		this.gmtOffsetForTS = sysvar;
	}

	/**
	 * @param contactLabel
	 *            the contactLabel to set
	 */
	public void setContactLabel(String contactLabel) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "contactLabel", contactLabel);
		this.contactLabel = sysvar;
	}

	/**
	 * @param contactLink
	 *            the contactLink to set
	 */
	public void setContactLink(String contactLink) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "contactLink", contactLink);
		this.contactLink = sysvar;
	}

	/**
	 * @param contactPhone
	 *            the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "contactPhone", contactPhone);
		this.contactPhone = sysvar;
	}

	/**
	 * @param fullContextPath
	 *            the fullContextPath to set
	 */
	// public void setFullContextPath(String fullContextPath) {
	// String sysvar = MdUtil.getFindProperty(getAppPropPrefix()
	// + "fullContextPath", fullContextPath);
	// this.fullContextPath = sysvar;
	// }

	/**
	 * @param serverContextPath
	 *            the serverContextPath to set
	 */
	// public void setServerContextPath(String serverContextPath) {
	// String sysvar = MdUtil.getFindProperty(getAppPropPrefix()
	// + "serverContextPath", serverContextPath);
	// this.serverContextPath = sysvar;
	// }

	/**
	 * @param networkUserProviderClass
	 *            the networkUserProviderClass to set
	 */
	public void setNetworkUserProviderClass(String networkUserProviderClass) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "networkUserProviderClass",
				networkUserProviderClass);
		this.networkUserProviderClass = sysvar;
	}

	/**
	 * @param corporateIdentity
	 *            the corporateIdentity to set
	 */
	public void setCorporateIdentity(String corporateIdentity) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "corporateIdentity", corporateIdentity);
		this.corporateIdentity = sysvar;
	}

	/**
	 * @param locationForTS
	 *            the locationForTS to set
	 */
	public void setLocationForTS(String locationForTS) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "locationForTS", locationForTS);
		this.locationForTS = sysvar;
	}

	/**
	 * @param firstDayOfWeek
	 *            the firstDayOfWeek to set
	 */
	public void setFirstDayOfWeek(String firstDayOfWeek) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "firstDayOfWeek", firstDayOfWeek);
		this.firstDayOfWeek = sysvar;
	}

	/**
	 * @param ireflink
	 *            the ireflink to set
	 */
	public void setIreflink(String ireflink) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "ireflink", ireflink);
		this.ireflink = sysvar;
	}

	/**
	 * @param iref
	 *            the iref to set
	 */
	public void setIref(String iref) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "iref", iref);
		this.iref = sysvar;
	}

	/**
	 * @param logoff
	 *            the logoff to set
	 */
	public void setLogoff(String logoff) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "logoff", logoff);
		this.logoff = sysvar;
	}

	/**
	 * @param ldapDomainController
	 *            the ldapDomainController to set
	 */
	public void setLdapDomainController(String ldapDomainController) {
		String sysvar = MdUtil.getFindProperty(getAppPropPrefix() + "ldapDomainController", ldapDomainController);
		this.ldapDomainController = sysvar;
	}

	/**
	 * @return the sessionIds
	 */
	public Vector<String> getSessionIds() {
		if (sessionIds == null) {
			sessionIds = new Vector<String>();
		}
		return sessionIds;
	}

}
