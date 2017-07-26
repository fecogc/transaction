package com.test.security;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthFailureEventListener implements ApplicationListener<AbstractAuthenticationFailureEvent>
{

	private final Logger log = Logger.getLogger(AuthFailureEventListener.class);

	@Override
	public void onApplicationEvent(AbstractAuthenticationFailureEvent e)
	{
		final Authentication auth = e.getAuthentication();

		if (auth != null)
		{
			final WebAuthenticationDetails webAuth = (WebAuthenticationDetails) e
					.getAuthentication().getDetails();
			log.info("Login Failed, User: " + auth.getName() + ", password: "
					+ auth.getCredentials() + ", ip: " + webAuth.getRemoteAddress() + ", cause: "
					+ e.getException());
		}
	}

}
