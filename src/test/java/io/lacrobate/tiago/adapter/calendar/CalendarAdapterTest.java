package io.lacrobate.tiago.adapter.calendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CalendarAdapterTest {
	@Autowired
	private CalendarAdapter calendarAdapter;
	@MockBean
	private EventMapper mapper;


	@Test
	@DisplayName("should interact with EventMapper when I call :ajouterEvent(event)")
	public void whenITryToAjouterEvent() {
		var event = EventBuilders.event();
		var googleEvent = EventBuilders.googleEvent();;
		when(mapper.toGoogleEvent(eq(event)))
				.thenReturn(googleEvent);
		//    when
		calendarAdapter.ajouterEvent(event);
		//    then
		verify(mapper).toGoogleEvent(event);
	}

}