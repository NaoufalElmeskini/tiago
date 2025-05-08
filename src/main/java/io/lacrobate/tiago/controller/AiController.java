package io.lacrobate.tiago.controller;

import io.lacrobate.tiago.adapter.ia.AiResponse;
import io.lacrobate.tiago.adapter.ia.IaModelPort;
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
	private IaModelPort aiService;
	@PostMapping(value = "/general",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<AiResponse> ask(@RequestBody AiRequest request) {
		return ResponseEntity.ofNullable(
				aiService.processQuery(request.message()));
	}

	@PostMapping(value = "/eventstructure",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<AiResponse> getEvent(@RequestBody AiRequest request) {
		return ResponseEntity.ofNullable(
				aiService.processQuery(request.message()));
	}




}