package com.wzy.quanyoumall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class QuanyoumallGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanyoumallGatewayApplication.class, args);
    }

}
