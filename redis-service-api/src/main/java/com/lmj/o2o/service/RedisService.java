package com.lmj.o2o.service;

/**
 * ClassName: RedisService
 * Description:
 * date: 2020/4/5 9:01
 *
 * @author MJ
 */
public interface RedisService {


        String getValue(String key);

        String storeValue(String key,String value);

        Long expireKey(String key,int time);

        Long delKey(String key);

        boolean existKey(String key);

        String get(String key);



}
