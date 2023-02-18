package com.mw.middleware.config;

import com.mw.middleware.bean.RedisInfo;
import com.mw.middleware.mapper.RedisInfoMapper;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisConnectionFactoryImpl implements RedisConnectionFactory {
    private final Map<Long, LettuceConnectionFactory> connectionFactoryMap = new ConcurrentHashMap<>();

    @Autowired
    private RedisInfoMapper redisInfoMapper;

    public RedisConnectionFactoryImpl() {
        // Do nothing
    }

    @PostConstruct
    public void init() {
        List<RedisInfo> redisInfoList = redisInfoMapper.selectAllRedisInfo();
        for (RedisInfo redisInfo : redisInfoList) {
            LettuceConnectionFactory connectionFactory = createConnectionFactory(redisInfo);
            connectionFactory.afterPropertiesSet();
            connectionFactoryMap.put(redisInfo.getId(), connectionFactory);
        }
    }

    @Override
    public LettuceConnectionFactory getConnectionFactory(Long id) {
        return connectionFactoryMap.get(id);
    }

    // deleteConnectionFactory
    public void deleteConnectionFactory(Long id) {
        LettuceConnectionFactory connectionFactory = connectionFactoryMap.get(id);
        connectionFactory.destroy();
        connectionFactoryMap.remove(id);
    }

    // updateConnectionFactory
    public void updateConnectionFactory(Long id) {
        LettuceConnectionFactory connectionFactory = connectionFactoryMap.get(id);
        connectionFactory.destroy();
        connectionFactoryMap.remove(id);

        RedisInfo redisInfo = redisInfoMapper.selectRedisInfoById(id);
        connectionFactoryMap.put(id, createConnectionFactory(redisInfo));
    }

    // createConnectionFactory
    public void createConnectionFactory(Long id) {
        RedisInfo redisInfo = redisInfoMapper.selectRedisInfoById(id);
        connectionFactoryMap.put(id, createConnectionFactory(redisInfo));
    }

    private LettuceConnectionFactory createConnectionFactory(RedisInfo redisInfo) {
        String host = redisInfo.getHost();
        // 判断host是否有逗号，如果有逗号，说明是集群
        if (host.contains(",")) {
            return createClusterConnectionFactory(redisInfo);
        } else {
            return createStandaloneConnectionFactory(redisInfo);
        }
    }

    // createClusterConnectionFactory
    private LettuceConnectionFactory createClusterConnectionFactory(RedisInfo redisInfo) {
        RedisClusterConfiguration configuration = new RedisClusterConfiguration();
        // password
        if (redisInfo.getPassword() != null && !redisInfo.getPassword().isEmpty()) {
            configuration.setPassword(RedisPassword.of(redisInfo.getPassword()));
        }

        String[] hostAndPort = redisInfo.getHost().split(",");
        for (String host : hostAndPort) {
            configuration.clusterNode(host, redisInfo.getPort());
        }

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    // createStandaloneConnectionFactory
    private LettuceConnectionFactory createStandaloneConnectionFactory(RedisInfo redisInfo) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisInfo.getHost());
        configuration.setPort(redisInfo.getPort());
        if (redisInfo.getPassword() != null && !redisInfo.getPassword().isEmpty()) {
            configuration.setPassword(RedisPassword.of(redisInfo.getPassword()));
        }

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }
}

