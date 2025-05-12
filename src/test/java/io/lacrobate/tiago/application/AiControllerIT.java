package io.lacrobate.tiago.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AiControllerIT extends CommonITUtils{

	@MockBean
	private ChatClient chatClient;

	private TestRestTemplate restTemplate;

	@Test
	@DisplayName("should call IA interface when I call POST /eventstructure with a natural phrase")
	public void whenITryToGetEventFromNaturalLanguage() {
		restTemplate = new TestRestTemplate("admin", "password");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String body = """
				{
					"message": "ajouter l evenement Pot de départ demain de 10h à 11h"
				}
				""";
		HttpEntity<String> request = new HttpEntity<>(body, headers);

		restTemplate.postForEntity(createUrl("/ia/eventstructure"), request, String.class);
		verify(chatClient).call(any(Prompt.class));
	}

}