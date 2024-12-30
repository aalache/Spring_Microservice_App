package com.microservice.microservice_produits.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.microservice_produits.modele.Product;

public interface ProductRepo extends JpaRepository<Product,Long> {
    
}
