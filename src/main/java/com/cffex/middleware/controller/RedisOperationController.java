package com.cffex.middleware.controller;

import com.cffex.middleware.bean.RedisDTO;
import com.cffex.middleware.bean.RedisPojo;
import com.cffex.middleware.mapper.RedisOperationMapper;
import com.cffex.middleware.service.RedisOperationService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/redis")
public class RedisOperationController {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RedisOperationController.class);

    @Resource
    RedisOperationService redisOperationService;

    // add redisPojo
    @RequestMapping("/add")
    public void add(RedisDTO redisDTO) {
        // check redisDTO
        if (redisDTO == null) {
            logger.error("redisDTO is null");
            return;
        }

        // check name
        if (redisDTO.getName() == null || redisDTO.getName().length() == 0) {
            logger.error("name is null or empty");
            return;
        }

        // 判断host是否为空
        if (redisDTO.getHost() == null || redisDTO.getHost().length() == 0) {
            logger.error("host is null or empty");
            throw new RuntimeException("host is null or empty");
        }

        // 判断host是ip地址
        String ip = redisDTO.getHost();
        String[] ipArr = ip.split("\\.");
        if (ipArr.length != 4) {
            logger.error("host is not ip address");
            throw new RuntimeException("host is not ip address");
        }
        for (String ipItem : ipArr) {
            if (ipItem.length() > 3) {
                logger.error("host is not ip address");
                throw new RuntimeException("host is not ip address");
            }
            try {
                int ipItemInt = Integer.parseInt(ipItem);
                if (ipItemInt > 255 || ipItemInt < 0) {
                    logger.error("host is not ip address");
                    throw new RuntimeException("host is not ip address");
                }
            } catch (NumberFormatException e) {
                logger.error("host is not ip address");
                throw new RuntimeException("host is not ip address");
            }
        }

        // 判断port是否为空
        if (redisDTO.getPort() == null || redisDTO.getPort().length() == 0) {
            logger.error("port is null or empty");
            throw new RuntimeException("port is null or empty");
        }

        // 判断port是否为数字
        try {
            Integer.parseInt(redisDTO.getPort());
        } catch (NumberFormatException e) {
            logger.error("port is not number");
            throw new RuntimeException("port is not number");
        }

        // check name is exist
        List<RedisDTO> redisDTOList = redisOperationService.listRedisDTO();
        List<RedisDTO> redisDTOList1 = redisDTOList.stream().filter(redisDTO1 -> redisDTO1.getName().equals(redisDTO.getName())).collect(Collectors.toList());
        if (redisDTOList1.size() > 0) {
            logger.error("name is exist");
            throw new RuntimeException("name is exist");
        }

        // add redisPojo
        redisOperationService.addRedisDTO(redisDTO);
    }

    // delete redisPojo
    @RequestMapping("/delete")
    public void delete(String name) {
        // check name
        if (name == null || name.length() == 0) {
            logger.error("name is null or empty");
            return;
        }

        // delete redisPojo
        redisOperationService.deleteRedisDTO(name);
    }

    // update redisPojo
    @RequestMapping("/update")
    public void updateR(RedisDTO redisDTO) {


        // update redisPojo
        redisOperationService.updateRedisDTO(redisDTO);
    }

    // list redisPojo
    @RequestMapping("/list")
    public List<RedisDTO> list() {
        return redisOperationService.listRedisDTO();
    }

}
