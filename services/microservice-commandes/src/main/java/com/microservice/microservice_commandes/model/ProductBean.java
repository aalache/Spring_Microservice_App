package com.microservice.microservice_commandes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBean {
    private Long id;
    private String titre;
    private String description;
    private String image;
    private Double prix;
}
