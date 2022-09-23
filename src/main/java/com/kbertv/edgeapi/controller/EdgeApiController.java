package com.kbertv.edgeapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbertv.edgeapi.client.Receiver;
import com.kbertv.edgeapi.client.Sender;
import com.kbertv.edgeapi.model.*;
import com.kbertv.edgeapi.model.DTO.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class EdgeApiController {

    private ObjectMapper objectMapper;

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

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
    // Just to test whether keycloak works
        @GetMapping("/")
        public String index(Principal principal) {
            return principal.getName();
        }
    */
    // requests to ps
    @GetMapping("/allcomponents")
    public String sendAllComponentsRequestToProductService(@RequestBody String frontEndDTOAsJon) {
        objectMapper = new ObjectMapper();

        try {
            FrontendDTO frontendDTO = objectMapper.readValue(frontEndDTOAsJon, FrontendDTO.class);

            ProductServiceRequestDTO detailProductRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), null, "celestialBody");

            String responseFromProductService = "{\"requestID\":\"99fa47f3-5569-46b9-a6cf-4110b8e26a61\",\"celestialBody\":[{\"id\":\"51cafbb6-e909-4851-8f14-d1994fdcc500\",\"name\":\"Sun\",\"amount\":3,\"price\":156.0,\"type\":\"sun\",\"orbital\":0,\"radius\":695700.0,\"volume\":1.40999997E18,\"mass\":1.9885E30,\"gravity\":274.0,\"rotationVelocity\":1997.0,\"orbitalVelocity\":0.0,\"surfaceTemperature\":1.57E7}]}";

            // String responseFromProductService = sender.sendRequestToProductService(objectMapper.writeValueAsString(detailProductRequestDTO));

            CelestialBodyDetailDTO celestialBodyDetailDTO = objectMapper.readValue(responseFromProductService, CelestialBodyDetailDTO.class);

            CurrencyCelestialBodyMessageDTO currencyMessageDTO = new CurrencyCelestialBodyMessageDTO(UUID.randomUUID(), celestialBodyDetailDTO.getCelestialBody(), "Euro", frontendDTO.getCurrencyToConvertTo());

            return sender.sendProductsToCurrencyService(objectMapper.writeValueAsString(currencyMessageDTO));
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: Frontend: use "detailID" key to send the UUID
    @GetMapping("/detailcomponent")
    public String sendDetailComponentRequestToProductService(@RequestBody String frontEndDTOAsJon) {
        objectMapper = new ObjectMapper();

        try {
            FrontendDTO frontendDTO = objectMapper.readValue(frontEndDTOAsJon, FrontendDTO.class);

            ProductServiceRequestDTO detailProductRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), frontendDTO.getDetailID(), "celestialBody");

            String responseFromProductService = "{\"requestID\":\"99fa47f3-5569-46b9-a6cf-4110b8e26a61\",\"celestialBody\":[{\"id\":\"51cafbb6-e909-4851-8f14-d1994fdcc500\",\"name\":\"Sun\",\"amount\":3,\"price\":156.0,\"type\":\"sun\",\"orbital\":0,\"radius\":695700.0,\"volume\":1.40999997E18,\"mass\":1.9885E30,\"gravity\":274.0,\"rotationVelocity\":1997.0,\"orbitalVelocity\":0.0,\"surfaceTemperature\":1.57E7}]}";

            //String responseFromProductService = sender.sendRequestToProductService(objectMapper.writeValueAsString(detailProductRequestDTO));

            CelestialBodyDetailDTO celestialBodyDetailDTO = objectMapper.readValue(responseFromProductService, CelestialBodyDetailDTO.class);

            CurrencyCelestialBodyMessageDTO currencyMessageDTO = new CurrencyCelestialBodyMessageDTO(UUID.randomUUID(), celestialBodyDetailDTO.getCelestialBody(), "Euro", frontendDTO.getCurrencyToConvertTo());

            return sender.sendProductsToCurrencyService(objectMapper.writeValueAsString(currencyMessageDTO));
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/allproducts")
    public String sendProductsRequestToProductServiceTest(@RequestBody String frontEndDTOAsJon) {

        objectMapper = new ObjectMapper();

        try {
            FrontendDTO frontendDTO = objectMapper.readValue(frontEndDTOAsJon, FrontendDTO.class);

            ProductServiceRequestDTO detailProductRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), null, "planetarySystem");

            String responseFromProductService = "{\"requestID\":\"9dfb5676-4a6f-4ba6-9d95-9ab731578051\",\"planetarySystems\":[{\"id\":\"aec3e84c-1c5c-4065-a7f3-7f32c711d420\",\"name\":\"Solar\",\"owner\":\" \",\"celestialBodies\":[{\"id\":\"51cafbb6-e909-4851-8f14-d1994fdcc500\",\"name\":\"Sun\",\"amount\":1,\"price\":156.0,\"type\":\"sun\",\"orbital\":0,\"radius\":695700.0,\"volume\":1.40999997E18,\"mass\":1.9885E30,\"gravity\":274.0,\"rotationVelocity\":1997.0,\"orbitalVelocity\":0.0,\"surfaceTemperature\":1.57E7},{\"id\":\"73411e26-8750-43b5-860e-aa5c7e0cc087\",\"name\":\"Mercury\",\"amount\":1,\"price\":56.0,\"type\":\"planet\",\"orbital\":0,\"radius\":2.439,\"volume\":6.0829999E10,\"mass\":3.3011E23,\"gravity\":0.0037,\"rotationVelocity\":10892.0,\"orbitalVelocity\":47.36,\"surfaceTemperature\":340.15},{\"id\":\"a09a2066-6bfa-4abc-9b74-66a81157c7b2\",\"name\":\"Mars\",\"amount\":1,\"price\":56.0,\"type\":\"planet\",\"orbital\":0,\"radius\":3389.5,\"volume\":1.631E11,\"mass\":6.4171E23,\"gravity\":3.72,\"rotationVelocity\":0.241,\"orbitalVelocity\":24.07,\"surfaceTemperature\":213.15},{\"id\":\"9788e1d4-9ac9-48ba-b221-315ca690ee0d\",\"name\":\"Earth\",\"amount\":1,\"price\":23.0,\"type\":\"planet\",\"orbital\":0,\"radius\":6371.0,\"volume\":1.08321001E12,\"mass\":5.97237E24,\"gravity\":9806.0,\"rotationVelocity\":0.4651,\"orbitalVelocity\":29.78,\"surfaceTemperature\":287.15},{\"id\":\"42353345-4b6f-45bf-9b17-861c03026780\",\"name\":\"Jupiter\",\"amount\":1,\"price\":45.0,\"type\":\"gasGiant\",\"orbital\":0,\"radius\":71492.0,\"volume\":1.4313E15,\"mass\":1.8982E27,\"gravity\":24.79,\"rotationVelocity\":12.6,\"orbitalVelocity\":13.07,\"surfaceTemperature\":165.0},{\"id\":\"47fcdc12-6fbb-4f3c-b7d2-745f7973996a\",\"name\":\"Pluto\",\"amount\":1,\"price\":20.0,\"type\":\"dwarfPlanet\",\"orbital\":0,\"radius\":118803.0,\"volume\":7.0569999E9,\"mass\":1.303E22,\"gravity\":6.2E-4,\"rotationVelocity\":47.18,\"orbitalVelocity\":4.743,\"surfaceTemperature\":44.0}],\"price\":574.0}],\"priceCalculated\":true}";

            //String responseFromProductService = sender.sendRequestToProductService(objectMapper.writeValueAsString(detailProductRequestDTO));

            PlanetarySystemDetailDTO planetarySystems = objectMapper.readValue(responseFromProductService, PlanetarySystemDetailDTO.class);

            CurrencyPlanetarySystemMessageDTO currencyMessageDTO = new CurrencyPlanetarySystemMessageDTO(UUID.randomUUID(), planetarySystems.getPlanetarySystems(), "Euro", frontendDTO.getCurrencyToConvertTo());

            return sender.sendProductsToCurrencyService(objectMapper.writeValueAsString(currencyMessageDTO));
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/detailproduct")
    public String sendDetailProductRequestToProductServiceTest(@RequestBody String frontEndDTOAsJon) {

        objectMapper = new ObjectMapper();

        try {
            FrontendDTO frontendDTO = objectMapper.readValue(frontEndDTOAsJon, FrontendDTO.class);

            ProductServiceRequestDTO detailProductRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), frontendDTO.getDetailID(), "planetarySystem");

            String responseFromProductService = "{\"requestID\":\"9dfb5676-4a6f-4ba6-9d95-9ab731578051\",\"planetarySystems\":[{\"id\":\"aec3e84c-1c5c-4065-a7f3-7f32c711d420\",\"name\":\"Solar\",\"owner\":\" \",\"celestialBodies\":[{\"id\":\"51cafbb6-e909-4851-8f14-d1994fdcc500\",\"name\":\"Sun\",\"amount\":1,\"price\":156.0,\"type\":\"sun\",\"orbital\":0,\"radius\":695700.0,\"volume\":1.40999997E18,\"mass\":1.9885E30,\"gravity\":274.0,\"rotationVelocity\":1997.0,\"orbitalVelocity\":0.0,\"surfaceTemperature\":1.57E7},{\"id\":\"73411e26-8750-43b5-860e-aa5c7e0cc087\",\"name\":\"Mercury\",\"amount\":1,\"price\":56.0,\"type\":\"planet\",\"orbital\":0,\"radius\":2.439,\"volume\":6.0829999E10,\"mass\":3.3011E23,\"gravity\":0.0037,\"rotationVelocity\":10892.0,\"orbitalVelocity\":47.36,\"surfaceTemperature\":340.15},{\"id\":\"a09a2066-6bfa-4abc-9b74-66a81157c7b2\",\"name\":\"Mars\",\"amount\":1,\"price\":56.0,\"type\":\"planet\",\"orbital\":0,\"radius\":3389.5,\"volume\":1.631E11,\"mass\":6.4171E23,\"gravity\":3.72,\"rotationVelocity\":0.241,\"orbitalVelocity\":24.07,\"surfaceTemperature\":213.15},{\"id\":\"9788e1d4-9ac9-48ba-b221-315ca690ee0d\",\"name\":\"Earth\",\"amount\":1,\"price\":23.0,\"type\":\"planet\",\"orbital\":0,\"radius\":6371.0,\"volume\":1.08321001E12,\"mass\":5.97237E24,\"gravity\":9806.0,\"rotationVelocity\":0.4651,\"orbitalVelocity\":29.78,\"surfaceTemperature\":287.15},{\"id\":\"42353345-4b6f-45bf-9b17-861c03026780\",\"name\":\"Jupiter\",\"amount\":1,\"price\":45.0,\"type\":\"gasGiant\",\"orbital\":0,\"radius\":71492.0,\"volume\":1.4313E15,\"mass\":1.8982E27,\"gravity\":24.79,\"rotationVelocity\":12.6,\"orbitalVelocity\":13.07,\"surfaceTemperature\":165.0},{\"id\":\"47fcdc12-6fbb-4f3c-b7d2-745f7973996a\",\"name\":\"Pluto\",\"amount\":1,\"price\":20.0,\"type\":\"dwarfPlanet\",\"orbital\":0,\"radius\":118803.0,\"volume\":7.0569999E9,\"mass\":1.303E22,\"gravity\":6.2E-4,\"rotationVelocity\":47.18,\"orbitalVelocity\":4.743,\"surfaceTemperature\":44.0}],\"price\":574.0}],\"priceCalculated\":true}";

            // String responseFromProductService = sender.sendRequestToProductService(objectMapper.writeValueAsString(detailProductRequestDTO));

            PlanetarySystemDetailDTO planetarySystems = objectMapper.readValue(responseFromProductService, PlanetarySystemDetailDTO.class);

            CurrencyPlanetarySystemMessageDTO currencyMessageDTO = new CurrencyPlanetarySystemMessageDTO(UUID.randomUUID(), planetarySystems.getPlanetarySystems(), "Euro", frontendDTO.getCurrencyToConvertTo());

            return sender.sendProductsToCurrencyService(objectMapper.writeValueAsString(currencyMessageDTO));
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/createproduct")
    public String sendCreateProductRequestToProductService(@RequestBody String planetarySystemToCreate) {
        objectMapper = new ObjectMapper();

        try {
            PlanetarySystem planetarySystem = objectMapper.readValue(planetarySystemToCreate, PlanetarySystem.class);

            ProductServiceCreateDTO productServiceCreateDTO = new ProductServiceCreateDTO(UUID.randomUUID(),planetarySystem);

            return sender.sendRequestToProductService(objectMapper.writeValueAsString(productServiceCreateDTO));
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

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
    }


