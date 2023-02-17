package com.mw.middleware.controller;

import com.google.common.collect.ImmutableMap;
import com.mw.middleware.bean.RedisDTO;
import com.mw.middleware.bean.RedisPojo;
import com.mw.middleware.service.RedisOperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

// RedisDto增删改查单元测试
@SpringBootTest
class RedisOperationControllerTest {

    @Autowired
    RedisOperationController redisOperationController;
    @Mock
    RedisOperationService redisOperationService;

    @Test
    public void add() {
        // 添加redisDTO, 确定添加成功
        RedisDTO redisDTO = new RedisDTO();
        redisDTO.setName("test");
        redisDTO.setHost("127.0.0.1");
        redisDTO.setPort("6379");

        redisOperationController.add(redisDTO);

        // 查询redisDTO
        RedisDTO redisDTO1 = redisOperationController.get("test").getData();
        System.out.println(redisDTO1);
        Assertions.assertEquals(redisDTO, redisDTO1);

        // 更新redisDTO, 确定更新成功
        redisDTO.setHost("127.0.0.2");
        redisOperationController.update("test", redisDTO);

        // 确定host字段是127.0.0.2
        RedisDTO redisDTO2 = redisOperationController.get("test").getData();
        System.out.println(redisDTO2);
        Assertions.assertEquals("127.0.0.2", redisDTO2.getHost());

        // 删除redisPojo, 确定删除成功
        redisOperationController.delete(ImmutableMap.of("name", "test"));

        // 确定删除成功  异常信息为redis resource not exist
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.get("test");
        }, "redis resource not exist");

    }

    // 添加redisDTO 边界条件测试
    @Test
    @Transactional
    public void addBoundaryTest() {
        // 添加redisDTO为空， 抛出异常
        RedisDTO redisDTO = new RedisDTO();
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.add(redisDTO);
        });

        // redisDTO name为空
        redisDTO.setName("");
        // 测试异常信息为"name is null or empty"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.add(redisDTO);
        }, "name is null or empty");

        // redisDTO host为空
        redisDTO.setName("test");
        redisDTO.setHost("");
        //测试异常信息为"host is null or empty"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.add(redisDTO);
        }, "host is null or empty");

        // redisDTO port为空
        redisDTO.setHost("127.0.0.1");
        redisDTO.setPort("");
        //测试异常信息为"port is null or empty"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.add(redisDTO);
        }, "port is null or empty");

        // redisDTO host不合法
        redisDTO.setName("test");
        redisDTO.setHost("127");
        redisDTO.setPort("6379");
        //测试异常信息为"host is not ip address"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.add(redisDTO);
        }, "host is not ip address");

        // redisDTO port is not number
        redisDTO.setHost("127.0.0.1");
        redisDTO.setPort("ss");
        //测试异常信息为"port is not valid"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.add(redisDTO);
        }, "port is not number");

        // name exists
        redisDTO.setName("test");
        redisDTO.setHost("127.0.0.1");
        redisDTO.setPort("6379");
        redisOperationController.add(redisDTO);
        //测试异常信息为"name is exist"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.add(redisDTO);
        }, "name is exist");
    }

    // 删除redisDTO 边界条件测试
    @Test
    @Transactional
    public void deleteBoundaryTest() {
        // 删除redisDTO为空， 抛出异常
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.delete(ImmutableMap.of("name",""));
        });

        // 删除redisDTO name为空
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.delete(ImmutableMap.of("name",""));
        }, "name is null or empty");

        // 删除redisDTO redis resource not exist
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.delete(ImmutableMap.of("name","test"));
        }, "redis resource not exist");
    }

    // update redisDTO 边界条件测试
    @Test
    @Transactional
    public void updateBoundaryTest() {
        // 更新redisDTO为空， 抛出异常
        RedisDTO redisDTO = new RedisDTO();
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.update("", redisDTO);
        });

        // redisDTO name为空
        redisDTO.setName("");
        // 测试异常信息为"name is null or empty"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.update("", redisDTO);
        }, "name is null or empty");

        // redisDTO host为空
        redisDTO.setName("test");
        redisDTO.setHost("");
        //测试异常信息为"host is null or empty"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.update("test", redisDTO);
        }, "host is null or empty");

        // redisDTO port为空
        redisDTO.setHost("127.0.0.1");
        redisDTO.setPort("");
        //测试异常信息为"port is null or empty"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.update("test", redisDTO);
        }, "port is null or empty");

        // redisDTO host不合法
        redisDTO.setName("test");
        redisDTO.setHost("127");
        redisDTO.setPort("6379");
        //测试异常信息为"host is not ip address"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.update("test", redisDTO);
        }, "host is not ip address");

        // redisDTO port is not number
        redisDTO.setHost("127.0.0.1");
        redisDTO.setPort("ss");
        //测试异常信息为"port is not valid"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.update("test", redisDTO);
        }, "port is not number");

        // redis resource not exist
        redisDTO.setName("test");
        redisDTO.setHost("127.0.0.1");
        redisDTO.setPort("6379");
        //测试异常信息为"redis resource not exist"
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.update("test", redisDTO);
        }, "redis resource not exist");
    }

    // get redisDTO 边界条件测试
    @Test
    @Transactional
    public void getBoundaryTest() {
        // 获取redisDTO为空， 抛出异常
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.get("");
        });

        // 获取redisDTO name为空
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.get("");
        }, "name is null or empty");

        // 获取redisDTO redis resource not exist
        Assertions.assertThrows(Exception.class, () -> {
            redisOperationController.get("123");
        }, "redis resource not exist");
    }
}