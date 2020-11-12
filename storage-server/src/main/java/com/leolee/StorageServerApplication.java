package com.leolee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.leolee.dao")
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class StorageServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageServerApplication.class, args);
    }

}
