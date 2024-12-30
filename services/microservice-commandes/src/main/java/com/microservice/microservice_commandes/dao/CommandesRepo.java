package com.microservice.microservice_commandes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.microservice.microservice_commandes.model.Commande;

@Repository
public interface CommandesRepo extends JpaRepository<Commande,Long> {
    
}
