package com.kbertv.edgeapi.controller;

import com.kbertv.edgeapi.model.CreatedPlanetarySystem;
import com.kbertv.edgeapi.model.PlanetarySystem;
import com.kbertv.edgeapi.model.ProductServiceRequestDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class EdgeApiController {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.call.key}")
    private String callRoutingKey;

    private static RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        EdgeApiController.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/")
    public String index(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/allcomponents")
    public void sendAllComponentsRequestToProductService() {
        ProductServiceRequestDTO allComponentsRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), null, "component");
        rabbitTemplate.convertAndSend(exchange, callRoutingKey, allComponentsRequestDTO);
    }

    //TODO: Frontend: use "detailID" key to send the UUID
    @GetMapping("/detailcomponent")
    public void sendDetailComponentRequestToProductService(@RequestBody String detailID) {
        List<UUID> componentUUIDList = Arrays.asList(UUID.fromString(detailID));
        ProductServiceRequestDTO detailComponentRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), componentUUIDList, "component");
        rabbitTemplate.convertAndSend(exchange, callRoutingKey, detailComponentRequestDTO);
    }

    @GetMapping("/allproducts")
    public void sendAllProductsRequestToProductService() {
        ProductServiceRequestDTO allProductsRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), null, "product");
        rabbitTemplate.convertAndSend(exchange, callRoutingKey, allProductsRequestDTO);
    }

    //TODO: Frontend: use "detailID" key to send the UUID
    @GetMapping("/detailproduct")
    public void sendDetailProductRequestToProductService(@RequestBody String detailID) {
        List<UUID> productUUIDList = Arrays.asList(UUID.fromString(detailID));
        ProductServiceRequestDTO detailProductRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), productUUIDList, "product");
        rabbitTemplate.convertAndSend(exchange, callRoutingKey, detailProductRequestDTO);
    }

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

}
