package com.example.testingspringboottests.feign;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class ClientInitializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
//		WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
//		wireMockServer.start();
//
//		configurableApplicationContext
//				.getBeanFactory()
//				.registerSingleton("wireMockServer", wireMockServer);
//
//		configurableApplicationContext.addApplicationListener(applicationEvent -> {
//			if (applicationEvent instanceof ContextClosedEvent) {
//				wireMockServer.stop();
//			}
//		});
//
//		TestPropertyValues
//				.of(Map.of("feign.url", "http://localhost:" + wireMockServer.port()))
//				.applyTo(configurableApplicationContext);
	}
}
