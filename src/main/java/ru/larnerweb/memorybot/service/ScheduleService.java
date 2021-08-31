package ru.larnerweb.memorybot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import reactor.core.publisher.Sinks;
import ru.larnerweb.memorybot.repository.AwaitingAnswerRepository;
import ru.larnerweb.memorybot.repository.CardRepository;
import ru.larnerweb.memorybot.repository.CardScheduleRepository;

import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class ScheduleService {


    private final Sinks.Many<SendMessage> responses;
    private final CardRepository cardRepository;
    private final CardScheduleRepository cardScheduleRepository;
    private final AwaitingAnswerRepository awaitingAnswerRepository;

    @Scheduled(fixedRate = 60000L)
    private void processTasks(){

        // get items with current schedule time
        Set<String> items = cardScheduleRepository.getActualSchedules();

        items.forEach(
                id -> {
                    String userId = id.split(":")[0];

                    responses.tryEmitNext(
                            SendMessage.builder()
                                    .chatId(userId)
                                    .text(cardRepository.getCard(id).toString())
                                    .build()
                    ).orThrow();

                    awaitingAnswerRepository.startAwaitingAnswer(userId);
                    cardScheduleRepository.remove(id);
                }
        );
    }

}
