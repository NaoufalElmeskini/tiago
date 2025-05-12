package io.lacrobate.tiago.application;

import io.lacrobate.tiago.adapter.bot.Bot;
import io.lacrobate.tiago.adapter.bot.BotProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@RestController
@RequestMapping("/bot")
@Slf4j
@RequiredArgsConstructor
public class BotController {
	private final Bot bot;
	private final BotProperties botProperties;

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

	@GetMapping("/start")
	public HttpStatus start() throws TelegramApiException {
		log.info("Bot registring...");
		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		botsApi.registerBot(bot);
		bot.sendText(botProperties.getRecieverid(), "Hello World!");
		return HttpStatus.OK;
	}
}