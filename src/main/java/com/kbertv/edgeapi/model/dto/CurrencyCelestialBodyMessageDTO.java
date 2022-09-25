package com.kbertv.edgeapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbertv.edgeapi.model.CelestialBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CurrencyCelestialBodyMessageDTO {
    private UUID requestID;

    private ArrayList<CelestialBody> celestialBody;

    private String currencyToConvertFrom;

    private String currencyToConvertTo;


    public CurrencyCelestialBodyMessageDTO(@JsonProperty("requestID") UUID requestID,
                                           @JsonProperty("celestialBody") ArrayList<CelestialBody> celestialBody,
                                           @JsonProperty("currencyToConvertFrom") String currencyToConvertFrom,
                                           @JsonProperty("currencyToConvertTo") String currencyToConvertTo) {
        this.requestID = requestID;
        this.celestialBody = celestialBody;
        this.currencyToConvertFrom = currencyToConvertFrom;
        this.currencyToConvertTo = currencyToConvertTo;
    }
}
