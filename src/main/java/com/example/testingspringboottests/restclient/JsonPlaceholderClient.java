package com.example.testingspringboottests.restclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "json-placeholder-client", url = "${feign.url}")
public interface JsonPlaceholderClient {
	@RequestMapping(method = GET, value = "/users")
	List<UserModel> getUsers();

	@RequestMapping(method = GET, value = "/users/{id}")
	ResponseEntity<UserModel> getUserById(@PathVariable("id") long id);
}
