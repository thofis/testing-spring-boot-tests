package com.example.testingspringboottests.webmvc;

import java.util.List;

import com.example.testingspringboottests.restclient.JsonPlaceholderClient;
import com.example.testingspringboottests.restclient.UserModel;
import feign.FeignException.NotFound;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JsonPlaceholderController {

	private final JsonPlaceholderClient client;

	@GetMapping(path = "users", produces = "application/json")
	public List<UserModel> getUsersViaFeignClient() {
		return client.getUsers();
	}

	@GetMapping(path = "users/{id}", produces = "application/json")
	public ResponseEntity<UserModel> getUserViaFeignClient(@PathVariable("id") long id) {
		final ResponseEntity<UserModel> userById;
		try {
			userById = client.getUserById(id);
		}
		catch (NotFound e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userById.getBody());
	}
}
