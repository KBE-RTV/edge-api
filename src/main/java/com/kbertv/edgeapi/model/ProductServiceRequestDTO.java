package com.kbertv.edgeapi.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductServiceRequestDTO {

    private UUID requestID;
    private List<UUID> detailID;
    private String type;

}
