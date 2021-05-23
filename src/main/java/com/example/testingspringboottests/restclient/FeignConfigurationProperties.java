package com.example.testingspringboottests.restclient;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "feign")
@Data
public class FeignConfigurationProperties {
	private boolean log = false;

	private String url;
}
