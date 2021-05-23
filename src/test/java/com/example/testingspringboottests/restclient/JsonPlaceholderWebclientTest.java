package com.example.testingspringboottests.restclient;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wiremock.com.google.common.io.ByteStreams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;

@RestClientTest
@ContextConfiguration(
		initializers = WireMockInitializer.class)
@ActiveProfiles("test")
public class JsonPlaceholderWebclientTest {

	@Autowired
	private WireMockServer wireMockServer;

	@Value("classpath:users.json")
	Resource usersResource;

	@Value("${feign.url}")
	private String baseUrl;

	private WebClient webClient;

	@BeforeEach
	void beforeEach() {
		ExchangeStrategies strategies = ExchangeStrategies
				.builder()
				.codecs(clientDefaultCodecsConfigurer -> {
					clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(new ObjectMapper(), MediaType.APPLICATION_JSON));
					clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(new ObjectMapper(), MediaType.APPLICATION_JSON));

				}).build();

		// @formatter:off
		webClient = WebClient.builder()
				.baseUrl(baseUrl)
				.exchangeStrategies(strategies)
				.build();
		// @formatter:on


	}

	@Test
	void test_get_users() throws IOException {
		// given
		wireMockServer.stubFor(
				WireMock.get("/users")
						.willReturn(aResponse()
								.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
								.withBody(ByteStreams.toByteArray(usersResource.getInputStream())))
		);
		// when
		final List<UserModel> users = webClient.get().uri("/users")
				.exchangeToFlux(res -> res.bodyToFlux(UserModel.class))
				.collectList().block();
		// then
		assertThat(users).isNotEmpty();
		assertThat(users).size().isEqualTo(10);
		users.stream()
				.filter(user -> user.getId() == 1)
				.forEach(user -> assertThat(user.getEmail()).isEqualTo("Sincere@april.biz"));
		users.stream()
				.forEach(user -> assertThat(user.getCompany()).isNotNull());

	}
}
