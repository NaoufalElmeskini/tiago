package io.lacrobate.tiago.adapter.calendar;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import io.lacrobate.tiago.adapter.ia.Event;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface CalendarPort {
	String ajouterEvent(Event event);

	boolean connectionNeeded();

	AuthorizationCodeRequestUrl createAuthzUrl() throws GeneralSecurityException, IOException;

	String storeCredentials(AuthorizationCodeRequestUrl requestUrl);
}
