package com.wzy.quanyoumall.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MyRabbitConfig {
    @Bean
    public TopicExchange topicExchange() {
        //String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        return new TopicExchange("order.event.exchange", true, false);
    }

    @Bean
    public Queue orderDelayQueue() {
//        String name, boolean durable, boolean exclusive, boolean autoDelete,@Nullable Map<String, Object> arguments
        HashMap<String, Object> argsMap = new HashMap<>();
//        x-message-ttl
//        x-dead-letter-exchange
//        x-dead-letter-routing-key
//        argsMap.put("x-message-ttl", 1800000);
        argsMap.put("x-message-ttl", 60000);
        argsMap.put("x-dead-letter-exchange", "order.event.exchange");
        argsMap.put("x-dead-letter-routing-key", "order.release.order");
        return new Queue("order.delay.queue", true, false, false, argsMap);
    }

    @Bean
    public Queue orderReleaseQueue() {
        return new Queue("order.release.queue", true, false, false, null);
    }

    @Bean
    public Binding bindingDelayQueue(TopicExchange topicExchange, Queue orderDelayQueue) {
        return BindingBuilder.bind(orderDelayQueue).to(topicExchange).with("order.create.order");
    }

    @Bean
    public Binding bindingReleaseQueue(TopicExchange topicExchange, Queue orderReleaseQueue) {
        return BindingBuilder.bind(orderReleaseQueue).to(topicExchange).with("order.release.order");
    }
}
