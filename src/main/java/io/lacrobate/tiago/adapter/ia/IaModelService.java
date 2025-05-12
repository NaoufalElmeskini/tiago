package io.lacrobate.tiago.adapter.ia;

import io.lacrobate.tiago.application.ia.AiResponse;

public interface IaModelService {

	AiResponse processQuery(String message);
}
