package com.kbertv.edgeapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedPlanetarySystem {

    private String name;
    private String[] celestialBodies;
}
