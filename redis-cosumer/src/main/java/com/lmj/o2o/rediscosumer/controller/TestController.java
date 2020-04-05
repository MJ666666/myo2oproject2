package com.lmj.o2o.rediscosumer.controller;

import com.lmj.o2o.service.RedisService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: TestController
 * Description:
 * date: 2020/4/5 10:39
 *
 * @author MJ
 */
@RestController
public class TestController {

    @Reference(version = "${demo.service.version}")
    private RedisService redisService;


    @GetMapping("/store/{key}/{value}")
    public String storeTest(@PathVariable("key") String key,@PathVariable("value") String value) {
        return redisService.storeValue(key, value);
    }
}
