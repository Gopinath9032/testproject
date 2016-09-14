package com.gofindo.controller;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.gofindo.filters.FilterController;

import org.springframework.boot.context.embedded.FilterRegistrationBean;

@SpringBootApplication
public class Application {
	static Logger logger = Logger.getLogger(Application.class);
	/**
	 * Responsible to handle the CORS requests.
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("OPTIONS", "DELETE", "POST", "PUT", "GET");
				registry.addMapping("/user/**").allowedMethods("OPTIONS", "DELETE", "POST", "PUT", "GET");
			}
		};
	}

	public static void main(String[] args) {
		logger.info("main is started.");
		SpringApplication.run(Application.class, args);
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public FilterRegistrationBean jwtFilter() {
		logger.info("Filtering captured.");
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new FilterController());
		registrationBean.addUrlPatterns("/user/*");
		return registrationBean;
	}
}
