package com.example.testingspringboottests;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test", "testcontainers" })
@TestPropertySource(properties = {
		"feign.log=true",
		"spring.jpa.properties.hibernate.show_sql=true"
})
public class JsonPlaceholderIT {

	@Autowired
	private WebTestClient webClient;

	@Test
	void get_users() {

	}

	@Test
	void get_todo() {
		// this endpoint should return false on first request...
		// @formatter:off
		byte[] returnCode = webClient.get().uri("/todos/{id}", 1L)
				.exchange()
				.expectStatus().isOk()
				.expectBody().returnResult().getResponseBody();
		assertThat(returnCode).isEqualTo(Boolean.FALSE.toString().getBytes());
		// @formatter:on

		// ... and true on next.
		returnCode = webClient.get().uri("/todos/{id}", 1L)
				.exchange()
				.expectStatus().isOk()
				.expectBody().returnResult().getResponseBody();
		assertThat(returnCode).isEqualTo(Boolean.TRUE.toString().getBytes());
	}
}
