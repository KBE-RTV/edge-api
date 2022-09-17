package com.kbertv.edgeapi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbertv.edgeapi.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Sender {
    private static RabbitTemplate rabbitTemplate;

    private static ObjectMapper objectMapper;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        Sender.rabbitTemplate = rabbitTemplate;
    }

    public static void sendProductsToCurrencyService(String message)
    {
        rabbitTemplate.convertAndSend(RabbitMQConfig.exchange.getName(), RabbitMQConfig.CURRENCY_SERVICE_CALL_ROUTING_KEY, message);

        System.out.println("SENT product to currency-service \n");
    }

    public static void sendRequestToProductService(String request)
    {
        rabbitTemplate.convertAndSend(RabbitMQConfig.exchange.getName(), RabbitMQConfig.PRODUCT_SERVICE_CALL_ROUTING_KEY, request);

        System.out.println("SENT request to product-service");
    }
}
