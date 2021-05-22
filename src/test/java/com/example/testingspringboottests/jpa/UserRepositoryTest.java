package com.example.testingspringboottests.jpa;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@ActiveProfiles({ "test", "testcontainers" })
@ActiveProfiles("test")
class UserRepositoryTest {
	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	UserRepository userRepository;

	@Value("${spring.datasource.url}")
	String datasourceUrl;

	@BeforeEach
	public void beforeEach() {
		User testUser = User.builder()
				.name("Tester")
				.email("test@test.de")
				.build();
		testEntityManager.persistAndFlush(testUser);
		System.out.println(datasourceUrl);
	}

	@Test
	void find_user_by_email() {
		final Optional<User> optionalUser = userRepository.findByEmail("test@test.de");
		assertThat(optionalUser.isPresent()).isTrue();
		User user = optionalUser.get();
		assertThat(user.getName()).isEqualTo("Tester");
		assertThat(user.getEmail()).isEqualTo("test@test.de");
		assertThat(user.getId()).isGreaterThan(0);
	}

	@Test
	void find_all() {
		final List<User> users = userRepository.findAll();
		assertThat(users).hasSize(1);
		assertThat(users.stream().findFirst().isPresent()).isTrue();
		assertThat(users.stream().findFirst().get().getName()).isEqualTo("Tester");
	}
}
