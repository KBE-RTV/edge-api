package com.kbertv.edgeapi.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String topicExchangeName;

    //Response queue from productservice
    @Value("${productservice.queue.response.name}")
    private String productserviceResponseQueue;
    @Value("${productservice.routing.response.key}")
    private String productserviceResponseRoutingKey;

    //Response queue from currencyservice
    @Value("${currencyservice.queue.response.name}")
    private String currencyserviceResponseQueue;
    @Value("${currencyservice.routing.response.key}")
    private String currencyserviceResponseRoutingKey;

    public static TopicExchange exchange;

    @Autowired
    public void setExchange(@Lazy TopicExchange exchange) {
        RabbitMQConfig.exchange = exchange;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    // Response queue from currency service
    @Bean
    Queue currencyResponseQueue() {
        return new Queue(currencyserviceResponseQueue, false);
    }

    @Bean
    Binding currencyBinding() {
        return BindingBuilder.bind(currencyResponseQueue()).to(exchange).with(currencyserviceResponseRoutingKey);
    }

    // Response queue from product service
    @Bean
    Queue productResponseQueue() {
        return new Queue(productserviceResponseQueue, false);
    }

    @Bean
    Binding productBinding() {
        return BindingBuilder.bind(productResponseQueue()).to(exchange).with(productserviceResponseRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter producerMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    //"final" made the app exit before -> maybe delete this?
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerMessageConverter());
        return rabbitTemplate;
    }
}
