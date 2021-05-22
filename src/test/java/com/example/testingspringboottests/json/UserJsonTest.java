package com.example.testingspringboottests.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ActiveProfiles("test")
class UserJsonTest {
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void serialize_user() throws JsonProcessingException {
		UserJson userJson = UserJson
				.builder()
				.firstName("John")
				.lastName("Doe")
				.build();
		String json = objectMapper.writeValueAsString(userJson);
		assertThat(json).isEqualTo("{\"fullName\":\"John Doe\"}");
	}

	@Test
	void deserialize_user() throws JsonProcessingException {
		String json = "{\"fullName\":\"John Doe\"}";
		UserJson userJson = objectMapper.readValue(json, UserJson.class);
		assertThat(userJson)
				.hasFieldOrPropertyWithValue("firstName", "John")
				.hasFieldOrPropertyWithValue("lastName", "Doe");
	}
}
