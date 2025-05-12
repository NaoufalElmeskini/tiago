package io.lacrobate.tiago.domain;

import io.lacrobate.tiago.adapter.ia.Event;

public interface AiModelService {
	Event processEventfrom(String message);
}
