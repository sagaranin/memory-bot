package ru.larnerweb.memorybot.handler.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import reactor.core.publisher.Sinks;
import ru.larnerweb.memorybot.config.TextConstants;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.larnerweb.memorybot.config.BotButtons.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class HelpCommandHandler {

    private final Sinks.Many<Update> updates;
    private final Sinks.Many<BotApiMethod<?>> responses;

    @PostConstruct
    private void listenUpdates() {
        updates.asFlux()
                .filter(u -> u.getMessage() != null)
                .filter(u -> u.getMessage().isCommand())
                .filter(u -> u.getMessage().getText().startsWith("/help"))
                .subscribe(update -> {
                    SendMessage message = SendMessage.builder()
                            .chatId(update.getMessage().getChatId().toString())
                            .replyMarkup(InlineKeyboardMarkup.builder()
                                    .keyboardRow(
                                            List.of(addCardButton, mainSettingsButton)
                                    ).keyboardRow(
                                            List.of(helpButton)
                                    )
                                    .build())
                            .text(TextConstants.HELP)
                            .build();
                    responses.tryEmitNext(message)
                            .orThrow();
                });
    }
}
