package io.lacrobate.tiago.adapter.calendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;
import io.lacrobate.tiago.adapter.ia.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {

    @Mapping(target = "summary", source = "titre")
    @Mapping(target = "start", source = "startDate", qualifiedByName = "toEventDateTime")
    @Mapping(target = "end", source = "endDate", qualifiedByName = "toEventDateTime")
    com.google.api.services.calendar.model.Event toGoogleEvent(Event result);

    /**
     *
     * @param value date et heure (format ISO 8601)
     *              ex : "2025-05-09T10:00:00"
     * @return EventDateTime
     */
    @Named("toEventDateTime")
    default EventDateTime toEventDateTime(String value) {
        Instant instant;

        try {
            // Format complet avec offset : "2025-05-28T10:00:00.000+02:00"
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(value);
            instant = offsetDateTime.toInstant();
        } catch (DateTimeParseException e1) {
            try {
                // Format sans offset : "2025-05-28T10:00:00"
                LocalDateTime localDateTime = LocalDateTime.parse(value);
                instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("Invalid date format: " + value, e2);
            }
        }

        return new EventDateTime().setDateTime(new DateTime(instant.toEpochMilli()));
    }
}