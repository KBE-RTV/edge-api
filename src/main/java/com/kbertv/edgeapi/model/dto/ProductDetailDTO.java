package com.kbertv.edgeapi.model.dto;

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
public class ProductDetailDTO {
    private UUID requestID;
    private ArrayList<PlanetarySystem> planetarySystems;

    public ProductDetailDTO(@JsonProperty("requestID") UUID requestID,
                            @JsonProperty("planetarySystem") ArrayList<PlanetarySystem> planetarySystems){
        this.requestID = requestID;
        this.planetarySystems = planetarySystems;
    }
}
