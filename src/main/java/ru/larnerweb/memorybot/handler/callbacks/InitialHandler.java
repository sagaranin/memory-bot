package ru.larnerweb.memorybot.handler.callbacks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import reactor.core.publisher.Sinks;
import ru.larnerweb.memorybot.repository.UserRepository;

import javax.annotation.PostConstruct;

@Log4j2
@Service
@RequiredArgsConstructor
public class InitialHandler {

    private final Sinks.Many<Update> updates;
    private final UserRepository userRepository;

    @PostConstruct
    private void listenUpdates() {
        updates.asFlux()
                .subscribe(
                        this::updateUserInfo,
                        log::error
                );
    }

    private void updateUserInfo(Update update) {
        try {
            if (update.getMessage() != null && update.getMessage().getFrom() != null) {
                User user = update.getMessage().getFrom();
                userRepository.saveOrUpdateUser(user);
            }
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
        }
    }
}
