package com.mw.middleware.controller;

import com.google.common.base.Strings;
import com.mw.middleware.bean.RedisInfo;
import com.mw.middleware.config.RedisTemplateFactory;
import com.mw.middleware.config.RedisConnectionFactory;
import com.mw.middleware.job.RedisStatusScheduler;
import com.mw.middleware.mapper.RedisInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mw.middleware.utils.CommonUtil.isValidIp;
import static com.mw.middleware.utils.CommonUtil.isValidPort;

@RestController
@RequestMapping("/redis")
public class RedisInfoController {
    @Autowired
    private RedisInfoMapper redisInfoMapper;

    @Autowired
    private RedisTemplateFactory redisTemplateFactory;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    RedisStatusScheduler redisStatusScheduler;

    @PostMapping("/add")
    @Transactional
    public Long addRedisInfo(@RequestBody RedisInfo redisInfo) {

        checkRedisInfo(redisInfo);

        Long id = redisInfoMapper.insertRedisInfo(redisInfo);
        redisConnectionFactory.createConnectionFactory(id);
        redisTemplateFactory.createRedisTemplate(id);
        redisStatusScheduler.addRedisStatus(id);

        return id;
    }


    @PutMapping("/update/{id}")
    @Transactional
    public void updateRedisInfo(@PathVariable("id") Long id, @RequestBody RedisInfo redisInfo) {

        checkRedisInfo(redisInfo);

        RedisInfo oldRedisInfo = redisInfoMapper.selectRedisInfoById(id);
        if (oldRedisInfo != null) {
            oldRedisInfo.setName(redisInfo.getName());
            oldRedisInfo.setHost(redisInfo.getHost());
            oldRedisInfo.setPort(redisInfo.getPort());
            oldRedisInfo.setPassword(redisInfo.getPassword());
            redisInfoMapper.updateRedisInfo(oldRedisInfo);
            if (!oldRedisInfo.getHost().equals(redisInfo.getHost()) || oldRedisInfo.getPort() != redisInfo.getPort()) {
                redisConnectionFactory.updateConnectionFactory(id);
                redisTemplateFactory.updateRedisTemplate(id);
                redisStatusScheduler.updateRedisStatus(id);
            }
        } else {
            throw new IllegalArgumentException("redisInfo is not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public void deleteRedisInfo(@PathVariable("id") Long id) {
        RedisInfo redisInfo = redisInfoMapper.selectRedisInfoById(id);
        if (redisInfo != null) {
            redisInfoMapper.deleteRedisInfo(id);
            redisConnectionFactory.deleteConnectionFactory(id);
            redisTemplateFactory.deleteRedisTemplate(id);
            redisStatusScheduler.deleteRedisStatus(id);
        } else {
            throw new IllegalArgumentException("redisInfo is not exist");
        }
    }

    // list
    @GetMapping("/list")
    public List<RedisInfo> listRedisInfo() {
        List<RedisInfo> redisInfos = redisInfoMapper.selectAllRedisInfo();

        // add redisStatus
        for (RedisInfo redisInfo : redisInfos) {
            redisInfo.setStatus(redisStatusScheduler.getRedisStatus(redisInfo.getId()));
        }

        return redisInfos;
    }

    // get
    @GetMapping("/get/{id}")
    public RedisInfo getRedisInfo(@PathVariable("id") Long id) {
        RedisInfo redisInfo = redisInfoMapper.selectRedisInfoById(id);
        if (redisInfo != null) {
            redisInfo.setStatus(redisStatusScheduler.getRedisStatus(id));
        }
        return redisInfo;
    }

    private static void checkRedisInfo(RedisInfo redisInfo) {
        String name = redisInfo.getName();
        String host = redisInfo.getHost();
        int port = redisInfo.getPort();

        // check name
        if (Strings.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("name is invalid");
        }

        if (Strings.isNullOrEmpty(host) || port <= 0) {
            throw new IllegalArgumentException("host or port is invalid");
        }

        // is valid host
        if (!isValidIp(host)) {
            throw new IllegalArgumentException("host or port is invalid");
        }

        // is valid port
        if (!isValidPort(String.valueOf(port))) {
            throw new IllegalArgumentException("host or port is invalid");
        }
    }


//    @GetMapping("/get/{key}")
//    public String get(@PathVariable("key") String key) {
//        return redisClient.get(key);
//    }
//
//    @PutMapping("/set")
//    public void set(@RequestParam String key, @RequestParam String value) {
//        redisClient.set(key, value);
//    }
//
//    @DeleteMapping("/delete/{key}")
//    public void delete(@PathVariable("key") String key) {
//        redisClient.delete(key);
//    }
}
