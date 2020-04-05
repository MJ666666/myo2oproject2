package com.lmj.o2o.rediscosumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lmj.o2o.rediscosumer")
public class RedisCosumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisCosumerApplication.class, args);
    }

}
