package io.lacrobate.tiago.adapter.calendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;
import io.lacrobate.tiago.adapter.ia.Event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static io.lacrobate.tiago.adapter.calendar.CalendarAdapter.GOOGLE_TIME_ZONE;

public class EventBuilders {
	public static Event event() {
		DateTime start = dateTime(2025, 5, 28, 10); // Date de début: 28 mai 2025 à 10h00
		DateTime end = dateTime(2025, 5, 28, 18); // Date de fin: 28 mai 2025 à 18h00

		return Event.builder()
				.titre("Anniversaire Smael")
				.description("Célébration de l'anniversaire")
				.startDate(start.toString())
				.endDate(end.toString())
				.build();
	}

	private static DateTime dateTime(int year, int month, int dayOfMonth, int hour) {
		LocalDateTime startDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, 0);
		Date date = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return new DateTime(date);
	}

	public static com.google.api.services.calendar.model.Event googleEvent() {
		com.google.api.services.calendar.model.Event event = new com.google.api.services.calendar.model.Event()
				.setSummary("Anniversaire Smael")
				.setDescription("Célébration de l'anniversaire de Smael");

		DateTime start = dateTime(2025, 5, 28, 10); // Date de début: 28 mai 2025 à 10h00
		DateTime end = dateTime(2025, 5, 28, 18); // Date de fin: 28 mai 2025 à 18h00

		event.setStart(new EventDateTime().setDateTime(start).setTimeZone(GOOGLE_TIME_ZONE));
		event.setEnd(new EventDateTime().setDateTime(end).setTimeZone(GOOGLE_TIME_ZONE));

		// Ajouter des rappels: 1 jour et 1 heure avant
		com.google.api.services.calendar.model.Event.Reminders reminders = new com.google.api.services.calendar.model.Event.Reminders().setUseDefault(false);
		event.setReminders(reminders);

		return event;
	}
}
