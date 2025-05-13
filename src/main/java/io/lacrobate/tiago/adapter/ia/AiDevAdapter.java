package io.lacrobate.tiago.adapter.ia;

import com.google.api.client.util.DateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Qualifier("eventExtractor")
@Profile("dev")
@Slf4j
public class AiDevAdapter implements AiModelPort {

    @Override
    public Event processQuery(String message) {
        return event();
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