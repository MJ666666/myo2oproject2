package com.lmj.o2o.redisservice.service;

import com.lmj.o2o.service.RedisService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

/**
 * ClassName: RedisServiceImpl
 * Description:
 * date: 2020/4/5 8:38
 *
 * @author MJ
 */
@Service(version = "${demo.service.version}")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisCluster jedisCluster;


    @Override
    public String getValue(String key) {
        return jedisCluster.get(key);

    }

    @Override
    public String storeValue(String key, String value) {
        return jedisCluster.set(key,value);

    }

    @Override
    public Long expireKey(String key, int time) {
        return jedisCluster.expire(key,time);
    }

    @Override
    public Long delKey(String key) {
        return jedisCluster.del(key);

    }

    @Override
    public boolean existKey(String key) {
        return jedisCluster.exists(key);

    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);

    }

}
