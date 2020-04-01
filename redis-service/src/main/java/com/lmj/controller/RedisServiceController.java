package com.lmj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: RedisServiceController
 * Description:
 * date: 2020/3/29 15:24
 *
 * @author MJ
 */
@RestController
public class RedisServiceController {

    @Autowired
    private JedisCluster jedisCluster;

    @RequestMapping(value = "/fetch/{key}",method = RequestMethod.GET)
    public String getValue(@PathVariable("key") String key) {
        return jedisCluster.get(key);
    }


    @RequestMapping(value="/store/{key}/{value}",method = RequestMethod.GET)
    public String storeValue(@PathVariable("key") String key,@PathVariable("value") String value) {
        return jedisCluster.set(key,value);
    }
    @RequestMapping(value="/expire/{key}/{time}",method = RequestMethod.GET)
    public Long expireKey(@PathVariable("key") String key,@PathVariable("time") int time) {
        return jedisCluster.expire(key,time);
    }

    @RequestMapping(value="/delete/{key}",method = RequestMethod.GET)
    public Long delKey(@PathVariable("key") String key) {
        return jedisCluster.del(key);
    }

    @RequestMapping(value="/exist/{key}",method = RequestMethod.GET)
    public boolean existKey(@PathVariable("key") String key) {
        return jedisCluster.exists(key);
    }

    @RequestMapping(value="/get/{key}",method = RequestMethod.GET)
    public String get(@PathVariable("key") String key) {
        return jedisCluster.get(key);
    }

    @RequestMapping(value = "/getIp", method = RequestMethod.GET)
    public String getPortAndIp(HttpServletRequest request) {
        String localAddr = request.getLocalAddr();
        int localPort = request.getLocalPort();
        String ipAndPort=localAddr+":"+localPort;
        return ipAndPort;
    }

}
