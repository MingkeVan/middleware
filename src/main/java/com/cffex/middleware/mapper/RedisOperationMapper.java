package com.cffex.middleware.mapper;

import com.cffex.middleware.bean.RedisPojo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RedisOperationMapper {

    // insert into t_redis(name, host, port, password) values(#{name}, #{host}, #{port}, #{password})
    @Insert("insert into t_redis(name, host, port, username, password) values(#{name}, #{host}, #{port},#{username}, #{password})")
    int addRedisPojo(RedisPojo redisPojo);

    int deleteRedisPojo(String name);

    int updateRedisPojo(RedisPojo redisPojo);

    RedisPojo getRedisPojo(String name);

    List<RedisPojo> listRedisPojo();

}
