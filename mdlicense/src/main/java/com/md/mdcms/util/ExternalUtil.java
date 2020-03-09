package com.md.mdcms.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.md.mdcms.backingbean.ApplicationConfigurationBean;

public final class ExternalUtil {

	/** private logger instance */
	private static final Log LOG = LogFactory.getLog(ExternalUtil.class);

	private static Hashtable<String, String> env;

	private ExternalUtil() {
		super();
	}

	static {
		env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ApplicationConfigurationBean
				.getInstance().getLdapserver());
		env.put(Context.SECURITY_PRINCIPAL, ApplicationConfigurationBean
				.getInstance().getLdapprinciple());
		env.put(Context.SECURITY_CREDENTIALS, EncodeDecodeUtil
				.decode(ApplicationConfigurationBean.getInstance()
						.getLdappassword()));
	}

	@SuppressWarnings("unchecked")
	public static String getSignonData(String userId, String requestData)
			throws NamingException {

		LOG.debug("entering ====>");
		String ldapsearchbase = ApplicationConfigurationBean.getInstance()
				.getLdapsearchbase();
		LOG.debug("ldapsearchbase : " + ldapsearchbase);
		String ldapprinciple = ApplicationConfigurationBean.getInstance()
				.getLdapprinciple();
		LOG.debug("ldapprinciple  : " + ldapprinciple);
		String ldapserver = ApplicationConfigurationBean.getInstance()
				.getLdapserver();
		LOG.debug("ldapserver     : " + ldapserver);

		SearchControls searchControl = new SearchControls();
		searchControl.setReturningAttributes(new String[] { requestData });
		searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String searchFilter = "(sAMAccountName=" + userId + ")";
		String resultData = null;
		InitialDirContext initialCtx = new InitialDirContext(env);
		NamingEnumeration answer = initialCtx.search(ldapsearchbase,
				searchFilter, searchControl);
		while (answer.hasMore()) {
			SearchResult result = (SearchResult) answer.next();
			Attributes attrs = (Attributes) result.getAttributes();
			for (NamingEnumeration ne = attrs.getAll(); ne.hasMore();) {
				Attribute attr = (Attribute) ne.next();
				resultData = (String) attr.get();
			}
		}
		return resultData;
	}
}