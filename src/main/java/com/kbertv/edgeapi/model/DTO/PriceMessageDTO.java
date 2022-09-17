 package com.kbertv.edgeapi.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbertv.edgeapi.model.PlanetarySystem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

 @Getter
 @Setter
 @ToString
 public class PriceMessageDTO implements Serializable {

     private UUID callId;
     private ArrayList<PlanetarySystem> products;

     public PriceMessageDTO(@JsonProperty("callId") UUID callId,
                            @JsonProperty("products") ArrayList<PlanetarySystem> products) {
         this.callId = callId;
         this.products = products;
     }
 }

