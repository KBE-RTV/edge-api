package com.kbertv.edgeapi.client;

import com.kbertv.edgeapi.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Receiver {
/*
    private static RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        Receiver.rabbitTemplate = rabbitTemplate;
    }

    @Value("${currencyservice.queue.response.name}")
    private String currencyserviceResponseQueue;

    @RabbitListener(queues = RabbitMQConfig.CURRENCY_SERVICE_RESPONSE_QUEUE_NAME)
    public void receiveConvertedAmount(String convertedAmountsAsJson) {

        System.out.println("RECEIVED convertedAmounts: " + convertedAmountsAsJson);
    }

    @RabbitListener(queues = {"${productservice.queue.response.name}"})
    public void receiveProducts(String productsAsJson) {

        System.out.println("RECEIVED products: " + productsAsJson + "\n");
    }

    public String receiveConvertedCurrencies() {
        System.out.println(currencyserviceResponseQueue);
        String message = (String) rabbitTemplate.receiveAndConvert(currencyserviceResponseQueue);

        System.out.println("RECEIVED products with converted currencies: " + message);
        return message;
    }


 */
    //TODO: add method to receive the ps response
}
