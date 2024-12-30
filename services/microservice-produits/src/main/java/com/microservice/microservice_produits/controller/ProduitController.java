package com.microservice.microservice_produits.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.microservice_produits.dao.ProductRepo;
import com.microservice.microservice_produits.modele.Product;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@AllArgsConstructor
public class ProduitController implements HealthIndicator {
    
    private ProductRepo productRepo;

    @GetMapping("/products")
    public List<Product> getProduits() {
        return productRepo.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProduit(@PathVariable Long id) {
        return productRepo.findById(id).orElse(null);
    }
    
    @PostMapping("/products/add")
    public ResponseEntity<String> postProduit(@RequestBody Product product) {
        if(product != null){
            Product res = productRepo.save(product);
            return ResponseEntity.accepted().body(res.toString());
        }else{
            return ResponseEntity.badRequest().body("The product can not be added, please check the product info!!!");
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> putMethodName(@PathVariable Long id, @RequestBody Product product) {
        Product productFound = productRepo.findById(id).orElse(null);
        if(product != null){
            product.setId(productFound.getId());
            Product res = productRepo.save(product);
            return ResponseEntity.accepted().body("Product was updated to : \n" + res.toString());
        }else{
            return ResponseEntity.badRequest().body("No product with id " + id + " was found !!!");
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        Product productFound = productRepo.findById(id).orElse(null);
        if(productFound != null){
            productRepo.delete(productFound);
            return ResponseEntity.ok().body("Product with id " + id + " was deleted successfuly");
        }else{
           return ResponseEntity.badRequest().body("No product with id " + id + " was found!!!"); 
        }
    }

    @Override
    public Health health() {
        if(productRepo.findAll().isEmpty()){
            return Health.down().build();
        }else{
            return Health.up().build();
        }
    }
    
}
