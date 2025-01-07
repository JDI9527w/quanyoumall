package com.wzy.quanyoumall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class QuanyoumallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanyoumallCouponApplication.class, args);
    }

}
