package com.md.mdcms.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.ApplicationConfigurationBean;
import com.md.mdcms.backingbean.StartConfigurationBean;

public class MdDummyNetworkUserProvider implements INetworkUserProvider, IConstants {

	/** private logger instance */
	private static final Log LOG = LogFactory
			.getLog(MdDummyNetworkUserProvider.class);

	private String networkUserId = "";
	private String networkUserName = "";

	public MdDummyNetworkUserProvider() {
		super();
		initNetworkUserIdAndName();
	}

	public void initNetworkUserIdAndName() {

		/*
		 * if we are on test profile we get the external user from file
		 */
		if (StartConfigurationBean.getInstance().isTest()) {

			LOG.info("test defaulttechnicaluser profile -> get user/pw from file");

			File propertiesFile = null;
			try {
				Properties applicationProperties = new Properties();
				propertiesFile = new File(StartConfigurationBean.getInstance()
						.getXmlfilepath() + MD_WORKFLOW_PROPERTIES);

				applicationProperties.load(new FileInputStream(propertiesFile));

				String env = ApplicationConfigurationBean.getInstance()
						.getHostEnv();

				if (env == null) {
					env = "";
				}

				String defaultUserString = applicationProperties
						.getProperty("defaulttechnicaluser");

				if (defaultUserString != null && !"".equals(defaultUserString)) {

					String[] userArgs = defaultUserString.split(";");
					setNetworkUserId(userArgs[0]);
					setNetworkUserName(userArgs[1]);

					LOG.info("defaulttechnicaluser set from file: "
							+ userArgs[0] + " / " + userArgs[1]);
				}
			} catch (Exception e) {
				LOG.info(MD_WORKFLOW_PROPERTIES + " not found: path /  "
						+ propertiesFile.getAbsolutePath());
				setNetworkUserId("dummy");
				setNetworkUserName("dummy");
			}
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