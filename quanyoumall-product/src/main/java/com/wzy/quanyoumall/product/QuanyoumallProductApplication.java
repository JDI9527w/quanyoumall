package com.wzy.quanyoumall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.wzy.quanyoumall.product.mapper")
@EnableTransactionManagement
public class QuanyoumallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanyoumallProductApplication.class, args);
    }

}
