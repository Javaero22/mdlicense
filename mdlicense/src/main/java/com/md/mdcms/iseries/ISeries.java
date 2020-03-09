package com.md.mdcms.iseries;

import java.beans.PropertyVetoException;
import java.io.IOException;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400SecurityException;
import com.md.mdcms.model.User;
import com.md.mdcms.model.XmlResponseObject;

public interface ISeries {

	String initialize();

	XmlResponseObject runMiddleware(String xmlRequestString);

	String getUserLanguage();

	void setUserLanguage(String newLanguage);

	String getUserPID();

	void close();

	String changePassword(String oldPassword, String newPassword);

	String changePasswordMdsec(String oldPassword, String newPassword);

	String getJobNumber();

	String getSessionKey();

	void open(User mdcmsUser) throws PropertyVetoException,
			AS400SecurityException, IOException;

	String getDateFormat();

	String getTimeSep();

	String getBuildDate();

	AS400 getAs400();

}
