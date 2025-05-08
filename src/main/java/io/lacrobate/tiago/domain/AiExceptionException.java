package io.lacrobate.tiago.domain;

public class AiExceptionException extends RuntimeException {
	public AiExceptionException(Exception e) {
		super(e);
	}
}
