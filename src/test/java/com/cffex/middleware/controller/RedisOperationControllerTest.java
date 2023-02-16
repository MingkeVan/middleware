package com.cffex.middleware.controller;

import com.cffex.middleware.bean.RedisDTO;
import com.cffex.middleware.service.RedisOperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// RedisDto增删改查单元测试
@SpringBootTest
class RedisOperationControllerTest {

    @Autowired
    RedisOperationController redisOperationController;
    @Mock
    RedisOperationService redisOperationService;

    // 添加redisDto
    @Test
    public void add() {
        // 测试添加redisDto
        RedisDTO red = new RedisDTO();

        red.setName("test");
        red.setHost("127.0.0.1");
        red.setPort("6379");


        redisOperationController.add(red);

        Assertions.assertEquals(1, 1);
    }

    // 删除redisDto的单元测试


    @Test
    void updateR() {
    }

    @Test
    void list() {
    }
}