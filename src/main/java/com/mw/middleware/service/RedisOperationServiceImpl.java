package com.mw.middleware.service;

import com.mw.middleware.bean.RedisDTO;
import com.mw.middleware.bean.RedisPojo;
import com.mw.middleware.bean.RedisStatus;
import com.mw.middleware.mapper.RedisOperationMapper;
import com.mw.middleware.job.RedisStatusScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RedisOperationServiceImpl implements RedisOperationService {
    @Resource
    RedisOperationMapper redisOperationMapper;

    @Autowired
    RedisStatusScheduler redisStatusScheduler;

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
        return new RedisDTO(redisPojo, getRedisStatus(redisPojo));
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
        return redisPojos.stream().map(x -> {

            RedisDTO redisDTO = new RedisDTO(x, getRedisStatus(x));
            return redisDTO;
        }).collect(Collectors.toList());

    }

    // getRedisStatus
    private RedisStatus getRedisStatus(RedisPojo redisPojo) {
        String key = redisStatusScheduler.generateKey(redisPojo);
        return redisStatusScheduler.getStatusCache().get(key);
    }
}
