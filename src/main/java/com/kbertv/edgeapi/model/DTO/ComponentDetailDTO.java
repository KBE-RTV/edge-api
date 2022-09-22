package com.kbertv.edgeapi.model.DTO;

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
public class ComponentDetailDTO {
    private UUID requestID;
    private ArrayList<CelestialBody> celestialBody;

    public ComponentDetailDTO(@JsonProperty("requestID") UUID requestID,
                              @JsonProperty("celestialBody") ArrayList<CelestialBody> celestialBody){
        this.requestID = requestID;
        this.celestialBody = celestialBody;
    }
}
