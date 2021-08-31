package ru.larnerweb.memorybot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import reactor.core.publisher.Sinks;
import ru.larnerweb.memorybot.service.ListenerService;

import java.time.Duration;
import java.util.List;

@Configuration
@Getter
public class BotConfig {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Bean
    public TelegramBotsApi telegramBotsApi(ListenerService listenerService) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(listenerService);
        return botsApi;
    }

    @Bean
    public List<Long> intervals() {
        return List.of(
                Duration.ofMinutes(1).toMillis(),
                Duration.ofMinutes(10).toMillis(),
                Duration.ofMinutes(60).toMillis(),
                Duration.ofHours(5).toMillis(),
                Duration.ofHours(24).toMillis(),
                Duration.ofDays(5).toMillis(),
                Duration.ofDays(25).toMillis(),
                Duration.ofDays(120).toMillis()
        );
    }
}
