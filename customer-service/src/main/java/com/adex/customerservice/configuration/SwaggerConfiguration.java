/**
 * 
 */
package com.adex.customerservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
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
	private final String devName;
	private final String devUrl;
	private final String devEmail;

	public SwaggerConfiguration(
			@Value("${springfox.documentation.swagger-ui.base-url:}") String baseUrl,
			@Value("${developer.name}") String devName,
			@Value("${developer.url}") String devUrl,
			@Value("${developer.email}") String devEmail
			) {
		this.devName = "";
		this.devUrl = "";
		this.devEmail = "";
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
		.setViewName("redirect:" + baseUrl + "/swagger-ui/index.html");
	}

	@Bean
	public Docket customerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()                 
				.apis(RequestHandlerSelectors.basePackage("com.adex.customerservice.controller"))
				.paths(PathSelectors.regex(".*/customer.*"))
				.build()
				.apiInfo(apiMetaData());

	}

	private ApiInfo apiMetaData() {
		return new ApiInfoBuilder()
				.title("Customer REST API")
				.description("Customer API for CRUD operations.")
				.version("0.0.1-SNAPSHOT")
				.contact(new Contact(devName, devUrl, devEmail))
				.build();
	}

}
