package com.mw.middleware.controller;

import com.mw.middleware.bean.RedisInfo;
import com.mw.middleware.config.RedisConnectionFactory;
import com.mw.middleware.config.RedisTemplateFactory;
import com.mw.middleware.job.RedisStatusScheduler;
import com.mw.middleware.mapper.RedisInfoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class RedisInfoControllerTest {

    @Autowired
    RedisInfoController redisInfoController;

    @Autowired
    RedisInfoMapper redisInfoMapper;

    @Autowired
    RedisTemplateFactory redisTemplateFactory;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    RedisStatusScheduler redisStatusScheduler;

    // 测试RedisInfoController.addRedisInfo

    // 1. 测试参数校验

    @Test
    @Transactional
    public void addRedisInfoTest() {
        // 1.1 name为空 抛异常
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RedisInfo redisInfo = new RedisInfo();
            redisInfoController.addRedisInfo(redisInfo);
        });

        // 1.2 host为空 抛异常

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RedisInfo redisInfo = new RedisInfo();
            redisInfo.setName("test");
            redisInfoController.addRedisInfo(redisInfo);
        });

        // 1.3 port为空
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RedisInfo redisInfo = new RedisInfo();
            redisInfo.setName("test");
            redisInfo.setHost("127.0.0.1");
            redisInfoController.addRedisInfo(redisInfo);
        });

        // 1.4 host不是ip
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RedisInfo redisInfo = new RedisInfo();
            redisInfo.setName("test");
            redisInfo.setHost("12");
            redisInfo.setPort(6379);
            redisInfoController.addRedisInfo(redisInfo);
        });

        // 1.6 port不在0-65535之间
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RedisInfo redisInfo = new RedisInfo();
            redisInfo.setName("test");
            redisInfo.setHost("127.0.0.1");
            redisInfo.setPort(70000);
            redisInfoController.addRedisInfo(redisInfo);
        });

        // 2.1 添加成功后，redisInfoMapper中有数据
        RedisInfo redisInfo1 = new RedisInfo();
        redisInfo1.setName("test");
        redisInfo1.setHost("127.0.0.1");
        redisInfo1.setPort(6379);
        Long id = redisInfoController.addRedisInfo(redisInfo1);
        // select * from redis_info where id = id
        Assertions.assertEquals(1, redisInfoMapper.selectRedisInfoById(id));

        // 2.2 添加成功后，restTemplateFactory中有数据
        Assertions.assertNotNull(redisTemplateFactory.getRedisTemplate(id));

        // 2.3 添加成功后，redisConnectionFactory中有数据
        Assertions.assertNotNull(redisConnectionFactory.getConnectionFactory(id));

        // 2.4 添加成功后，redisStatusScheduler中有数据
        Assertions.assertNotNull(redisStatusScheduler.getRedisStatus(id));

        // 3. 测试添加失败
        RedisInfo redisInfo2 = new RedisInfo();
        redisInfo2.setName("test");
        redisInfo2.setHost("12");
        redisInfo2.setPort(6379);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            redisInfoController.addRedisInfo(redisInfo2);
        });

        // 3.1 添加失败后，redisInfoMapper中没有数据
        Assertions.assertEquals(0, redisInfoMapper.selectRedisInfoById(id));

        // 3.2 添加失败后，restTemplateFactory中没有数据
        Assertions.assertNull(redisTemplateFactory.getRedisTemplate(id));

        // 3.3 添加失败后，redisConnectionFactory中没有数据
        Assertions.assertNull(redisConnectionFactory.getConnectionFactory(id));

        // 3.4 添加失败后，redisStatusScheduler中没有数据
        Assertions.assertNull(redisStatusScheduler.getRedisStatus(id));
    }




    @Test
    void addRedisInfo() {


    }

    @Test
    void updateRedisInfo() {
    }

    @Test
    void deleteRedisInfo() {
    }

    @Test
    void listRedisInfo() {
    }
}