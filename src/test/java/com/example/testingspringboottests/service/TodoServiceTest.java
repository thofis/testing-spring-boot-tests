package com.example.testingspringboottests.service;

import java.util.HashMap;
import java.util.Optional;

import com.example.testingspringboottests.restclient.TodoClient;
import feign.FeignException.NotFound;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

	@Mock
	TodoClient client;

	@Mock
	TodoRepository repository;

	@InjectMocks
	TodoService service;

	@Captor
	ArgumentCaptor<Long> longCaptor;

	@Test
	void get_todo_not_yet_persisted() {
		final Todo todo = Todo.builder()
				.id(1L)
				.title("title")
				.completed(false)
				.build();
		when(client.getTodoById(1L)).thenReturn(todo);
		when(repository.findById(1L)).thenReturn(Optional.empty());
		// when
		final boolean alreadyPersisted = service.getAndPersistTodo(1L);
		// then
		assertThat(alreadyPersisted).isFalse();
		// capture the argument that the service used to call the client
		verify(client).getTodoById(longCaptor.capture());
		assertThat(longCaptor.getValue()).isEqualTo(1L);
		verify(client, times(1)).getTodoById(1L);
		verify(repository, times(1)).findById(1L);
		verify(repository, times(1)).save(todo);
	}

	@Test
	void get_todo_already_persisted() {
		// given
		final Todo todo = Todo.builder()
				.id(1L)
				.title("title")
				.completed(false)
				.build();
		when(client.getTodoById(1L)).thenReturn(todo);
		when(repository.findById(1L)).thenReturn(Optional.of(todo));
		// when
		final boolean alreadyPersisted = service.getAndPersistTodo(1L);
		// then
		assertThat(alreadyPersisted).isTrue();
		verify(client, times(1)).getTodoById(1L);
		verify(repository, times(1)).findById(1L);
		verify(repository, never()).save(todo);
	}

	@Test
	void get_todo_produces_notfound() {
		// given
		Request request = Request.create(Request.HttpMethod.GET, "url",
				new HashMap<>(), null, new RequestTemplate());
		when(client.getTodoById(1L)).thenThrow(new NotFound("", request, null));
		// when
		// then
		assertThatThrownBy(() -> {
			service.getAndPersistTodo(1L);
		}).isInstanceOf(NotFound.class);
		verify(repository, never()).findById(1L);
		verify(repository, never()).save(any(Todo.class));
	}

	@Test
	void get_todo_with_null_param_throws() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			service.getAndPersistTodo(null);
		}).isInstanceOf(NullPointerException.class);
		verify(client, never()).getTodoById(1L);
		verify(repository, never()).findById(1L);
		verify(repository, never()).save(any(Todo.class));
	}

}
