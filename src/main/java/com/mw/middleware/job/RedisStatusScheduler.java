package com.mw.middleware.job;

import com.google.common.base.Strings;
import com.mw.middleware.bean.RedisDTO;
import com.mw.middleware.bean.RedisPojo;
import com.mw.middleware.bean.RedisStatus;
import com.mw.middleware.mapper.RedisOperationMapper;
import com.mw.middleware.service.RedisOperationService;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RedisStatusScheduler {
    // logger
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RedisStatusScheduler.class);

    // RedisOperationService
    @Resource
    private RedisOperationMapper redisOperationMapper;

    private final Map<String, RedisStatus> statusCache = new HashMap<>();
    private final Map<String, RedisClient> redisClientCache = new HashMap<>();

    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void updateRedisStatus() {
        List<RedisPojo> servers = getRedisServers();
        for (RedisPojo server : servers) {
            RedisClient redisClient = getRedisClient(server);
            try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
                String info = connection.sync().info();
                RedisStatus status = parseRedisStatus(info);
                statusCache.put(generateKey(server), status);
            } catch (Exception e) {
                logger.error("Failed to get redis status", e);
            }
        }
    }

    private RedisStatus parseRedisStatus(String info) {
        RedisStatus status = new RedisStatus();
        String[] lines = info.split("\\r?\\n");
        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                switch (key) {
                    case "redis_version":
                        status.setVersion(value);
                        break;
                    case "uptime_in_seconds":
                        status.setUptimeInSeconds(Long.parseLong(value));
                        break;
                    case "connected_clients":
                        status.setConnectedClients(Long.parseLong(value));
                        break;
                    case "blocked_clients":
                        status.setBlockedClients(Long.parseLong(value));
                        break;
                    case "used_memory":
                        status.setUsedMemory(Long.parseLong(value));
                        break;
                    case "used_memory_rss":
                        status.setUsedMemoryRss(Long.parseLong(value));
                        break;
                    case "mem_fragmentation_ratio":
                        status.setMemFragmentationRatio(Double.parseDouble(value));
                        break;
                    case "keyspace_hits":
                        status.setKeyspaceHits(Long.parseLong(value));
                        break;
                    case "keyspace_misses":
                        status.setKeyspaceMisses(Long.parseLong(value));
                        break;
                    case "total_connections_received":
                        status.setTotalConnectionsReceived(Long.parseLong(value));
                        break;
                    case "total_commands_processed":
                        status.setTotalCommandsProcessed(Long.parseLong(value));
                        break;
                    case "role":
                        status.setRole(value);
                        break;
                    case "cluster_enabled":
                        status.setClusterEnabled(value);
                        break;
                    default:
                        // Ignore other keys
                        break;
                }
            }
        }
        return status;
    }


    public Map<String, RedisStatus> getStatusCache() {
        return Collections.unmodifiableMap(statusCache);
    }

    // getRedisServer
    private List<RedisPojo> getRedisServers() {
        return redisOperationMapper.listRedisPojo();
    }

    private RedisClient getRedisClient(RedisPojo redisPojo) {
        RedisClient redisClient = redisClientCache.get(generateKey(redisPojo));
        // check redisClient is null
        if (redisClient == null) {

            RedisURI.Builder builder = RedisURI.builder()
                    .withHost(redisPojo.getHost())
                    .withPort(redisPojo.getPort())
                    .withTimeout(Duration.ofSeconds(5));

            if (!Strings.isNullOrEmpty(redisPojo.getPasswd())) {
                builder.withPassword(redisPojo.getPasswd());
            }
            redisClient = RedisClient.create(builder.build());
            redisClientCache.put(generateKey(redisPojo), redisClient);
        }

        return redisClient;
    }

    // generate redis key
    public String generateKey(RedisPojo redisPojo) {
        return redisPojo.getHost() + ":" + redisPojo.getPort();
    }
}
