package io.lacrobate.tiago.application.ia;

import io.lacrobate.tiago.domain.AiModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ia")
public class AiController {

	@Autowired
	private AiModelService service;

	@PostMapping(value = "/eventstructure",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<AiResponse> getEvent(@RequestBody AiRequest request) {
		return ResponseEntity.ofNullable(
				new AiResponse(service.processEventfrom(request.message())));
	}

}