package com.lmj.o2o.o2o_customer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.lmj.o2o")
@MapperScan("com.lmj.o2o.dao")
@EnableCaching
@EnableDiscoveryClient
@EnableFeignClients("com.lmj.o2o")
public class O2oCustomerApplication {

    public static void main(String[] args)

    {
        SpringApplication.run(O2oCustomerApplication.class, args);
    }

}
