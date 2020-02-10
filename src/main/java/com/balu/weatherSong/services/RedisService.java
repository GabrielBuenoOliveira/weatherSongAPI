package com.balu.weatherSong.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@Service
public class RedisService implements CacheService {

    private ObjectMapper objectMapper;
    private RedisTemplate redisTemplate;

    @Autowired
    public RedisService(ObjectMapper objectMapper, RedisTemplate redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @SneakyThrows
    @Override
    public <T> void setValue(String key, T value, Long ttlInSeconds) {
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value), Duration.of(ttlInSeconds, SECONDS));
    }

    @Override
    public <T> T getValue(String key, Class<T> responseType) {
        try {
            return  objectMapper.readValue((String) redisTemplate.opsForValue().get(key), responseType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isPresent(String key) {
        return redisTemplate.hasKey(key);
    }
}
