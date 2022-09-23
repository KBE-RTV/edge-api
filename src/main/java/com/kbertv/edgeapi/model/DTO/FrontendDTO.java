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
public class FrontendDTO {

    private UUID detailID;

    private String currencyToConvertTo;


    public FrontendDTO(@JsonProperty("detailID") UUID detailID,
                       @JsonProperty("currencyToConvertTo") String currencyToConvertTo) {
        this.detailID = detailID;
        this.currencyToConvertTo = currencyToConvertTo;
    }
}
