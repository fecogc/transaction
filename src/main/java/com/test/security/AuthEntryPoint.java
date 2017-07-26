package com.test.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.test.web.ApiError;

@Component
public class AuthEntryPoint extends BasicAuthenticationEntryPoint 
{
	
	@Value("api.realm")
	private String realm;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException
	{
		response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        String error = "401_UNAUTHORIZED";
        
        final ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, error, error);
        final ObjectMapper jacksonObjectMapper = Jackson2ObjectMapperBuilder
        											.json()
        											.featuresToEnable(SerializationFeature.INDENT_OUTPUT)
        											.build();
        
        PrintWriter writer = response.getWriter();
        writer.println(jacksonObjectMapper.writeValueAsString(apiError));
	}
 
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(realm);
        super.afterPropertiesSet();
    }
}