package io.lacrobate.tiago.adapter.ia;

import lombok.Builder;

@Builder
public record Event(String titre, String startDate, String endDate, String description) {

}