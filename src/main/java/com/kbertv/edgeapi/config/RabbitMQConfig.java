package com.kbertv.edgeapi.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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

    @Bean(name="asyncRabbitTemplateForCurrencyService")
    public AsyncRabbitTemplate asyncRabbitTemplateForCurrencyService(RabbitTemplate rabbitTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(rabbitTemplate.getConnectionFactory());
        container.setQueueNames(currencyserviceResponseQueue);
        return new AsyncRabbitTemplate(rabbitTemplate, container);
    }



    @Bean(name="asyncRabbitTemplateForProductService")
    public AsyncRabbitTemplate asyncRabbitTemplateForProductService(RabbitTemplate rabbitTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(rabbitTemplate.getConnectionFactory());
        container.setQueueNames(productserviceResponseQueue);
        return new AsyncRabbitTemplate(rabbitTemplate, container);
    }

}
