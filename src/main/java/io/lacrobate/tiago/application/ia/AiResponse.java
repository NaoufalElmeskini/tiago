package io.lacrobate.tiago.application.ia;

import io.lacrobate.tiago.adapter.ia.Event;
import lombok.Builder;

@Builder
public record AiResponse(Event result) {
}
