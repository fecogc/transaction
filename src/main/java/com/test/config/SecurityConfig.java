package com.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.test.security.AuthEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{	
	@Autowired
	private AuthEntryPoint authEntryPoint;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication().withUser("user").password("1234").roles("ADMIN");
    } 
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
        .csrf().disable()        
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authEntryPoint)
        .and()
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .anonymous().disable()
        .httpBasic()
        .and()
        .addFilterBefore(new BasicAuthenticationFilter(authenticationManager(), authEntryPoint), BasicAuthenticationFilter.class);
    }
}