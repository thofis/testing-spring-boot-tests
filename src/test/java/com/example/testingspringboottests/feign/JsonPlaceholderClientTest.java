package com.example.testingspringboottests.feign;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.example.testingspringboottests.service.TodoService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import wiremock.com.google.common.io.ByteStreams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.devtools.autoconfigure.LocalDevToolsAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@EnableAutoConfiguration(exclude = { WebMvcAutoConfiguration.class,
		JmxAutoConfiguration.class,
		LocalDevToolsAutoConfiguration.class,
		RestTemplateAutoConfiguration.class,
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
		SimpleDiscoveryClientAutoConfiguration.class })
@ContextConfiguration(
		initializers = WireMockInitializer.class)
@ActiveProfiles("test")
class JsonPlaceholderClientTest {
	@Autowired
	private WireMockServer wireMockServer;

	@Autowired
	private JsonPlaceholderClient jsonPlaceholderClient;

	@Value("classpath:users.json")
	Resource usersResource;

	@Value("classpath:user.json")
	Resource userResource;

	@AfterEach
	public void afterEach() {
		wireMockServer.resetAll();
	}

	@MockBean
	private TodoService todoService;

	@Test
	public void test_get_users() throws IOException {
		// given
		wireMockServer.stubFor(
				WireMock.get("/users")
						.willReturn(aResponse()
								.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
								.withBody(ByteStreams.toByteArray(usersResource.getInputStream())))
		);
		// when
		final List<UserModel> users = jsonPlaceholderClient.getUsers();
		// then
		assertThat(users).isNotEmpty();
		assertThat(users).size().isEqualTo(10);
		users.stream()
				.filter(user -> user.getId() == 1)
				.forEach(user -> assertThat(user.getEmail()).isEqualTo("Sincere@april.biz"));
		users.stream()
				.forEach(user -> assertThat(user.getCompany()).isNotNull());
	}

	@Test
	public void test_get_user() throws IOException {
		// given
		long userId = 77;
		wireMockServer.stubFor(
				WireMock.get("/users/" + userId)
						.willReturn(aResponse()
								.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
								.withBody(ByteStreams.toByteArray(userResource.getInputStream())))
		);
		// when
		final ResponseEntity<UserModel> userResponse = jsonPlaceholderClient.getUserById(userId);
		// then
		assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(Objects.requireNonNull(userResponse.getBody()).getId()).isEqualTo(userId);
		assertThat(userResponse.getHeaders().get("content-type")).contains(MediaType.APPLICATION_JSON_VALUE);
	}

}
