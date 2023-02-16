package com.mw.middleware.service;

import com.mw.middleware.bean.RedisDTO;
import com.mw.middleware.bean.RedisPojo;
import com.mw.middleware.mapper.RedisOperationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RedisOperationServiceImpl implements RedisOperationService {
    @Resource
    RedisOperationMapper redisOperationMapper;

    @Override
    public int addRedisDTO(RedisDTO redisDTO) {
        RedisPojo redisPojo = new RedisPojo();
        redisPojo.setName(redisDTO.getName());
        redisPojo.setHost(redisDTO.getHost());
        redisPojo.setPort(Integer.parseInt(redisDTO.getPort()));
        redisPojo.setUsername(redisDTO.getUsername());
        redisPojo.setPasswd(redisDTO.getPassword());
        return redisOperationMapper.addRedisPojo(redisPojo);
    }

    @Override
    public int deleteRedisDTO(String name) {
        return redisOperationMapper.deleteRedisPojo(name);
    }

    @Override
    public int updateRedisDTO(RedisDTO redisDTO) {
        RedisPojo redisPojo = new RedisPojo();
        redisPojo.setName(redisDTO.getName());
        redisPojo.setHost(redisDTO.getHost());
        redisPojo.setPort(Integer.parseInt(redisDTO.getPort()));
        redisPojo.setUsername(redisDTO.getUsername());
        redisPojo.setPasswd(redisDTO.getPassword());
        return redisOperationMapper.updateRedisPojo(redisPojo);
    }

    public RedisDTO getRedisDTO(String name) {
        RedisPojo redisPojo = redisOperationMapper.getRedisPojo(name);
        if (redisPojo == null) {
            return null;
        }
        return new RedisDTO(redisPojo);
    }



    @Override
    public List<RedisDTO> listRedisDTO() {

        // list redisPojo
        List<RedisPojo> redisPojos = redisOperationMapper.listRedisPojo();

        // check redisPojos
        if (redisPojos == null || redisPojos.size() == 0) {
            // 返回空list
            return Collections.emptyList();
        }

        // convert redisPojo to redisDTO
        return redisPojos.stream().map(RedisDTO::new).collect(Collectors.toList());

    }
}
