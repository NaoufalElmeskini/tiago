package io.lacrobate.tiago.adapter.bot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
class BotTest {
	@Autowired
	private Bot bot;
	@MockBean
	private AbsSender sender;

	@Test
	@DisplayName("should XXX when I YYY")
	public void whenITryToXXX() {
		//    given

		/**
		 given :
		  as telegramUser Toto

		 when
		  0. j'envoie "gauthenticate me"
		  1. Bot me repond "(link)"
		  2. (automatiquement) Toto fait parvenir code à app

		 then
		  bot me repond (Toto authenticated) !
		  et credentials stoqués dans appli
		 */
		envoyerMessageTelegram("gauthenticate me");
		BotAssertor.assertMessageSent(sender, "link");



		assertThat(false).isTrue();
	}

	private void envoyerMessageTelegram(String message) {
		bot.onUpdateReceived(update(message));
	}

	private Update update(String text) {
		Update update = new Update();
		Message message = new Message();
		message.setText(text);
		update.setMessage(message);
		return update;
	}

	private static class BotAssertor {
		public static void assertMessageSent(AbsSender sender, String link) {
			try {
				ArgumentCaptor<SendMessage> argCaptor = ArgumentCaptor.captor();
				verify(sender).execute(argCaptor.capture());
				SendMessage message = argCaptor.getValue();
				assertThat(message.getText()).contains(link);
			} catch (TelegramApiException e) {
				throw new RuntimeException(e);
			}

		}
	}
}