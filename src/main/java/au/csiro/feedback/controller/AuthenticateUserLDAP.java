package au.csiro.feedback.controller;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.*;

import java.util.Hashtable;

public class AuthenticateUserLDAP {

    String userName = "dev087";
	String pwd ="";
	static String LDAP_SERVER = "pool.ldap.csiro.au";
	static String LDAP_SERVER_PORT = "389";
	static String LDAP_BASE_DN = "dc=nexus,dc=csiro,dc=au";
	static String LDAP_BIND_DN = "nexus" + "\\" + userName; 
	static String LDAP_BIND_PASSWORD = pwd; 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		validateLogin(userName, pwd); //add iden, pwd
	}

	public static Boolean validateLogin(String userName, String userPassword) {
		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://" + LDAP_SERVER + ":" + LDAP_SERVER_PORT + "/" + LDAP_BASE_DN);

		// To get rid of the PartialResultException when using Active Directory
		env.put(Context.REFERRAL, "follow");

		// Needed for the Bind (User Authorized to Query the LDAP server)
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, LDAP_BIND_DN);
		env.put(Context.SECURITY_CREDENTIALS, LDAP_BIND_PASSWORD);

		DirContext ctx;
		try {
			ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}

		NamingEnumeration<SearchResult> results = null;

		try {
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search
																	// Entire
																	// Subtree
			controls.setCountLimit(1); // Sets the maximum number of entries to
										// be returned as a result of the search
			controls.setTimeLimit(5000); // Sets the time limit of these
											// SearchControls in milliseconds

			String searchString = "(&(objectCategory=user)(sAMAccountName=" + userName + "))";

			results = ctx.search("", searchString, controls);

			if (results.hasMore()) {

				SearchResult result = (SearchResult) results.next();
				Attributes attrs = result.getAttributes();
				for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
					Attribute attr = (Attribute) ae.next();
					System.out.print("attribute: " + attr.getID());

					/* print each value */
					for (NamingEnumeration e = attr.getAll(); e.hasMore(); System.out.println(" , value: " + e.next()));
				}

				Attribute dnAttr = attrs.get("distinguishedName");
				String dn = (String) dnAttr.get();
				System.out.println(" Person Common Name = " + attrs.get("cn"));
				System.out.println(" Person Display Name = " + attrs.get("displayName"));
				System.out.println(" Person logonhours = " + attrs.get("logonhours"));
				System.out.println(" Person MemberOf = " + attrs.get("memberOf"));

				// User Exists, Validate the Password

				env.put(Context.SECURITY_PRINCIPAL, dn);
				env.put(Context.SECURITY_CREDENTIALS, userPassword);

				new InitialDirContext(env); // Exception will be thrown
											// onInvalid case

				return true;
			} else
				return false;

		} catch (AuthenticationException e) { // Invalid Login

			return false;
		} catch (NameNotFoundException e) { // The base context was not found.

			return false;
		} catch (SizeLimitExceededException e) {
			throw new RuntimeException("LDAP Query Limit Exceeded, adjust the query to bring back less records", e);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} finally {

			if (results != null) {
				try {
					results.close();
				} catch (Exception e) { /* Do Nothing */
				}
			}

			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) { /* Do Nothing */
				}
			}
		}
	}

}
