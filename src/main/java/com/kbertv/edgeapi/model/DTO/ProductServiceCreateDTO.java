package com.kbertv.edgeapi.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbertv.edgeapi.model.PlanetarySystem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ProductServiceCreateDTO {
    private UUID requestID;
    private PlanetarySystem planetarySystem;

    public ProductServiceCreateDTO(@JsonProperty("requestID") UUID requestID,
                                   @JsonProperty("detailID") PlanetarySystem planetarySystem){
        this.requestID = requestID;
        this.planetarySystem = planetarySystem;
    }
}
