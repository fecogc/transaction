package com.test.config;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter 
{
	
	@Bean 
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
    // Map Swagger UI
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("index.html").addResourceLocations("classpath:/main/webapp/WEB-INF/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    // Enable Pretty output
    // Enable Fail on Unknown properties
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
    {
    	Optional<HttpMessageConverter<?>> converterFound;
        converterFound = converters.stream().filter(c -> c instanceof AbstractJackson2HttpMessageConverter).findFirst();

	     if (converterFound.isPresent()) {
	         final AbstractJackson2HttpMessageConverter converter;
	         converter = (AbstractJackson2HttpMessageConverter) converterFound.get();
	         converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	         converter.getObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	     }
    }

}
