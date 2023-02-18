package com.mw.middleware.mapper;

import com.mw.middleware.bean.RedisInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RedisInfoMapper {
    List<RedisInfo> selectAllRedisInfo();

    RedisInfo selectRedisInfoById(Long id);

    Long insertRedisInfo(RedisInfo redisInfo);

    int updateRedisInfo(RedisInfo redisInfo);

    int deleteRedisInfo(Long id);
}

