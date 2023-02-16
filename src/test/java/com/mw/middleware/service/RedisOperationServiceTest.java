package com.mw.middleware.service;

import com.mw.middleware.bean.RedisDTO;
import com.mw.middleware.service.RedisOperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RedisOperationServiceTest {
    @Autowired
    RedisOperationService redisOperationService;

    // add redisPojo test
    @Test
    // 报错时回滚
    @Transactional
    public void addRedisPojoTest() {
        // 添加redisDTO, 确定添加成功
        RedisDTO redisDTO = new RedisDTO();
        redisDTO.setName("test");
        redisDTO.setHost("127.0.0.1");
        redisDTO.setPort("6379");

        int result = redisOperationService.addRedisDTO(redisDTO);
        System.out.println(result);
        Assertions.assertEquals(1, result);

        // 更新redisPojo, 确定更新成功
        redisDTO.setHost("127.0.0.2");
        int result1 = redisOperationService.updateRedisDTO(redisDTO);
        System.out.println(result1);
        Assertions.assertEquals(1, result1);

        // 确定host字段是127.0.0.2
        RedisDTO redisDTO1 = redisOperationService.getRedisDTO("test");
        System.out.println(redisDTO1);
        Assertions.assertEquals("127.0.0.2", redisDTO1.getHost());

        // 删除redisPojo
        redisOperationService.deleteRedisDTO("test");

        // 确定删除成功
        RedisDTO redisDTO3 = redisOperationService.getRedisDTO("test");
        System.out.println(redisDTO3);

        Assertions.assertNull(redisDTO3);
    }


}
