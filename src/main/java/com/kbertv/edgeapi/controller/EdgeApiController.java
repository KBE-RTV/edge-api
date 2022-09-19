package com.kbertv.edgeapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbertv.edgeapi.client.Receiver;
import com.kbertv.edgeapi.client.Sender;
import com.kbertv.edgeapi.model.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class EdgeApiController {

    private ObjectMapper objectMapper;
    private Sender sender;
    private Receiver receiver;

    public EdgeApiController(Sender sender, Receiver receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${productservice.routing.call.key}")
    private String productserviceCallRoutingKey;

    private static RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        EdgeApiController.rabbitTemplate = rabbitTemplate;
    }
/*
    @PostConstruct
    public void init() {
        sender = new Sender();
        receiver = new Receiver();
    }

 Just to test whether keycloak works
    @GetMapping("/")
    public String index(Principal principal) {
        return principal.getName();
    }
*/

// requests to ps
    @GetMapping("/allcomponents")
    public void sendAllComponentsRequestToProductService() {
        ProductServiceRequestDTO allComponentsRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), null, "component");
        rabbitTemplate.convertAndSend(exchange, productserviceCallRoutingKey, allComponentsRequestDTO);
    }

    //TODO: Frontend: use "detailID" key to send the UUID
    @GetMapping("/detailcomponent")
    public void sendDetailComponentRequestToProductService(@RequestBody String detailID) {
        List<UUID> componentUUIDList = Arrays.asList(UUID.fromString(detailID));
        ProductServiceRequestDTO detailComponentRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), componentUUIDList, "component");
        rabbitTemplate.convertAndSend(exchange, productserviceCallRoutingKey, detailComponentRequestDTO);
    }

    @GetMapping("/allproducts")
    public void sendAllProductsRequestToProductService() {
        ProductServiceRequestDTO allProductsRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), null, "product");
        rabbitTemplate.convertAndSend(exchange, productserviceCallRoutingKey, allProductsRequestDTO);
    }

    //TODO: Frontend: use "detailID" key to send the UUID
    @GetMapping("/detailproduct")
    public void sendDetailProductRequestToProductService(@RequestBody String detailID) {
        List<UUID> productUUIDList = Arrays.asList(UUID.fromString(detailID));
        ProductServiceRequestDTO detailProductRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), productUUIDList, "product");
        rabbitTemplate.convertAndSend(exchange, productserviceCallRoutingKey, detailProductRequestDTO);
    }

/*
    //TODO: Frontend: use "name" and "celestialBodies" key
    @PostMapping("/createproduct")
    public void sendCreateProductRequestToProductService(@RequestBody CreatedPlanetarySystem createdPlanetarySystem) {
        ArrayList<UUID> celestialBodiesUUID = new ArrayList<>();
        String[] celestialBodiesUUIDasString = createdPlanetarySystem.getCelestialBodies();
        for (String celestialBody : celestialBodiesUUIDasString) {
            celestialBodiesUUID.add(UUID.fromString(celestialBody));
        }

        PlanetarySystem newPlanetarySystem = new PlanetarySystem();
        newPlanetarySystem.setId(UUID.randomUUID());
        newPlanetarySystem.setName(createdPlanetarySystem.getName());
        newPlanetarySystem.setCelestialBodies(celestialBodiesUUID);

        rabbitTemplate.convertAndSend(exchange, callRoutingKey, newPlanetarySystem);
    }
*/

    // convert currency for all products page:
    @GetMapping("/convertcurrencies")
    public String sendProductsToConvertCurrencies() {
        objectMapper = new ObjectMapper();

        CelestialBody celestialBody1 = new CelestialBody(UUID.randomUUID(), "Sun", 1, 3.50f, "sun", 0, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
        CelestialBody celestialBody2 = new CelestialBody(UUID.randomUUID(), "Earth", 1, 3.50f, "planet", 0, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

        ArrayList<CelestialBody> celestialBodies = new ArrayList<CelestialBody>();
        celestialBodies.add(celestialBody1);
        celestialBodies.add(celestialBody2);

        PlanetarySystem planetarySystem1 = new PlanetarySystem(UUID.randomUUID(), "MyPlanetarySystem", "Ricky", celestialBodies, 0f);
        PlanetarySystem planetarySystem2 = new PlanetarySystem(UUID.randomUUID(), "MyPlanetarySystem2", "Ricky", celestialBodies, 0f);

        ArrayList<PlanetarySystem> planetarySystems = new ArrayList<PlanetarySystem>();
        planetarySystems.add(planetarySystem1);
        planetarySystems.add(planetarySystem2);

        planetarySystem1.setPrice(70.0f);
        planetarySystem2.setPrice(80.0f);

        CurrencyMessageDTO currencyMessageDTO = new CurrencyMessageDTO(UUID.randomUUID(), planetarySystems,"Euro", "Dollar");

        try {
            sender.sendProductsToCurrencyService(objectMapper.writeValueAsString(currencyMessageDTO));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return receiver.receiveConvertedCurrencies();
    }
}
