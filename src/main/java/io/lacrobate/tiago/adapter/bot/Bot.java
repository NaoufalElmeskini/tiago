package io.lacrobate.tiago.adapter.bot;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import io.lacrobate.tiago.adapter.calendar.CalendarPort;
import io.lacrobate.tiago.adapter.ia.AiModelPort;
import io.lacrobate.tiago.adapter.ia.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RequiredArgsConstructor
@Service
public class Bot extends TelegramLongPollingBot {
	private final BotProperties botProperties;
	private final AiModelPort iaPort;
	private final CalendarPort calendarPort;

	@Override
	public String getBotUsername() {
		return botProperties.getUsername();
	}

	@Override
	public String getBotToken() {
		return botProperties.getToken();
	}

	@Override
	public void onUpdateReceived(Update update) {
		try {
			log.info("Message received: " + update);
			Event event = iaPort.processQuery(update.getMessage().getText());
			// si pas connecté => envoyer lien de connection
			if (calendarPort.connectionNeeded()) {
				AuthorizationCodeRequestUrl authRequestUrl = calendarPort.createAuthzUrl();
				sendText("authentication needed: " + authRequestUrl.build());
				calendarPort.storeCredentials(authRequestUrl);
			}
			// si connecté => traitement normal
			String result = calendarPort.ajouterEvent(event);
			sendText(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void sendText(String result) {
		sendText(botProperties.getRecieverid(), result);
	}

	public void sendText(Long toWho, String what) {
		SendMessage sm = SendMessage.builder()
				.chatId(toWho.toString()) //Who are we sending a message to
				.text(what).build();    //Message content
		try {
			execute(sm);                        //Actually sending the message
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);      //Any error will be printed here
		}
	}
}