package com.mw.middleware.config;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

public interface RedisConnectionFactory {
    LettuceConnectionFactory getConnectionFactory(Long id);

    // deleteConnectionFactory
    void deleteConnectionFactory(Long id);

    // updateConnectionFactory
    void updateConnectionFactory(Long id);

    // createConnectionFactory
    void createConnectionFactory(Long id);
}
