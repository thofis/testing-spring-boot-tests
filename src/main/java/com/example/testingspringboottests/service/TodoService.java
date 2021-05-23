package com.example.testingspringboottests.service;

import java.util.Objects;
import java.util.Optional;

import com.example.testingspringboottests.feign.TodoClient;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

	private final TodoClient client;

	private final TodoRepository repository;

	/**
	 * Gets a todo from jsonplaceholder-rest-api.
	 * Then checks of a todo with the same id is stored in local database.
	 * If no, todo gets stored in database
	 * @param id id of todo.
	 * @return true, if todo already stored in local database, false otherwise
	 */
	public boolean getAndPersistTodo(Long id) {
		Objects.requireNonNull(id);
		final Todo todoFromRestClient = client.getTodoById(id);
		final Optional<Todo> optionalTodo = repository.findById(id);
		if (optionalTodo.isPresent()) {
			return true;
		}
		else {
			repository.save(todoFromRestClient);
			return false;
		}
	}
}
