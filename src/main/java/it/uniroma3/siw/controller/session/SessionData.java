package it.uniroma3.siw.controller.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;

/**
 * SessionData is an interface to save and retrieve specific objects from the current Session
 * It is mainly used to store the currently logged User and her Credentials
 * @author claud
 *
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS) //this object is going to be istantiated in each User's session
//his scope is limited to the session
public class SessionData {

	/**
	 * Currently logged User
	 */
	private User user;

	/**
	 * Credentials for the currently logged User
	 */
	private Credentials credentials;

	@Autowired
	private  CredentialsService credentialsService;


	/**
	 * Retrieve from Session the credentials for the currently logged user
	 * If they are not store in Session already, retrieve them from the SecurityContext and from the DB
	 * and store them in session
	 * @return the retrieved Credentials for the currently logged user
	 */
	public Credentials getLoggedCredentials() {
		if(this.credentials == null) this.update();
		return this.credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	/**
	 * Retrieve from Session the user for the currently logged user
	 * If they are not store in Session already, retrieve them from the SecurityContext and from the DB
	 * and store them in session
	 * @return the retrieved user for the currently logged user
	 */
	public User getLoggedUser() {
		if(this.user == null) this.update();
		return this.user;
	}

	public void setLoggedUser(User user) {
		this.user = user;
	}


	/**
	 * Store the Credentials and User objects for the currently logged user in session
	 */
	public void update() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUserDetails = (UserDetails) obj;

		this.credentials = this.credentialsService.getCredentials(loggedUserDetails.getUsername());
		this.credentials.setPassword("[PROTECTED]");
		this.user = this.credentials.getUser();
	}

}
