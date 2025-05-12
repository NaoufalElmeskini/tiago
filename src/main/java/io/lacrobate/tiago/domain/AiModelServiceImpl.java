package io.lacrobate.tiago.domain;

import io.lacrobate.tiago.adapter.ia.AiModelPort;
import io.lacrobate.tiago.adapter.ia.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiModelServiceImpl implements AiModelService {
	private final AiModelPort aiModel;

	@Override
	public Event processEventfrom(String message) {
		Event aiResponse = aiModel.processQuery(message);
		return aiResponse;
	}
}
