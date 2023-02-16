package com.mw.middleware.controller;

import com.mw.middleware.bean.RedisDTO;
import com.mw.middleware.bean.ResultResponse;
import com.mw.middleware.exception.BusinessException;
import com.mw.middleware.service.RedisOperationService;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RedisOperationController {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RedisOperationController.class);

    @Resource
    RedisOperationService redisOperationService;

    // add redisPojo
    @RequestMapping(value = "/redis", method = RequestMethod.POST, produces = "application/json")
    public ResultResponse add(@RequestBody RedisDTO redisDTO) {
        checkParams(redisDTO);

        // check name is exist
        RedisDTO redisDTO1 = redisOperationService.getRedisDTO(redisDTO.getName());
        if (redisDTO1 != null) {
            logger.error("redis resource is exist");
            throw new BusinessException("redis resource is exist");
        }

        // add redisPojo
        redisOperationService.addRedisDTO(redisDTO);

        return ResultResponse.success();
    }

    // delete redisPojo
    @PostMapping(value = "/redis/delete")
    public ResultResponse<Void> delete(@RequestBody Map<String,String> params) {
        // check params
        if (params == null || params.size() == 0) {
            logger.error("params is null or empty");
            throw new BusinessException("params is null or empty");
        }

        String name = params.get("name");
        // check name
        if (name == null || name.length() == 0) {
            logger.error("name is null or empty");
            throw new BusinessException("name is null or empty");
        }

        //  redisDTO not exist
        RedisDTO redisDTO = redisOperationService.getRedisDTO(name);
        if (redisDTO == null) {
            logger.error("redis resource not exist");
            throw new BusinessException("redis resource not exist");
        }

        // delete redisPojo
        redisOperationService.deleteRedisDTO(name);
        return ResultResponse.success();
    }

    // update redisPojo
    @PostMapping(value = "/redis/update", produces = "application/json")
    public ResultResponse update(String name, @RequestBody RedisDTO redisDTO) {

        name = redisDTO.getName();
        // check name
        if (name == null || name.length() == 0) {
            logger.error("name is null or empty");
            throw new BusinessException("name is null or empty");
        }

        // check redisDTO
        if (redisDTO == null) {
            logger.error("redisDTO is null");
            throw new BusinessException("redisDTO is null");
        }



        // 判断redisDTO是否存在
        RedisDTO redisDTO1 = redisOperationService.getRedisDTO(redisDTO.getName());
        if (redisDTO1 == null) {
            logger.error("redis resource not exist");
            throw new BusinessException("redis resource not exist");
        }

        // 判断host是否为空
        if (!StringUtils.isEmpty(redisDTO.getHost())) {
            // 判断host合法
            if (!isValidIp(redisDTO.getHost())) {
                logger.error("host is not valid");
                throw new BusinessException("host is not valid");
            }
            redisDTO1.setHost(redisDTO.getHost());
        }

        // 判断port是否为空
        if (!StringUtils.isEmpty(redisDTO.getPort())) {
            // 判断port合法
            if (!isValidPort(redisDTO.getPort())) {
                logger.error("port is not valid");
                throw new BusinessException("port is not valid");
            }
            redisDTO1.setPort(redisDTO.getPort());
        }

        // update redisPojo
        redisOperationService.updateRedisDTO(redisDTO1);

        return ResultResponse.success();
    }

    // get redisDTO
    @GetMapping(value = "/redis/{name}", produces = "application/json")
    public ResultResponse<RedisDTO> get(@PathVariable String name) {
        // check name
        if (name == null || name.length() == 0) {
            logger.error("name is null or empty");
            throw new BusinessException("name is null or empty");
        }

        // get redisDTO
        RedisDTO redisDTO = redisOperationService.getRedisDTO(name);

        // redisDTO not exist
        if (redisDTO == null) {
            logger.error("redis resource not exist");
            throw new BusinessException("redis resource not exist");
        }

        return ResultResponse.success(redisDTO);

    }

    // list redisPojo
    @GetMapping(value = "/redis", produces = "application/json")
    public ResultResponse<List<RedisDTO>> list() {
        return ResultResponse.success(redisOperationService.listRedisDTO());
    }

    // check redisDTO param is legal
    public void checkParams(RedisDTO redisDTO) {
        // check redisDTO
        if (redisDTO == null) {
            logger.error("redisDTO is null");
            throw new BusinessException("redisDTO is null");
        }

        // check name
        if (redisDTO.getName() == null || redisDTO.getName().length() == 0) {
            logger.error("name is null or empty");
            throw new BusinessException("name is null or empty");
        }

        // 判断host是否为空
        if (redisDTO.getHost() == null || redisDTO.getHost().length() == 0) {
            logger.error("host is null or empty");
            throw new BusinessException("host is null or empty");
        }

        // 判断host是ip地址
        String ip = redisDTO.getHost();

        if (!isValidIp(ip)) {
            logger.error("host is not ip address");
            throw new BusinessException("host is not ip address");
        }

        // 判断port是否为空
        if (redisDTO.getPort() == null || redisDTO.getPort().length() == 0) {
            logger.error("port is null or empty");
            throw new BusinessException("port is null or empty");
        }

        // 判断port是否为数字
        if (!isValidPort(redisDTO.getPort())) {
            logger.error("port is not number");
            throw new BusinessException("port is not number");
        }
    }

    // isValidIp
    public boolean isValidIp(String ip) {
        String[] ipArr = ip.split("\\.");
        if (ipArr.length != 4) {
            return false;
        }
        for (String ipItem : ipArr) {
            if (ipItem.length() > 3) {
                return false;
            }
            try {
                int ipItemInt = Integer.parseInt(ipItem);
                if (ipItemInt > 255 || ipItemInt < 0) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    // check port is valid
    public boolean isValidPort(String port) {
        try {
            Integer.parseInt(port);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
