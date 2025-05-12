package io.lacrobate.tiago.adapter.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import io.lacrobate.tiago.adapter.ia.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequiredArgsConstructor
@Service
public class CalendarAdapter implements CalendarPort {
	private final EventMapper mapper;


	public static String GOOGLE_TIME_ZONE = "Europe/Paris";

	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

	@Value("${google.application.name}")
	private String applicationName;

	@Autowired
	private GoogleAuthService googleAuthService;

	/**
	 * Crée un client pour l'API Google Calendar
	 */
	private Calendar getCalendarService() throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Credential credentials = googleAuthService.getCredentials();
		return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentials)
				.setApplicationName(applicationName)
				.build();
	}

	@Override
	public String ajouterEvent(Event event) {
		// map EventData to Google Event
		var googleEvent = mapper.toGoogleEvent(event);

		return createEvent(googleEvent);
	}

	private String createEvent(com.google.api.services.calendar.model.Event event) {
		try {
			Calendar service = getCalendarService();

			// Ajouter des rappels: 1 jour et 1 heure avant
			com.google.api.services.calendar.model.Event.Reminders reminders = new com.google.api.services.calendar.model.Event.Reminders().setUseDefault(false);
			event.setReminders(reminders);

			// Insérer l'événement dans le calendrier principal
			event = service.events().insert("primary", event).execute();

			return "Événement créé avec succès: " + event.getHtmlLink();
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
			return "Erreur lors de la création de l'événement: " + e.getMessage();
		}
	}

}
