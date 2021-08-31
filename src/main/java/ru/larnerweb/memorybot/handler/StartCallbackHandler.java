package ru.larnerweb.memorybot.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import reactor.core.publisher.Sinks;
import ru.larnerweb.memorybot.config.TextConstants;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.larnerweb.memorybot.config.Buttons.*;
import static ru.larnerweb.memorybot.config.Buttons.helpButton;

@Log4j2
@Service
@RequiredArgsConstructor
public class StartCallbackHandler {

    private final Sinks.Many<Update> updates;
    private final Sinks.Many<SendMessage> responses;

    @PostConstruct
    private void listenUpdates() {

        updates.asFlux()
                .filter(u -> u.getCallbackQuery() != null)
                .filter(u -> u.getCallbackQuery().getData().equals("/start"))
                .subscribe(update -> {
                    SendMessage message = SendMessage.builder()
                            .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                            .replyMarkup(InlineKeyboardMarkup.builder()
                                    .keyboardRow(
                                            List.of(addCardButton, settingsButton)
                                    ).keyboardRow(
                                            List.of(helpButton)
                                    )
                                    .build())
                            .text(TextConstants.START)
                            .build();
                    responses.tryEmitNext(message)
                            .orThrow();
                });

    }
}
