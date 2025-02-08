package com.wzy.quanyoumall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.wzy.quanyoumall.ware.mapper")
public class QuanyoumallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanyoumallWareApplication.class, args);
    }

}
