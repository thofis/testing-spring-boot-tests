package com.example.testingspringboottests.restclient;

import feign.Logger;
import feign.Logger.Level;
import lombok.RequiredArgsConstructor;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
@RequiredArgsConstructor
public class FeignConfiguration {

	private final FeignConfigurationProperties feignConfigurationProperties;

	@Bean
	Logger.Level feignLogLevel() {
		return feignConfigurationProperties.isLog() ? Level.FULL : Level.NONE;
	}
}
