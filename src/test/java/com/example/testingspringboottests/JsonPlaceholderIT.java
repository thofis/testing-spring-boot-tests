package com.example.testingspringboottests;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

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
		webClient.get().uri("/users")
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.length()").isEqualTo(10);
	}

	@Test
	void get_todo() {
		// this endpoint should return false on first request...
		webClient.get().uri("/todos/{id}", 1L)
				.exchange()
				.expectStatus().isOk()
				.expectBody().returnResult().toString().matches("false");
		// ... and true on next.
		webClient.get().uri("/todos/{id}", 1L)
				.exchange()
				.expectStatus().isOk()
				.expectBody().returnResult().toString().matches("true");

	}
}
