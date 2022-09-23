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
public class ProductServiceRequestDTO {
    private UUID requestID;
    private UUID detailID;
    private String type;

    public ProductServiceRequestDTO(@JsonProperty("requestID") UUID requestID,
                          @JsonProperty("detailID") UUID detailID,
                          @JsonProperty("type") String type) {
        this.requestID = requestID;
        this.detailID = detailID;
        this.type = type;
    }
}