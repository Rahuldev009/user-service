package com.microservice.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	@Bean
	public ModelMapper getmodelmapper() {
		return new ModelMapper();
	}
	@Bean
	@LoadBalanced
	public  RestTemplate getrestTemplate() {
		return new RestTemplate();
		
	}

}
