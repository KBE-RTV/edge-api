package com.kbertv.edgeapi.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbertv.edgeapi.model.PlanetarySystem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ConversionDTO {

    private ArrayList<PlanetarySystem> planetarySystems;

    private String currencyToConvertFrom;

    private String currencyToConvertTo;


    public ConversionDTO(@JsonProperty("planetarySystems") ArrayList<PlanetarySystem> planetarySystems,
                         @JsonProperty("currencyToConvertFrom") String currencyToConvertFrom,
                         @JsonProperty("currencyToConvertTo") String currencyToConvertTo) {
        this.planetarySystems = planetarySystems;
        this.currencyToConvertFrom = currencyToConvertFrom;
        this.currencyToConvertTo = currencyToConvertTo;
    }
}
