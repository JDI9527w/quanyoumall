package com.wzy.quanyoumall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableDiscoveryClient
@SpringBootApplication
public class QuanyoumallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanyoumallOrderApplication.class, args);
    }

}
