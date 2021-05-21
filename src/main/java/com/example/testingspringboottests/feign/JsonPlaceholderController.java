package com.example.testingspringboottests.feign;

import java.util.List;

import lombok.RequiredArgsConstructor;

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
	public UserModel getUserViaFeignClient(@PathVariable("id") long id) {
		return client.getUserById(id).getBody();
	}
}
