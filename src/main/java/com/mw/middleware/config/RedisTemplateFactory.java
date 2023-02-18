package com.mw.middleware.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisTemplateFactory {
    private final Map<Long, RedisTemplate<String, Object>> redisTemplateMap = new ConcurrentHashMap<>();

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    public RedisTemplateFactory() {
        // Do nothing
    }

    public RedisTemplate<String, Object> getRedisTemplate(Long id) {
        return redisTemplateMap.computeIfAbsent(id, this::createRedisClient);
    }

    // deleteRedisTemplate
    public void deleteRedisTemplate(Long id) {
        redisConnectionFactory.deleteConnectionFactory(id);
        redisTemplateMap.remove(id);
    }

    // updateRedisTemplate
    public void updateRedisTemplate(Long id) {
        redisConnectionFactory.updateConnectionFactory(id);
        redisTemplateMap.remove(id);
        redisTemplateMap.put(id, createRedisClient(id));
    }

    // createRedisTemplate
    public void createRedisTemplate(Long id) {
        redisTemplateMap.put(id, createRedisClient(id));
    }

    private RedisTemplate<String, Object> createRedisClient(Long id) {
        LettuceConnectionFactory connectionFactory = redisConnectionFactory.getConnectionFactory(id);

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

