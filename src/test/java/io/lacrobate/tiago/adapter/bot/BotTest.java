package io.lacrobate.tiago.adapter.bot;

import io.lacrobate.tiago.adapter.calendar.CalendarPort;
import io.lacrobate.tiago.adapter.calendar.EventBuilders;
import io.lacrobate.tiago.adapter.ia.AiModelPort;
import io.lacrobate.tiago.adapter.ia.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BotTest {
	@Autowired
	private Bot bot;
	@MockBean
	private CalendarPort calendarPort;
	@MockBean
	private AiModelPort aiPort;
	@MockBean
	private BotSender botSender;


	@Test
	@DisplayName("should call AiService and calendarPort when I process onUpdateReceived event")
	public void whenOnUpdateReceivedEvent() {
		Event event = EventBuilders.event();
		Update update = update();
		when(aiPort.processQuery(update.getMessage().getText()))
				.thenReturn(event);

		//    when
		this.bot.onUpdateReceived(update);

		//    then
		verify(calendarPort).ajouterEvent(event);
	}

	@Test
	@DisplayName("should XXX when I send event as non authentified (calendar api) user ")
	public void whenIAddEventAsNonAuthorizedByCalendarApi() {
		when(calendarPort.connectionNeeded())
				.thenReturn(true);
		//    when
		this.bot.onUpdateReceived(update());
		//    then
//		assertThat lien de connetion envoyé sur Teleg. comment ?

		verify(this.botSender).sendText(contains("authentication needed :"));
	}

	private Update update() {
		Update update = new Update();
		Message message = new Message();
		message.setText("ajouter l'evenement Anniversaire de Sara, de 15h à 16h demain.");
		update.setMessage(message);
		return update;
	}
}