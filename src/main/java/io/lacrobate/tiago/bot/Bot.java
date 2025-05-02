package io.lacrobate.tiago.bot;

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
		log.info("Message received: " + update);
	}

	public void sendText(Long who, String what){
		SendMessage sm = SendMessage.builder()
				.chatId(who.toString()) //Who are we sending a message to
				.text(what).build();    //Message content
		try {
			execute(sm);                        //Actually sending the message
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);      //Any error will be printed here
		}
	}
}