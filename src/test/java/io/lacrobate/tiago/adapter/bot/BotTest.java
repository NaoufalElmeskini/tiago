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
		verify(aiPort).processQuery(update.getMessage().getText());
		verify(calendarPort).ajouterEvent(event);
	}

	private Update update() {
		Update update = new Update();
		Message message = new Message();
		message.setText("ajouter l'evenement Anniversaire de Sara, de 15h à 16h demain.");
		update.setMessage(message);
		return update;
	}
}