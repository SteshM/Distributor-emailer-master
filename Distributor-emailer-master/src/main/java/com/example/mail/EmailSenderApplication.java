package com.example.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.service")
public class EmailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSenderApplication.class, args);
	}
	@Primary
	@Bean
	public FreeMarkerConfigurationFactoryBean markerBean() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("classpath:/templates");
		return bean;
	}

}
