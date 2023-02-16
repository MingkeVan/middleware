package com.mw.middleware.service;

import com.mw.middleware.bean.RedisDTO;

import java.util.List;

public interface RedisOperationService {

    // 实现redisDTO对象的增删改查
    int addRedisDTO(RedisDTO redisDTO);

    int deleteRedisDTO(String name);

    int updateRedisDTO(RedisDTO redisDTO);

    List<RedisDTO> listRedisDTO();

}
