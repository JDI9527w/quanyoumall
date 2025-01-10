package com.wzy.quanyoumall.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class QuanyoumallThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanyoumallThirdPartyApplication.class, args);
    }

}
