package com.lmj.o2o.esservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsServiceApplication.class, args);
    }

}
