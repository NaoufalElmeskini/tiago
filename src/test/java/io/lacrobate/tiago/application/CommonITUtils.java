package io.lacrobate.tiago.application;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonITUtils {

	protected String createUrl(String port, String endpoint) {
		String fullurl = "http://localhost:" + port + "/tiago-api" + endpoint;
		log.info("Full URL: {}", fullurl);
		return fullurl;
	}
	protected String createUrl(String endpoint) {
		return createUrl("8080", endpoint);
	}
}
