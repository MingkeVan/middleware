package com.mw.middleware;

import com.mw.middleware.bean.RedisDTO;
import com.mw.middleware.service.RedisOperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisOperationServiceTest {
    @Autowired
    RedisOperationService redisOperationService;

    // add redisPojo test
    @Test
    public void addRedisPojoTest() {
        // 测试添加redisPojo
        RedisDTO redisDTO = new RedisDTO();
        redisDTO.setName("test");
        redisDTO.setHost("127.0.0.1");
        redisDTO.setPort("6379");

        int result = redisOperationService.addRedisDTO(redisDTO);
        System.out.println(result);
        Assertions.assertEquals(1, result);
    }

    // delete redisPojo test
    @Test
    public void deleteRedisPojoTest() {
        // 测试删除redisPojo
        String name = "test";
        int result = redisOperationService.deleteRedisDTO(name);
        System.out.println(result);
        Assertions.assertEquals(1, result);
    }

}
