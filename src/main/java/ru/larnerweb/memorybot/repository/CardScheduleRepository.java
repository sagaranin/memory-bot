package ru.larnerweb.memorybot.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.larnerweb.memorybot.model.Card;

import java.util.List;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class CardScheduleRepository {
    private final static String ZSET_NAME = "card-schedule";

    private final List<Long> intervals;
    private final RedisTemplate<String, String> redisTemplateSchedule;

    public void addSchedule(Card card) {
        redisTemplateSchedule.opsForZSet()
                .add("card-schedule",
                        card.getOwner() + ":" + card.getFrontHash(),
                        card.getCreationTime() + intervals.get(card.getLevel())
                );
    }

    public Set<String> getActualSchedules() {
        return redisTemplateSchedule.opsForZSet()
                .rangeByScore(ZSET_NAME, 0, System.currentTimeMillis());
    }

    public void remove(String id) {
        redisTemplateSchedule.opsForZSet().remove(ZSET_NAME, id);
        log.info("schedule {} removed", id);
    }
}
