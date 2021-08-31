package ru.larnerweb.memorybot.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.larnerweb.memorybot.model.Card;


@Log4j2
@Service
@RequiredArgsConstructor
public class CardRepository {

    private final RedisTemplate<String, Card> redisTemplateCard;
    private final CardScheduleRepository cardScheduleRepository;

    public void addCard(Card card) {
        redisTemplateCard.opsForValue()
                .set("card:" + card.getOwner() +":"+ card.getFrontHash(), card);
        cardScheduleRepository.addSchedule(card);
    }

    public Card getCard(String id) {
        return redisTemplateCard.opsForValue().get("card:"+id);
    }

    public void removeCard() {

    }
}
