package com.kbertv.edgeapi.client;

import com.kbertv.edgeapi.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public
class Receiver {


    @RabbitListener(queues = RabbitMQConfig.CURRENCY_SERVICE_RESPONSE_QUEUE_NAME)
    public void receiveConvertedAmount(String convertedAmountsAsJson) {

        System.out.println("RECEIVED convertedAmounts: " + convertedAmountsAsJson);
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCT_SERVICE_RESPONSE_QUEUE_NAME)
    public void receiveProducts(String productsAsJson) {

        System.out.println("RECEIVED products: " + productsAsJson + "\n");
    }



}
