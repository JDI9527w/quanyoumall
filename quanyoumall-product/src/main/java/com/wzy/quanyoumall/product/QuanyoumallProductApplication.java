package com.wzy.quanyoumall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class QuanyoumallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanyoumallProductApplication.class, args);
    }

}
