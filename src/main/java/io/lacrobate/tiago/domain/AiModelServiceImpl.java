package io.lacrobate.tiago.domain;

import io.lacrobate.tiago.adapter.ia.AiModelPort;
import io.lacrobate.tiago.adapter.ia.EventData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiModelServiceImpl implements AiModelService {
	private final AiModelPort aiModel;

	@Override
	public EventData processEventfrom(String message) {
		EventData aiResponse = aiModel.processQuery(message);
		return aiResponse;
	}
}
