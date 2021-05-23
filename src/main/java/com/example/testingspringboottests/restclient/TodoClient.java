package com.example.testingspringboottests.restclient;

import com.example.testingspringboottests.service.Todo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "todo-client", url = "${feign.url}")
public interface TodoClient {
	@RequestMapping(method = GET, value = "/users/{id}")
	Todo getTodoById(@PathVariable("id") long id);
}
