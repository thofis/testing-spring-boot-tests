package com.example.testingspringboottests.webmvc;

import java.util.HashMap;
import java.util.List;

import com.example.testingspringboottests.feign.AddressModel;
import com.example.testingspringboottests.feign.CompanyModel;
import com.example.testingspringboottests.feign.GeoModel;
import com.example.testingspringboottests.feign.JsonPlaceholderClient;
import com.example.testingspringboottests.feign.UserModel;
import feign.FeignException.NotFound;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import utils.ResourceReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest
@ActiveProfiles("test")
class JsonPlaceholderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JsonPlaceholderClient jsonPlaceholderClient;

	@Value("classpath:users-controller.json")
	Resource usersResource;

	@Test
	void test_get_users() throws Exception {
		// given
		when(jsonPlaceholderClient.getUsers()).thenReturn(createUsers());
		// when
		mockMvc.perform(MockMvcRequestBuilders.get("/users"))
				// then
				.andExpect(MockMvcResultMatchers.status().isOk())
				// load expected json content from file and compare with result
				.andExpect(MockMvcResultMatchers.content().json(ResourceReader.asString(usersResource)))
				// use jsonPath to verify expectation
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].company.name").value("company"))
				.andExpect(MockMvcResultMatchers.jsonPath("$..phone.concat()").value("123234"));

	}

	private List<UserModel> createUsers() {
		List<UserModel> users = List.of(
				UserModel.builder()
						.id(1L)
						.email("email@email.com")
						.name("tester")
						.username("testerX")
						.phone("123")
						.address(AddressModel.builder()
								.street("street")
								.suite("suite")
								.city("city")
								.geo(GeoModel.builder().lat(0.1).lng(0.2).build())
								.build())
						.company(CompanyModel.builder()
								.name("company")
								.catchPhrase("catch")
								.bs("bs")
								.build())
						.build(),
				UserModel.builder()
						.id(2L)
						.email("email2@email.com")
						.name("tester2")
						.username("testerY")
						.phone("234")
						.address(AddressModel.builder()
								.street("street2")
								.suite("suite2")
								.city("city2")
								.geo(GeoModel.builder().lat(0.1).lng(0.2).build())
								.build())
						.company(CompanyModel.builder()
								.name("company2")
								.catchPhrase("catch2")
								.bs("bs2")
								.build())
						.build()
		);
		return users;
	}

	/*
		@GetMapping(path = "users", produces = "application/json")
	public List<UserModel> getUsersViaFeignClient() {
		return client.getUsers();
	}

	@GetMapping(path = "users/{id}", produces = "application/json")
	public UserModel getUserViaFeignClient(@PathVariable("id") long id) {
		return client.getUserById(id).getBody();
	}
	 */

	@Test
	void test_get_user() throws Exception {
		// given
		when(jsonPlaceholderClient.getUserById(1L)).thenReturn(
				ResponseEntity.ok(UserModel.builder()
						.id(1L)
						.email("email@email.com")
						.name("tester")
						.username("testerX")
						.phone("123")
						.address(AddressModel.builder()
								.street("street")
								.suite("suite")
								.city("city")
								.geo(GeoModel.builder().lat(0.1).lng(0.2).build())
								.build())
						.company(CompanyModel.builder()
								.name("company")
								.catchPhrase("catch")
								.bs("bs")
								.build())
						.build())
		);
		// when
		mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 1L))
				// then
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}


	@Test
	void test_get_user_not_found() throws Exception {
		// given
		Request request = Request.create(Request.HttpMethod.GET, "url",
				new HashMap<>(), null, new RequestTemplate());
		// mock NotFound-Exception for given id
		when(jsonPlaceholderClient.getUserById(99L)).thenThrow(new NotFound("", request, null));
		// when
		mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 99L))
				// then
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
