package ru.larnerweb.memorybot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Sinks;
import ru.larnerweb.memorybot.config.BotConfig;

import javax.annotation.PostConstruct;

@Log4j2
@Service
@RequiredArgsConstructor
public class ListenerService extends TelegramLongPollingBot {

    private final BotConfig config;
    private final Sinks.Many<Update> updates;
    private final Sinks.Many<BotApiMethod<?>> responses;

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update: {}", update.getUpdateId());
        updates.tryEmitNext(update).orThrow();
    }

    @PostConstruct
    public void responseListener() {
        responses.asFlux()
                .subscribe(this::sendResponseMessage);
    }

    void sendResponseMessage(BotApiMethod<?> message) {
        log.info("Sending message: '{}'", message.getMethod());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

}
