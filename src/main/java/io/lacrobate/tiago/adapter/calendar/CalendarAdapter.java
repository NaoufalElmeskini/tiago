package io.lacrobate.tiago.adapter.calendar;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import io.lacrobate.tiago.adapter.ia.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequiredArgsConstructor
@Service
public class CalendarAdapter implements CalendarPort {
	private final EventMapper mapper;


	public static String GOOGLE_TIME_ZONE = "Europe/Paris";

	@Autowired
	private GoogleCalendarTemplate googleCalendarTemplate;


	@Override
	public String ajouterEvent(Event event) {

		// map EventData to Google Event
		var googleEvent = mapper.toGoogleEvent(event);

		return createEvent(googleEvent);
	}

	@Override
	public boolean connectionNeeded() {
		if (googleCalendarTemplate.checkAuthorizationStatus() != AuthStatus.CODE2) {
			return true;
		}
		return false;
	}

	@Override
	public AuthorizationCodeRequestUrl createAuthzUrl() throws GeneralSecurityException, IOException {
		return googleCalendarTemplate.authRequestUrl();
	}

	@Override
	public String storeCredentials(AuthorizationCodeRequestUrl requestUrl) {
		return googleCalendarTemplate.storeCredentials(requestUrl);
	}

	private String createEvent(com.google.api.services.calendar.model.Event event) {
		try {

			// Ajouter des rappels: 1 jour et 1 heure avant
			com.google.api.services.calendar.model.Event.Reminders reminders = new com.google.api.services.calendar.model.Event.Reminders().setUseDefault(false);
			event.setReminders(reminders);

			// Insérer l'événement dans le calendrier principal
			event = googleCalendarTemplate.insertEvent(event);

			return "Événement créé avec succès: " + event.getHtmlLink();
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
			return "Erreur lors de la création de l'événement: " + e.getMessage();
		}
	}

}
