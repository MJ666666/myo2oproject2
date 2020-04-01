package com.lmj.o2o.service;

import com.lmj.o2o.service.fallback.RedisServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ClassName: RedisService
 * Description:
 * date: 2020/3/30 10:21
 *
 * @author MJ
 */

@FeignClient(value = "redis-service",fallback = RedisServiceFallBack.class)
public interface RedisService {

    @GetMapping(value="/fetch/{key}")
     String getRedisValue(@PathVariable String key);

    @GetMapping(value="/store/{key}/{value}")
     String storeValue(@PathVariable("key") String key, @PathVariable("value") String value);


    @GetMapping(value="/delete/{key}")
    Long deleteKey(@PathVariable("key") String key);


    @GetMapping(value="/exist/{key}")
    boolean existKey(@PathVariable("key") String key);

    @GetMapping(value="/get/{key}")
    String get(@PathVariable("key") String key);

    @GetMapping(value="/expire/{key}/{time}")
    Long expireKey(@PathVariable("key") String key, @PathVariable("time") int time);

    @RequestMapping(value = "/getIp", method = RequestMethod.GET)
    public String getIpAndPort();
}
