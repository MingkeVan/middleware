package com.cffex.middleware.service;

import com.cffex.middleware.bean.RedisDTO;
import com.cffex.middleware.bean.RedisPojo;

import java.util.List;

public interface RedisOperationService {

    // 实现redisDTO对象的增删改查
    int addRedisDTO(RedisDTO redisDTO);

    int deleteRedisDTO(String name);

    int updateRedisDTO(RedisDTO redisDTO);

    List<RedisDTO> listRedisDTO();

}
