package com.microservice.microservice_commandes.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservice.microservice_commandes.model.ProductBean;

import java.util.List;

@FeignClient(name = "microservice-produits")
public interface MicroserviceProductsProxy {
    
    @GetMapping("/products")
    public List<ProductBean> getProduits();

    @GetMapping("/products/{id}")
    public ProductBean getProduit(@PathVariable Long id);
    
    @PostMapping("/products/add")
    public ResponseEntity<String> postProduit(@RequestBody ProductBean product);

    @PutMapping("/products/{id}")
    public ResponseEntity<String> putMethodName(@PathVariable Long id, @RequestBody ProductBean product);

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id);
}
