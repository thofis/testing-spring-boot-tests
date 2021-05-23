package com.example.testingspringboottests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
	@Autowired
	private TodoService todoService;

	@GetMapping("/todos/{id}")
	public boolean getTodo(@PathVariable("id") long id) {
		return todoService.getAndPersistTodo(id);
	}
}
