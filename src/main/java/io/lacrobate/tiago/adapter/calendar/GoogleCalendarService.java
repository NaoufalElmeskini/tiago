package io.lacrobate.tiago.adapter.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class GoogleCalendarService {

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

    /**
     * Crée un événement statique pour l'anniversaire de Smael le 28 mai 2025
     */
    public String createBirthdayEvent() {
        try {
            Calendar service = getCalendarService();

            Event event = new Event()
                    .setSummary("Anniversaire Smael")
                    .setDescription("Célébration de l'anniversaire de Smael");

            // Date de début: 28 mai 2025 à 10h00
            LocalDateTime startDateTime = LocalDateTime.of(2025, 5, 28, 10, 0);
            Date startDate = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
            DateTime start = new DateTime(startDate);
            event.setStart(new EventDateTime().setDateTime(start).setTimeZone("Europe/Paris"));

            // Date de fin: 28 mai 2025 à 18h00
            LocalDateTime endDateTime = LocalDateTime.of(2025, 5, 28, 18, 0);
            Date endDate = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());
            DateTime end = new DateTime(endDate);
            event.setEnd(new EventDateTime().setDateTime(end).setTimeZone("Europe/Paris"));

            // Ajouter des rappels: 1 jour et 1 heure avant
            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false);
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