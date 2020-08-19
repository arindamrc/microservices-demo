/**
 * 
 */
package com.adex.customerservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configuration for the Swagger API documentation.
 * 
 * @author arc
 *
 */
@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer{
	private final String baseUrl;

	public SwaggerConfiguration(
			@Value("${springfox.documentation.swagger-ui.base-url:}") String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
		registry.
		addResourceHandler(baseUrl + "/swagger-ui/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
		.resourceChain(false);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController(baseUrl + "/swagger-ui/")
		.setViewName("forward:" + baseUrl + "/swagger-ui/index.html");
	}
	
    @Bean
    public Docket customerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 
                .apis(RequestHandlerSelectors.basePackage("com.adex.customerservice.controller"))
                .paths(PathSelectors.regex(".*/customer.*"))
                .build();
             
    }

}
