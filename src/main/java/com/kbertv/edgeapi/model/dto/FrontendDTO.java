package com.kbertv.edgeapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
