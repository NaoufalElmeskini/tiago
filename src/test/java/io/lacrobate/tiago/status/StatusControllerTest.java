package io.lacrobate.tiago.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatusControllerTest {
	private String urlBase;
	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeAll
	void setUp() {
		urlBase = "http://localhost:8080/rest-api/";
	}

	@Test
//	fixme
	@DisplayName("should return ACCEPTED when I call /status API")
	public void whenICallStatusAPI() {
		//    when
		HttpStatus result = restTemplate.getForObject(urlBase + "status", HttpStatus.class);
		//    then
		assertEquals(result, HttpStatus.ACCEPTED);
	}
}