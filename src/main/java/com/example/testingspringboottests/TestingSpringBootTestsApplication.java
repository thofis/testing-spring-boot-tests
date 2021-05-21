package com.example.testingspringboottests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class TestingSpringBootTestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingSpringBootTestsApplication.class, args);
	}

}
