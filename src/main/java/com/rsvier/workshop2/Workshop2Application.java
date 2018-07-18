package com.rsvier.workshop2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Workshop2Application implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(Workshop2Application.class, args);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/teamNevvo").setViewName("teamNevvo");
		registry.addViewController("/projects").setViewName("projects");
		registry.addViewController("/contact").setViewName("contact");
		registry.addViewController("/links").setViewName("links");
		}
}
