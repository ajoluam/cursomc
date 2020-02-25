package com.nelioalves.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.nelioalves.cursomc.services.DBService;
import com.nelioalves.cursomc.services.EmailService;
import com.nelioalves.cursomc.services.MockEmailService;
import com.nelioalves.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if (!strategy.equals("create")) {
			return false;
		}
			
		dbService.instatiateTestDatabase();
		
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	
}
