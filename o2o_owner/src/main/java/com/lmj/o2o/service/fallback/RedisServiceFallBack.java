package com.lmj.o2o.service.fallback;

import com.lmj.o2o.service.RedisService;
import org.springframework.stereotype.Component;

/**
 * ClassName: RedisServiceFallBack
 * Description:
 * date: 2020/3/30 13:47
 *
 * @author MJ
 */
@Component
public class RedisServiceFallBack implements RedisService {


    @Override
    public String getRedisValue(String key) {
        System.out.println("降级啦："+key);
        return "error";
    }

    @Override
    public String storeValue(String key, String value) {
        System.out.println("降级啦："+key+","+value);

        return "error";
    }

    @Override
    public Long deleteKey(String key) {
        System.out.println("降级啦："+key);

        return null;
    }

    @Override
    public boolean existKey(String key) {
        System.out.println("降级啦："+key);
        return false;
    }

    @Override
    public String get(String key) {
        System.out.println("降级啦："+key);
        return "error";
    }

    @Override
    public Long expireKey(String key, int time) {
        System.out.println("降级啦："+key+","+time);
        return null;
    }

    @Override
    public String getIpAndPort() {
        System.out.println("拿不到端口，降级啦");
        return "error";
    }
}
