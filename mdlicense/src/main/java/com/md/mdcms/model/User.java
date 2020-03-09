package com.md.mdcms.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.md.mdcms.backingbean.ApplicationConfigurationBean;
import com.md.mdcms.util.EncodeDecodeUtil;

@Root(name = "repositoryUser")
public class User implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 9121799150794068847L;

	@Attribute
	private String userId;

	private String networkUserId;

	private String networkUserName;

	@Attribute
	private String password;

	@Element
	private RepositoryLocation repositoryLocation;

	private Integer sessionId;

	private String serverEnvironment;

	private String userType;

	private String dateFormat;

	private String timeSep;

	private String timeFormatSample;

	public User() {
		super();
	}

	public User(User user) {
		super();
		this.userId = user.getUserId();
		this.password = user.getPassword();
		this.repositoryLocation = user.getRepositoryLocation();
	}

	public User(String userId, String password,
			RepositoryLocation repositoryLocation) {
		super();
		this.userId = userId;
		this.password = password;
		this.repositoryLocation = repositoryLocation;
	}

	public String getTimeFormatSample() {
		if (this.timeFormatSample == null) {
			String tf = "07;22;22";
			this.timeFormatSample = tf.replace(";", getTimeSep());
		}
		return timeFormatSample;
	}

	/**
	 * @param systemUser
	 * @param systemPw
	 */
	public User(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return EncodeDecodeUtil.decode(password);
	}

	public void setPassword(String password) {
		this.password = EncodeDecodeUtil.encode(password);
	}

	public RepositoryLocation getRepositoryLocation() {
		return repositoryLocation;
	}

	public void setRepositoryLocation(RepositoryLocation repositoryLocation) {
		this.repositoryLocation = repositoryLocation;
	}

	public Integer toSessionId() {
		return this.hashCode();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (this.repositoryLocation.getNickname() != null
				&& !"".equals(this.repositoryLocation.getNickname())) {
			sb.append(this.repositoryLocation.getNickname());
			sb.append("-");
		}
		sb.append(this.repositoryLocation.getHost());
		if (!"".equals(this.repositoryLocation.getPort())) {
			sb.append(":");
			sb.append(this.repositoryLocation.getPort());
		}
		if (!"".equals(this.repositoryLocation.getEnvironment())) {
			sb.append("-");
			sb.append(this.repositoryLocation.getEnvironment());
		}
		sb.append("@");
		sb.append(this.userId);

		return sb.toString();
	}

	/**
	 * @return
	 */
	public String getUserIdAndPw() {
		StringBuffer sb = new StringBuffer(20);
		sb.append(getUserId());
		int userIdLength = getUserId().length();
		for (int i = 10 - userIdLength; i > 0; i--) {
			sb.append(" ");
		}
		sb.append(getPassword());
		return sb.toString();
	}

	public boolean isIseriesUser() {
		if (ApplicationConfigurationBean.ISERIES.equals(getUserType())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isMdsecUser() {
		if (ApplicationConfigurationBean.MDSEC.equals(getUserType())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isExternalUser() {
		if (ApplicationConfigurationBean.EXTERNAL.equals(getUserType())) {
			return true;
		} else {
			return false;
		}
	}

	public static User getFromFields(List<Field> fields) {
		User user = new User();
		RepositoryLocation repLoc = new RepositoryLocation();
		for (Iterator<Field> fieldIt = fields.iterator(); fieldIt.hasNext();) {
			Field field = (Field) fieldIt.next();
			if ("userId".equals(field.getId()))
				user.setUserId(field.getValue());
			if ("password".equals(field.getId()))
				user.setPassword(field.getValue());
			if ("repositoryLocation.host".equals(field.getId()))
				repLoc.setHost(field.getValue());
			if ("repositoryLocation.nickname".equals(field.getId()))
				repLoc.setNickname(field.getValue());
			if ("repositoryLocation.port".equals(field.getId())) {
				repLoc.setPort(field.getValue());
			}
			if ("repositoryLocation.environment".equals(field.getId()))
				repLoc.setEnvironment(field.getValue());
			if ("repositoryLocation.proxyServer".equals(field.getId()))
				repLoc.setProxyServer(field.getValue());
			if ("repositoryLocation.proxyServerPort".equals(field.getId()))
				repLoc.setProxyServerPort(field.getValue());
		}

		user.setRepositoryLocation(repLoc);
		return user;
	}

	public static boolean compareOk(User userToChange, User user) {
		if (userToChange.getUserId().equals(user.getUserId())
				&& userToChange.getRepositoryLocation().getNickname()
						.equals(user.getRepositoryLocation().getNickname())
				&& userToChange.getRepositoryLocation().getHost()
						.equals(user.getRepositoryLocation().getHost())
				&& userToChange.getRepositoryLocation().getEnvironment()
						.equals(user.getRepositoryLocation().getEnvironment())
				&& userToChange.getRepositoryLocation().getPort()
						.equals(user.getRepositoryLocation().getPort())
				&& userToChange.getRepositoryLocation().getProxyServer()
						.equals(user.getRepositoryLocation().getProxyServer())
				&& userToChange
						.getRepositoryLocation()
						.getProxyServerPort()
						.equals(user.getRepositoryLocation()
								.getProxyServerPort()))
			return true;
		else
			return false;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.password = encryptedPassword;

	}

	/**
	 * @param sessionId
	 */
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the serverEnvironment
	 */
	public String getServerEnvironment() {
		return serverEnvironment;
	}

	/**
	 * @param serverEnvironment
	 *            the serverEnvironment to set
	 */
	public void setServerEnvironment(String serverEnvironment) {
		this.serverEnvironment = serverEnvironment;
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
		this.userType = userType;
	}

	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @return the timeSep
	 */
	public String getTimeSep() {
		return timeSep;
	}

	/**
	 * @param timeSep
	 *            the timeSep to set
	 */
	public void setTimeSep(String timeSep) {
		this.timeSep = timeSep;
	}

	/**
	 * @return the sessionId
	 */
	public Integer getSessionId() {
		return sessionId;
	}

	/**
	 * @return the networkUserId
	 */
	public String getNetworkUserId() {
		return networkUserId;
	}

	/**
	 * @param networkUserId
	 *            the networkUserId to set
	 */
	public void setNetworkUserId(String networkUserId) {
		this.networkUserId = networkUserId;
	}

	/**
	 * @return the networkUserName
	 */
	public String getNetworkUserName() {
		return networkUserName;
	}

	/**
	 * @param networkUserName
	 *            the networkUserName to set
	 */
	public void setNetworkUserName(String networkUserName) {
		this.networkUserName = networkUserName;
	}

}
