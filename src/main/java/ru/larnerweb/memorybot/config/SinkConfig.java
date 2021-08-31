package ru.larnerweb.memorybot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

    @Bean
    public Sinks.Many<Update> updates() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }



    @Bean
    public Sinks.Many<BotApiMethod<?>> responses() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

}
