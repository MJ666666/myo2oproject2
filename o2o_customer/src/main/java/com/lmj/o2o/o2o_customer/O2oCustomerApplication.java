package com.lmj.o2o.o2o_customer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lmj.o2o")
@MapperScan("com.lmj.o2o.dao")
public class O2oCustomerApplication {

    public static void main(String[] args)

    {
        SpringApplication.run(O2oCustomerApplication.class, args);
    }

}
