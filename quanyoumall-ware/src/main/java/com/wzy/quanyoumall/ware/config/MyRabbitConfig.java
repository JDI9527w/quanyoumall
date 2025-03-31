package com.wzy.quanyoumall.ware.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyRabbitConfig {
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("stock.event.exchange", true, false, null);
    }

    @Bean
    public Queue stockDelayQueue() {
        Map<String, Object> argsMap = new HashMap<>();
//        argsMap.put("x-message-ttl", 1860000);
        // 库存解锁可以比订单解锁慢一些.
        argsMap.put("x-message-ttl", 80000);
        argsMap.put("x-dead-letter-exchange", "stock.event.exchange");
        argsMap.put("x-dead-letter-routing-key", "stock.release.stock");
        return new Queue("stock.delay.queue", true, false, false, argsMap);
    }

    @Bean
    public Queue stockReleaseQueue() {
        return new Queue("stock.release.queue");
    }

    @Bean
    public Binding bindingDelayQueue(Queue stockDelayQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(stockDelayQueue).to(topicExchange).with("stock.finish");
    }

    @Bean
    public Binding bindingReleaseQueue(Queue stockReleaseQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(stockReleaseQueue).to(topicExchange).with("stock.release.#");
    }
}
