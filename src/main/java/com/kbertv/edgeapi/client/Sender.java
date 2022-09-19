package com.kbertv.edgeapi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbertv.edgeapi.config.RabbitMQConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Value("${rabbitmq.exchange.name}")
    private String topicExchangeName;

    @Value("${productservice.routing.call.key}")
    private String productserviceCallRoutingKey;

    @Value("${currencyservice.routing.call.key}")
    private String currencyserviceCallRoutingKey;

    private static RabbitTemplate rabbitTemplate;
    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        Sender.rabbitTemplate = rabbitTemplate;
    }

    public void sendProductsToCurrencyService(String message)
    {
        System.out.println(currencyserviceCallRoutingKey);
        rabbitTemplate.convertAndSend(topicExchangeName, currencyserviceCallRoutingKey, message);

        System.out.println("SENT product to currency-service \n");
    }

    public void sendRequestToProductService(String request)
    {
        rabbitTemplate.convertAndSend(topicExchangeName, productserviceCallRoutingKey, request);

        System.out.println("SENT request to product-service \n");
    }
}
