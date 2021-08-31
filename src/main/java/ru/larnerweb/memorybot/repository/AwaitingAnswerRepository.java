package ru.larnerweb.memorybot.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.larnerweb.memorybot.model.Card;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class AwaitingAnswerRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Boolean isAwaitingAnswer(String userId) {
        return redisTemplate.hasKey("user-awaiting:" + userId);
    }

    public void startAwaitingAnswer(String userId) {
        redisTemplate.opsForValue().set("user-awaiting:"+userId, "?", Duration.ofHours(6));
    }
}
