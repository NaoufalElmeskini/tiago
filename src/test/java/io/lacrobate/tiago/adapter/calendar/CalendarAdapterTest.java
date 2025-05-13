package io.lacrobate.tiago.adapter.calendar;

import com.google.api.services.calendar.model.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static io.lacrobate.tiago.adapter.calendar.EventBuilders.event;
import static io.lacrobate.tiago.adapter.calendar.EventBuilders.googleEvent;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class CalendarAdapterTest {
	@Autowired
	private CalendarAdapter calendarAdapter;
	@MockBean
	private EventMapper mapper;
	@MockBean
	private GoogleCalendarTemplate googleCalendarTemplate;

	@Test
	@DisplayName("should interact with EventMapper when I call :ajouterEvent(event)")
	public void whenITryToAjouterEvent() {
		var event = event();
		var googleEvent = googleEvent();;
		when(mapper.toGoogleEvent(eq(event)))
				.thenReturn(googleEvent);
		//    when
		calendarAdapter.ajouterEvent(event);
		//    then
		verify(mapper).toGoogleEvent(event);
	}

	@Test
	@DisplayName("should initiate new Google auth when I Add Event As Non Authorized user")
	public void whenIAddEventAsNonAuthorizedByGoogle() throws GeneralSecurityException, IOException {
		Event googleEvent = googleEvent();
		when(googleCalendarTemplate.checkAuthorizationStatus())
				.thenReturn(AuthStatus.CODE3);
		when(googleCalendarTemplate.insertEvent(googleEvent))
				.thenReturn(googleEvent);
		when(mapper.toGoogleEvent(eq(event())))
				.thenReturn(googleEvent);
		//    when
		calendarAdapter.ajouterEvent(event());
		//    then
		verify(googleCalendarTemplate).initiateAuthorization();
	}

	@Test
	@DisplayName("should not initiate new Google auth when I Add Event As already Authorized user")
	public void whenIAddEventAsAuthorizedByGoogle() throws GeneralSecurityException, IOException {
		Event googleEvent = googleEvent();
		when(googleCalendarTemplate.checkAuthorizationStatus())
				.thenReturn(AuthStatus.CODE2);
		when(googleCalendarTemplate.insertEvent(googleEvent))
				.thenReturn(googleEvent);
		when(mapper.toGoogleEvent(eq(event())))
				.thenReturn(googleEvent);
		//    when
		calendarAdapter.ajouterEvent(event());
		//    then
		verify(googleCalendarTemplate, never()).initiateAuthorization();
	}

}