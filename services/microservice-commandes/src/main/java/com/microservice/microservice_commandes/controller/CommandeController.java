package com.microservice.microservice_commandes.controller;

import java.util.List;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.microservice_commandes.config.ApplicationPropertiesConfiguration;
import com.microservice.microservice_commandes.dao.CommandesRepo;
import com.microservice.microservice_commandes.model.Commande;
import com.microservice.microservice_commandes.model.ProductBean;
import com.microservice.microservice_commandes.proxies.MicroserviceProductsProxy;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@AllArgsConstructor
public class CommandeController implements HealthIndicator {

    private CommandesRepo commandesRepo;
    private ApplicationPropertiesConfiguration appProperties;
    private MicroserviceProductsProxy productsProxy;

    

    @GetMapping("/commandes")
    public List<Commande> getAllCommandes() {
        List<Commande> commandes =commandesRepo.findAll().reversed();
        if(!commandes.isEmpty()){
            return commandesRepo.findAll().subList(0,appProperties.getCommandesLast().intValue());
        }
        return commandes;
    }

    @GetMapping("/commandes/{id}")
    public Commande getCommandeById(@PathVariable Long id) {
        Commande commande = commandesRepo.findById(id).orElse(null);
        if(commande != null){
            return commande;
        }else{
            return null;   
        }
    }

    @PostMapping("/commandes/add")
    public ResponseEntity<String> postCommande(@RequestBody Commande commande) {
        if(commande != null){
            ProductBean productFound = productsProxy.getProduit(commande.getProduct_ID());
            if(productFound != null){
                Commande res = commandesRepo.save(commande);
                return ResponseEntity.accepted().body(res.toString());
            }else{
                return ResponseEntity.badRequest().body("No product found with id " + commande.getProduct_ID());
            }
        }else{
            return ResponseEntity.badRequest().body("commande can not be added!!!");
        }
    }

    @PutMapping("commandes/{id}")
    public ResponseEntity<String> updateCommande(@PathVariable Long id, @RequestBody Commande commande) {
        Commande commandeFound = commandesRepo.findById(id).orElse(null);
        ProductBean productFound = productsProxy.getProduit(commande.getProduct_ID());
        if(commandeFound != null && productFound != null){
            commande.setId(commandeFound.getId());
            Commande res = commandesRepo.save(commande);
            return ResponseEntity.accepted().body(res.toString());
        }
        else if(productFound == null){
            return ResponseEntity.badRequest().body("NO product with id " + commande.getProduct_ID() + " was found !!!");
        }else{
            return ResponseEntity.badRequest().body("NO commande with id " + id + " was found !!!");
        }
    }
    
    @DeleteMapping("/commandes/{id}")
    public ResponseEntity<String> deleteCommande(@PathVariable Long id){
        Commande commandeFound = commandesRepo.findById(id).orElse(null);
        if(commandeFound != null){
            commandesRepo.deleteById(id);
            return ResponseEntity.ok().body("commande with id " + id + " was deleted successfuly");
        }else{
            return ResponseEntity.badRequest().body("No commande with id " + id + "was found !!!");
        }
    }
    

    @Override
    public Health health() {
        System.out.println("****** Actuator :: CommandeController Health() ");
        List<Commande> commandes = commandesRepo.findAll();
        if(commandes.isEmpty()){
            return Health.down().build();
        }else{
            return Health.up().build();
        }
    }
    
}
