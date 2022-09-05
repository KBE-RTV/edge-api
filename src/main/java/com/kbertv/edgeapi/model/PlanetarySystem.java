package com.kbertv.edgeapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetarySystem {
    private UUID id;
    private String name;
    private String owner;
    private ArrayList<UUID> celestialBodies = new ArrayList<>();
    private float price;
}
