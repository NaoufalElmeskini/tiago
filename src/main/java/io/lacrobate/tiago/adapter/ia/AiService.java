package io.lacrobate.tiago.adapter.ia;

import io.lacrobate.tiago.application.ia.AiResponse;

public interface AiService {

	AiResponse processQuery(String message);
}
