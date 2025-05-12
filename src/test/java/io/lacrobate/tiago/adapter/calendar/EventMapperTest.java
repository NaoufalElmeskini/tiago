package io.lacrobate.tiago.adapter.calendar;

import com.google.api.services.calendar.model.EventDateTime;
import io.lacrobate.tiago.adapter.ia.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventMapperTest {

    @Autowired
    EventMapper eventMapper;

    @Test
    @DisplayName("should return equivalent GoogleEvent when I call toGoogleEvent with domain Event")
    public void whenIMapToGoogleEventFromDomainEvent() {
        Event event = EventBuilders.event();
        //    when
        var googleEvent = eventMapper.toGoogleEvent(event);
        //    then
    	assertThat(googleEvent)
                .extracting(
                        "summary",
                        "description"
                )
                .containsExactly(
                        event.titre(),
                        event.description());

        assertThat(googleEvent.getStart().getDateTime().toStringRfc3339())
                .contains(event.startDate());
        assertThat(googleEvent.getEnd().getDateTime().toStringRfc3339())
                .contains(event.endDate());

    }

    @Test
    void testWithOffset() {
        String input = "2025-05-28T10:00:00.000+02:00";
        EventDateTime result = eventMapper.toEventDateTime(input);
        assertNotNull(result.getDateTime());
        assertTrue(result.getDateTime().toStringRfc3339().startsWith("2025-05-28T10:00:00"));
    }

    @Test
    void testWithoutOffset() {
        String input = "2025-05-28T10:00:00";
        EventDateTime result = eventMapper.toEventDateTime(input);
        assertNotNull(result.getDateTime());
        assertTrue(result.getDateTime().toStringRfc3339().startsWith("2025-05-28T10:00:00"));
    }

    @Test
    void testInvalidFormat() {
        String input = "28/05/2025 10:00";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> eventMapper.toEventDateTime(input));
        assertTrue(exception.getMessage().contains("Invalid date format"));
    }
}