package com.kbertv.edgeapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbertv.edgeapi.client.RabbitMQClient;
import com.kbertv.edgeapi.model.*;
import com.kbertv.edgeapi.model.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class EdgeApiController {

    private ObjectMapper objectMapper;

    @Autowired
    private RabbitMQClient sender;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${productservice.routing.call.key}")
    private String productserviceCallRoutingKey;

    private static RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        EdgeApiController.rabbitTemplate = rabbitTemplate;
    }

    @Operation(summary = "Get all celestial bodies", description = "Returns all celestial bodies with converted prices", tags = {"celestial bodies"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CelestialBody.class))))})
    @GetMapping(value = "/allcomponents/{currency}", produces = "application/json")
    public String sendAllComponentsRequestToProductService(@PathVariable("currency") String currency) {
        objectMapper = new ObjectMapper();

        try {
            ProductServiceRequestDTO detailProductRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), null, "celestialBody");

            String responseFromProductService = sender.sendRequestToProductService(objectMapper.writeValueAsString(detailProductRequestDTO));

            if (!(currency.equals("Euro"))) {
                CelestialBodyDetailDTO celestialBodyDetailDTO = objectMapper.readValue(responseFromProductService, CelestialBodyDetailDTO.class);
                CurrencyCelestialBodyMessageDTO currencyMessageDTO = new CurrencyCelestialBodyMessageDTO(UUID.randomUUID(), celestialBodyDetailDTO.getCelestialBody(), "Euro", currency);
                String responseFromCurrencyService = sender.sendProductsToCurrencyService(objectMapper.writeValueAsString(currencyMessageDTO));
                return responseFromCurrencyService;
            } else {
                return responseFromProductService;
            }
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Operation(summary = "Get single celestial body by id", description = "Returns single celestial body with converted price", tags = {"celestial bodies"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CelestialBody.class))))})
    @GetMapping(value = "/detailcomponent/{uuid}/{currency}", produces = "application/json")
    public String sendDetailComponentRequestToProductService(@PathVariable("currency") String currency, @PathVariable("uuid") String uuid) {
        objectMapper = new ObjectMapper();

        try {
            ProductServiceRequestDTO detailProductRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), UUID.fromString(uuid), "celestialBody");

            String responseFromProductService = sender.sendRequestToProductService(objectMapper.writeValueAsString(detailProductRequestDTO));

            if (!(currency.equals("Euro"))) {
                CelestialBodyDetailDTO celestialBodyDetailDTO = objectMapper.readValue(responseFromProductService, CelestialBodyDetailDTO.class);
                CurrencyCelestialBodyMessageDTO currencyMessageDTO = new CurrencyCelestialBodyMessageDTO(UUID.randomUUID(), celestialBodyDetailDTO.getCelestialBody(), "Euro", currency);
                return sender.sendProductsToCurrencyService(objectMapper.writeValueAsString(currencyMessageDTO));
            } else {
                return responseFromProductService;
            }
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Operation(summary = "Get all planetary systems", description = "Returns all planetary systems with converted prices", tags = {"planetary systems"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlanetarySystem.class))))})
    @GetMapping(value = "/allproducts/{currency}", produces = "application/json")
    public String sendProductsRequestToProductServiceTest(@PathVariable("currency") String currency) {

        objectMapper = new ObjectMapper();

        try {
            ProductServiceRequestDTO detailProductRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), null, "planetarySystem");
            String responseFromProductService = sender.sendRequestToProductService(objectMapper.writeValueAsString(detailProductRequestDTO));

            if (!(currency.equals("Euro"))) {
                PlanetarySystemDetailDTO planetarySystems = objectMapper.readValue(responseFromProductService, PlanetarySystemDetailDTO.class);
                CurrencyPlanetarySystemMessageDTO currencyMessageDTO = new CurrencyPlanetarySystemMessageDTO(UUID.randomUUID(), planetarySystems.getPlanetarySystems(), "Euro", currency);
                return sender.sendProductsToCurrencyService(objectMapper.writeValueAsString(currencyMessageDTO));
            } else {
                return responseFromProductService;
            }
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Get single planetary system by id", description = "Returns single planetary system with converted price", tags = {"planetary systems"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlanetarySystem.class))))})
    @GetMapping(value = "/detailproduct/{uuid}/{currency}", produces = "application/json")
    public String sendDetailProductRequestToProductServiceTest(@PathVariable("currency") String currency, @PathVariable("uuid") String uuid) {

        objectMapper = new ObjectMapper();

        try {
            ProductServiceRequestDTO detailProductRequestDTO = new ProductServiceRequestDTO(UUID.randomUUID(), UUID.fromString(uuid), "planetarySystem");

            String responseFromProductService = sender.sendRequestToProductService(objectMapper.writeValueAsString(detailProductRequestDTO));

            if (!(currency.equals("Euro"))) {
                PlanetarySystemDetailDTO planetarySystems = objectMapper.readValue(responseFromProductService, PlanetarySystemDetailDTO.class);
                CurrencyPlanetarySystemMessageDTO currencyMessageDTO = new CurrencyPlanetarySystemMessageDTO(UUID.randomUUID(), planetarySystems.getPlanetarySystems(), "Euro", currency);
                return sender.sendProductsToCurrencyService(objectMapper.writeValueAsString(currencyMessageDTO));
            } else {
                return responseFromProductService;
            }
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Create a new planetary system", description = "Adds a new planetary system", tags = {"planetary systems"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlanetarySystem.class))))})
    @PostMapping("/createproduct")
    public String sendCreateProductRequestToProductService(@RequestBody String planetarySystemToCreate) {
        objectMapper = new ObjectMapper();

        try {
            PlanetarySystem planetarySystem = objectMapper.readValue(planetarySystemToCreate, PlanetarySystem.class);
            planetarySystem.setId(UUID.randomUUID());
            ProductServiceCreateDTO productServiceCreateDTO = new ProductServiceCreateDTO(UUID.randomUUID(), planetarySystem);

            return sender.sendRequestToProductService(objectMapper.writeValueAsString(productServiceCreateDTO));
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
