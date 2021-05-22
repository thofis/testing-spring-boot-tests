package com.example.testingspringboottests.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class UserJsonDeserializer extends JsonDeserializer<UserJson> {
	@Override
	public UserJson deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
		final TextNode fullName = (TextNode) treeNode.get("fullName");
		final String[] nameSegments = fullName.textValue().split(" ");
		return UserJson.builder()
				.firstName(nameSegments[0])
				.lastName(nameSegments[1])
				.build();
	}
}
