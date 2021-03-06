package com.adex.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration.
 * 
 * @author arc
 */
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
	
	@Value("${zuul.prefix}")
	private String apiPrefix;

    /**
     * Enables Cross-Origin Resource Sharing (CORS)
     * @param registry
     */
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping(apiPrefix);
    }
    
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    	configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

}
