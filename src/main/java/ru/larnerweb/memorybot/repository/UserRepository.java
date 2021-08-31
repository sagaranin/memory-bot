package ru.larnerweb.memorybot.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserRepository {

    private final RedisTemplate<String, User> redisTemplate;

    public void saveOrUpdateUser(User user){
        redisTemplate.opsForValue().set("user:" + user.getId(), user);
    }
}
