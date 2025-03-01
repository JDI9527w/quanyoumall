package com.wzy.quanyoumall.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "quanyoumall.thread")
@Component
@Data
public class ThreadPoolConfigurationProperties {
    private Integer coreSize;
    private Integer maxSize;
    private Long keepAliveTime;
}
