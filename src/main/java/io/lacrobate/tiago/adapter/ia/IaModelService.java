package io.lacrobate.tiago.adapter.ia;

import io.lacrobate.tiago.controller.ia.AiResponse;

public interface IaModelService {

	AiResponse processQuery(String message);
}
