package com.microservice.microservice_commandes.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String quantite;
    private Double montant;
    @DateTimeFormat
    private LocalDate date;
    private Long product_ID;
}
