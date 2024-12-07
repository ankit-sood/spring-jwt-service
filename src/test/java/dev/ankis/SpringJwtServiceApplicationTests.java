package dev.ankis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ankis.entities.User;
import dev.ankis.repositories.UserRepository;
import dev.ankis.requests.LoginUserRequest;
import dev.ankis.requests.RegisterUserRequest;
import dev.ankis.responses.LoginResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS, scripts = "classpath:/ddl/data.sql")
class SpringJwtServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private UserRepository userRepository;

	@Order(1)
	@Test
	@DisplayName("When a new request to register user comes, the user should be registered in the system.")
	void registerUser() throws Exception {
		RegisterUserRequest newUser = RegisterUserRequest.builder()
				.email("foo@bar.com")
				.fullName("Foo Bar")
				.password("foobar")
				.build();


		String response = mockMvc
				.perform(post("/auth/sign-up").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(newUser)))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		final JsonNode node = mapper.readTree(response);

		assertThat(userRepository.findByEmail(newUser.getEmail())
								 .orElseThrow(
										 () -> new IllegalStateException("New User is not registered in the system.")
								 ).getUsername(), equalTo(node.get("username").asText()));
	}

	@Order(2)
	@Test
	@DisplayName("When user provides correct username & password then user should be logged into the system.")
	void loginUser() throws Exception {
		final LoginResponse loginResponse = getLoginToken();

		assertThat(loginResponse.getToken(), notNullValue());
		assertThat(loginResponse.getExpiresIn(), equalTo(3600000l));

	}

	@Order(3)
	@Test
	@DisplayName("When users endpoint is called without any parameter then all the users in db should be returned.")
	void getAllUsers() throws Exception {
		final LoginResponse loginResponse = getLoginToken();


		String response = mockMvc
				.perform(get("/users/").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + loginResponse.getToken()))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		TypeReference<List<User>> jacksonUserTypeReference = new TypeReference<>() {};
		final List<User> users = mapper.readValue(response, jacksonUserTypeReference);

		assertThat(users, notNullValue());
		assertThat(users.size(), equalTo(1));
	}

	private LoginResponse getLoginToken() throws Exception {
		LoginUserRequest registeredUser = LoginUserRequest.builder()
				.email("foo@bar.com")
				.password("foobar")
				.build();


		String response = mockMvc
				.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(registeredUser)))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		return mapper.readValue(response, LoginResponse.class);
	}

}
