package com.mw.middleware.job;

import com.mw.middleware.bean.RedisInfo;
import com.mw.middleware.bean.RedisStatus;
import com.mw.middleware.config.RedisTemplateFactory;
import com.mw.middleware.mapper.RedisInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class RedisStatusScheduler {
    // logger
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RedisStatusScheduler.class);

    // RedisOperationService
    @Resource
    private RedisInfoMapper redisInfoMapper;

    @Autowired
    RedisTemplateFactory redisTemplateFactory;

    private final Map<Long, RedisStatus> statusCache = new HashMap<>();

    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void updateRedisStatus() {
        // 获取所有的RedisInfo
        List<RedisInfo> redisInfoList = redisInfoMapper.selectAllRedisInfo();

        // 遍历所有的RedisInfo
        for (RedisInfo redisInfo : redisInfoList) {

            RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(redisInfo.getId());
            // 获取info
            Properties info = redisTemplate.getRequiredConnectionFactory().getConnection().info();
            // check info
            if (info == null) {
                logger.error("RedisInfo is null, id: {}", redisInfo.getId());
                continue;
            }

            // 解析info
            RedisStatus status = parseInfo(info);
            statusCache.put(redisInfo.getId(), status);
        }
    }

    // getRedisStatus by id
    public RedisStatus getRedisStatus(Long id) {
        return statusCache.get(id);
    }

    // updateRedisStatus
    public void updateRedisStatus(Long id) {
        RedisInfo redisInfo = redisInfoMapper.selectRedisInfoById(id);
        RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(redisInfo.getId());
        Properties info = redisTemplate.getRequiredConnectionFactory().getConnection().info();
        if (info == null) {
            logger.error("RedisInfo is null, id: {}", redisInfo.getId());
            return;
        }
        RedisStatus status = parseInfo(info);
        statusCache.put(redisInfo.getId(), status);
    }

    // deleteRedisStatus by id
    public void deleteRedisStatus(Long id) {
        statusCache.remove(id);
    }

    // addRedisStatus by id
    public void addRedisStatus(Long id) {
        RedisInfo redisInfo = redisInfoMapper.selectRedisInfoById(id);
        RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(redisInfo.getId());
        Properties info = redisTemplate.getRequiredConnectionFactory().getConnection().info();
        if (info == null) {
            logger.error("RedisInfo is null, id: {}", redisInfo.getId());
            return;
        }
        RedisStatus status = parseInfo(info);
        statusCache.put(redisInfo.getId(), status);
    }

    //parseInfo Optional.ofNullable
    private RedisStatus parseInfo(Properties info) {
        RedisStatus status = new RedisStatus();
        status.setVersion(info.getProperty("redis_version"));
        status.setUptimeInSeconds(Long.parseLong(Optional.ofNullable(info.getProperty("uptime_in_seconds")).orElse("0")));
        status.setConnectedClients(Long.parseLong(Optional.ofNullable(info.getProperty("connected_clients")).orElse("0")));
        status.setBlockedClients(Long.parseLong(Optional.ofNullable(info.getProperty("blocked_clients")).orElse("0")));
        status.setUsedMemory(Long.parseLong(Optional.ofNullable(info.getProperty("used_memory")).orElse("0")));
        status.setUsedMemoryRss(Long.parseLong(Optional.ofNullable(info.getProperty("used_memory_rss")).orElse("0")));
        status.setMemFragmentationRatio(Double.parseDouble(Optional.ofNullable(info.getProperty("mem_fragmentation_ratio")).orElse("0")));
        status.setKeyspaceHits(Long.parseLong(Optional.ofNullable(info.getProperty("keyspace_hits")).orElse("0")));
        status.setKeyspaceMisses(Long.parseLong(Optional.ofNullable(info.getProperty("keyspace_misses")).orElse("0")));
        status.setKeyspaceHitRate(Double.parseDouble(Optional.ofNullable(info.getProperty("keyspace_hit_rate")).orElse("0")));
        status.setInstantaneousOpsPerSec(Long.parseLong(Optional.ofNullable(info.getProperty("instantaneous_ops_per_sec")).orElse("0")));
        status.setTotalCommandsProcessed(Long.parseLong(Optional.ofNullable(info.getProperty("total_commands_processed")).orElse("0")));
        status.setRejectedConnections(Long.parseLong(Optional.ofNullable(info.getProperty("rejected_connections")).orElse("0")));
        status.setExpiredKeys(Long.parseLong(Optional.ofNullable(info.getProperty("expired_keys")).orElse("0")));
        status.setEvictedKeys(Long.parseLong(Optional.ofNullable(info.getProperty("evicted_keys")).orElse("0")));
        status.setUsedCpuSys(Double.parseDouble(Optional.ofNullable(info.getProperty("used_cpu_sys")).orElse("0")));
        status.setUsedCpuUser(Double.parseDouble(Optional.ofNullable(info.getProperty("used_cpu_user")).orElse("0")));
        status.setUsedCpuSysChildren(Double.parseDouble(Optional.ofNullable(info.getProperty("used_cpu_sys_children")).orElse("0")));
        status.setUsedCpuUserChildren(Double.parseDouble(Optional.ofNullable(info.getProperty("used_cpu_user_children")).orElse("0")));
        return status;
    }


}
