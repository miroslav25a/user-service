package com.iceze.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2)
	            .groupName("user-api")
	            .useDefaultResponseMessages(false)
	            .apiInfo(apiInfo())
	            .select()
	            .paths(regex("/api.*"))
	            .build();
	}
	
	@Bean
    public UiConfiguration uiConfig() {
        return new UiConfiguration(null);
    }
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Simple User API")
                .description("API for managing users")
                .version("1.0")
                .build();
    }
}
