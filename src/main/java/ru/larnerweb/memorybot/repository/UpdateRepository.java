package ru.larnerweb.memorybot.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
@RequiredArgsConstructor
public class UpdateRepository {

    private final RedisTemplate<String, Update> redisTemplate;

    public void saveUpdate(Update update){
        redisTemplate.opsForValue().set("update:" + update.getUpdateId(), update, 72, TimeUnit.HOURS);
    }
}
