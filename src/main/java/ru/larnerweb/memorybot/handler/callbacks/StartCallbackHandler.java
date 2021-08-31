package ru.larnerweb.memorybot.handler.callbacks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import reactor.core.publisher.Sinks;
import ru.larnerweb.memorybot.config.TextConstants;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.larnerweb.memorybot.config.BotButtons.*;
import static ru.larnerweb.memorybot.config.BotButtons.helpButton;

@Log4j2
@Service
@RequiredArgsConstructor
public class StartCallbackHandler {

    private final Sinks.Many<Update> updates;
    private final Sinks.Many<BotApiMethod<?>> responses;

    @PostConstruct
    private void listenUpdates() {

        updates.asFlux()
                .filter(u -> u.getCallbackQuery() != null)
                .filter(u -> u.getCallbackQuery().getData().equals("/start"))
                .subscribe(update -> {

                    EditMessageText message = EditMessageText.builder()
                            .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                            .messageId(update.getCallbackQuery().getMessage().getMessageId())
                            .text(TextConstants.START)
                            .replyMarkup(InlineKeyboardMarkup.builder()
                                    .keyboardRow(
                                            List.of(addCardButton, mainSettingsButton)
                                    ).keyboardRow(
                                            List.of(helpButton)
                                    )
                                    .build()
                            ).build();

                    responses.tryEmitNext(message)
                            .orThrow();
                });

    }
}
