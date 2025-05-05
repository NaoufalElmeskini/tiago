package io.lacrobate.tiago.status;

import io.lacrobate.tiago.controller.CommonITUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StatusControllerTest extends CommonITUtils {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@DisplayName("should return ACCEPTED when I call /status API")
	public void whenICallStatusAPI() {
		//    when
		var result = restTemplate.getForObject(createUrl("/status"), HttpStatus.class);
		//    then
		assertThat(result).isEqualTo(HttpStatus.ACCEPTED);
	}
}