package com.md.mdcms.base;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.ApplicationConfigurationBean;
import com.md.mdcms.util.ExternalUtil;

public class MdNetworkUserProvider implements INetworkUserProvider {

	/** private logger instance */
	private static final Log LOG = LogFactory.getLog(MdNetworkUserProvider.class);

	private String networkUserId = "";
	private String networkUserName = "";

	public MdNetworkUserProvider() {
		super();
		initNetworkUserIdAndName();
	}

	public void initNetworkUserIdAndName() {
		LOG.info("Network user id is null or blank - we get it from system");
		// ExternalContext exContext = FacesContext.getCurrentInstance()
		// .getExternalContext();
		// String remoteUser = exContext.getRemoteUser();

		// LOG.info("Remote user: " + remoteUser);
		// String ldapDomainController = ApplicationConfigurationBean
		// .getInstance().getLdapDomainController();
		// LOG.info("ldapDomainController: " + ldapDomainController);
		// int ldapDomainControllerLength = ldapDomainController.length() + 1;
		// if (remoteUser != null
		// && remoteUser.toLowerCase().startsWith(ldapDomainController)
		// && remoteUser.length() > ldapDomainControllerLength) {
		// this.networkUserId = remoteUser
		// .substring(ldapDomainControllerLength);
		// } else {
		// this.networkUserId = remoteUser;
		// }
		LOG.info("networkUserId obtained is : " + this.networkUserId);

		if (ApplicationConfigurationBean.getInstance().getLdapconverttocn()) {
			try {
				LOG.info("Get the displayName");
				this.networkUserName = ExternalUtil.getSignonData(this.networkUserId, "displayName");
				LOG.info("networkName: " + this.networkUserName);
			} catch (NamingException e) {
				LOG.warn("ExternalUtil is unable to get the displayName");
			}
		} else {
			this.networkUserName = this.networkUserId;
			LOG.info("networkName is also networkUserid: " + this.networkUserName);
		}
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