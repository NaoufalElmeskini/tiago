package io.lacrobate.tiago.controller;

import io.lacrobate.tiago.adapter.ia.OpenIaModelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
class AiControllerIT extends CommonITUtils{

	@MockBean
	private OpenIaModelService aiService;

	private TestRestTemplate restTemplate;

	@Test
	@DisplayName("should XXX when I YYY")
	public void whenITryToAsk() {
		restTemplate = new TestRestTemplate("admin", "password");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String body = """
				{
				  "message": "capital de France ?"
				}
				""";
		HttpEntity<String> request = new HttpEntity<>(body, headers);

		restTemplate.postForEntity(createUrl("/ia/general"), request, String.class);

		verify(aiService).processQuery(Mockito.contains("capital de France ?"));
	}

	@Test
	@DisplayName("should XXX when I YYY")
	public void whenITryToYY() {
		restTemplate = new TestRestTemplate("admin", "password");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String body = """
				{
				  "message": "ajouter l'\\''evenement Réunion d'\\''équipe, de 10h à 11h30 demain."
				}
				""";
		HttpEntity<String> request = new HttpEntity<>(body, headers);

		restTemplate.postForEntity(createUrl("/ia/general"), request, String.class);

		verify(aiService).processQuery(Mockito.contains("capital de France ?"));
	}
		//    when
//		curl -X POST http://localhost:8080/api/ai/event \
//		-H "Content-Type: application/json" \
//		-d '{"query":"ajouter l'\''evenement Réunion d'\''équipe, de 10h à 11h30 demain."}'
		//    then
//		assertThat(false).isTrue();
//	}

}