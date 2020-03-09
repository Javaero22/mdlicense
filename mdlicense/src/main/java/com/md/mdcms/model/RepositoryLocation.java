package com.md.mdcms.model;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;

import com.md.mdcms.backingbean.ApplicationConfigurationBean;

public class RepositoryLocation implements Serializable {

	private static final long serialVersionUID = -7472520368524856137L;

	@Attribute
	private String host;

	@Attribute(required = false)
	private String port;

	@Attribute(required = false)
	private String environment;

	@Attribute(required = false)
	private String nickname;

	@Attribute(required = false)
	private String proxyServer;

	@Attribute(required = false)
	private String proxyServerPort;

	private String version;

	private String timeSeparator;

	public RepositoryLocation() {
		super();
	}

	public RepositoryLocation(String host, String port, String env,
			String nickname, String proxyServer, String proxyPort) {
		super();
		this.host = host;
		this.port = port;
		this.environment = env;
		this.nickname = nickname;
		this.proxyServer = proxyServer;
		this.proxyServerPort = proxyPort;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (getNickname() != null && !"".equals(getNickname())) {
			sb.append(getNickname());
			sb.append("-");
		}
		sb.append(getHost());
		if (!"".equals(getEnvironment())) {
			sb.append("-");
			sb.append(getEnvironment());
		}

		return sb.toString();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProxyServer() {
		return proxyServer;
	}

	public void setProxyServer(String proxyServer) {
		this.proxyServer = proxyServer;
	}

	public String getProxyServerPort() {
		return proxyServerPort;
	}

	public void setProxyServerPort(String proxyServerPort) {
		this.proxyServerPort = proxyServerPort;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTimeSeparator() {
		return timeSeparator;
	}

	public void setTimeSeparator(String timeSeparator) {
		this.timeSeparator = timeSeparator;
	}

	/**
	 * @return
	 */
	public static RepositoryLocation getDefault() {
		RepositoryLocation repLoc = new RepositoryLocation();
		repLoc.setEnvironment(ApplicationConfigurationBean.getInstance()
				.getHostEnv());
		repLoc.setHost(ApplicationConfigurationBean.getInstance().getHost());
		repLoc.setProxyServer("");
		repLoc.setProxyServerPort("");
		return repLoc;
	}

	public static RepositoryLocation getAddress(int addressCount) {
		String host = ApplicationConfigurationBean.getInstance().getHosts(
				addressCount);
		if (host != null) {
			RepositoryLocation repLoc = new RepositoryLocation();
			repLoc.setHost(host);
			repLoc.setEnvironment(ApplicationConfigurationBean.getInstance()
					.getHostEnv());
			repLoc.setProxyServer("");
			repLoc.setProxyServerPort("");
			return repLoc;
		}
		return null;
	}

}
