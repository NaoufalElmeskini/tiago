package io.lacrobate.tiago.controller;

import io.lacrobate.tiago.adapter.googlecal.GoogleAuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class GoogleAuthControllerIT extends CommonITUtils {
	@MockBean
	private GoogleAuthService googleAuthService;

	@Nested
	class AsNonAuthenticatedUser {

		@Autowired
		private MockMvc mockMvc;


		@Test
		@DisplayName("should return 401 when I get (Google) authentication status as non authenticated user")
		public void whenITryToGetAuthStatus() throws Exception {
			mockMvc.perform(get(createUrl("/googleauth/status")))
					.andExpect(MockMvcResultMatchers.status().isUnauthorized());
		}
	}


	@Nested
	class AsAuthenticatedUser {
		private TestRestTemplate restTemplate;

		@Test
		@DisplayName("should return (Google) unauthorized message when I get (Google) authentication status as an authenticated user")
		public void whenITryToGetAuthStatus() {
			restTemplate = new TestRestTemplate("admin", "password");
			restTemplate.getForObject(createUrl("/googleauth/status"), String.class);
			verify(googleAuthService).checkAuthorizationStatus();
		}

		@Test
		@DisplayName("should initiate google authentication when I GET /authorize")
		public void whenITryToGetAuthorized() {
			restTemplate = new TestRestTemplate("admin", "password");
			restTemplate.getForObject(createUrl("/googleauth/authorize"), String.class);
			verify(googleAuthService).initiateAuthorization();
		}

		@Test
		@DisplayName("should return (Google) unauthorized message when I GET /clear")
		public void whenITryToClearAuth() {
			restTemplate = new TestRestTemplate("admin", "password");
			restTemplate.getForObject(createUrl("/googleauth/clear"), String.class);
			verify(googleAuthService).clearStoredCredentials();
		}
	}
}