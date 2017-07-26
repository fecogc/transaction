package com.test.security;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent>
{
	private final Logger log = Logger.getLogger(AuthSuccessEventListener.class);
	
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent e)
	{
		final WebAuthenticationDetails webAuth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
		
		if(webAuth != null)
			log.debug("Login Success, User: " + e.getAuthentication().getName() + ", ip: " + webAuth.getRemoteAddress());
		
	}

}
