package io.lacrobate.tiago.domain;

import io.lacrobate.tiago.adapter.ia.EventData;

public interface AiModelService {
	EventData processEventfrom(String message);
}
