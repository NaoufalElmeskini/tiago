package io.lacrobate.tiago.application.calendar;

import com.google.api.client.util.DateTime;
import io.lacrobate.tiago.adapter.calendar.CalendarPort;
import io.lacrobate.tiago.adapter.ia.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    @Autowired
    private CalendarPort calendarPort;

    @GetMapping("/add-event-demo")
    public String addDemoEvent() {
        log.info("Adding birthday event...");
        Event demoEvent = event();
        return calendarPort.ajouterEvent(demoEvent);
    }

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
}