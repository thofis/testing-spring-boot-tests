package com.example.testingspringboottests.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class UserJsonSerializer extends JsonSerializer<UserJson> {
	@Override
	public void serialize(UserJson userJson, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("fullName", String.format("%s %s", userJson.getFirstName(), userJson.getLastName()));
		jsonGenerator.writeEndObject();
	}
}
