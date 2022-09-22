package com.kbertv.edgeapi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbertv.edgeapi.config.RabbitMQConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class Sender {

    @Value("${rabbitmq.exchange.name}")
    private String topicExchangeName;

    @Value("${productservice.routing.call.key}")
    private String productserviceCallRoutingKey;

    @Value("${currencyservice.routing.call.key}")
    private String currencyserviceCallRoutingKey;

    private static AsyncRabbitTemplate asyncRabbitTemplateForCurrencyService;

    private static AsyncRabbitTemplate asyncRabbitTemplateForProductService;

    @Autowired
    @Qualifier("asyncRabbitTemplateForCurrencyService")
    public void setAsyncRabbitTemplateForCurrencyService(AsyncRabbitTemplate asyncRabbitTemplateForCurrencyService) {
        Sender.asyncRabbitTemplateForCurrencyService = asyncRabbitTemplateForCurrencyService;
    }


    @Autowired
    @Qualifier("asyncRabbitTemplateForProductService")
    public void setAsyncRabbitTemplateForProductService(AsyncRabbitTemplate asyncRabbitTemplateForProductService) {
        Sender.asyncRabbitTemplateForProductService = asyncRabbitTemplateForProductService;
    }


    public String sendProductsToCurrencyService(String requestMessage) throws ExecutionException, InterruptedException {
        final String responseMessage;

        AsyncRabbitTemplate.RabbitConverterFuture<String> future =
                asyncRabbitTemplateForCurrencyService.convertSendAndReceive(topicExchangeName, currencyserviceCallRoutingKey, requestMessage);
        log.info("SENT: " + requestMessage);

        return future.get();
    }

    public String sendRequestToProductService(String requestMessage) throws ExecutionException, InterruptedException {
        final String responseMessage;

        AsyncRabbitTemplate.RabbitConverterFuture<String> future =
                asyncRabbitTemplateForProductService.convertSendAndReceive(topicExchangeName, currencyserviceCallRoutingKey, requestMessage);
        log.info("SENT: " + requestMessage);

        return future.get();
    }


/*
    public String sendProductsToCurrencyService(String message) throws ExecutionException, InterruptedException {
        ListenableFuture<String> listenableFuture =
                asyncRabbitTemplate.convertSendAndReceiveAsType(
                        topicExchangeName,
                        currencyserviceCallRoutingKey,
                        message,
                        new ParameterizedTypeReference<>() {
                        });

        System.out.println("SENT and RECEIVED product to currency-service \n"+listenableFuture.get());
        return listenableFuture.get();
    }

 */
/*
    public void sendRequestToProductService(String request)
    {
        rabbitTemplate.convertAndSend(topicExchangeName, productserviceCallRoutingKey, request);

        System.out.println("SENT request to product-service \n");
    }
 */
}
