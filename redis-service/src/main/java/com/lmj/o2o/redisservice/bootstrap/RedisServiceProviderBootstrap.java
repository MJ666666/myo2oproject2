package com.lmj.o2o.redisservice.bootstrap;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "com.lmj.o2o.redisservice")
public class RedisServiceProviderBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(RedisServiceProviderBootstrap.class, args);
    }
}