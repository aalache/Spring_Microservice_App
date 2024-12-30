# Spring Microservices Architecture

Ce projet met en œuvre une architecture de microservices avec Spring Boot. Il comprend :

1. Un **Config Server** pour centraliser les configurations.
2. Un **Eureka Server** pour la découverte des services.
3. Deux microservices (**microservice-produits** et **microservice-commandes**) enregistrés dans Eureka.
4. Une communication entre microservices via **OpenFeign**.

## Fonctionnalités

- **Config Server** :
  - Centralise les fichiers de configuration des microservices.
  - Récupère les configurations depuis un dépôt GitHub.

- **Eureka Server** :
  - Fournit un registre des services pour faciliter la découverte.
  
- **Microservices** :
  - `microservice-produits` : Gère les informations sur les produits.
  - `microservice-commandes` : Gère les commandes des utilisateurs.
  
- **Communication inter-services** :
  - Utilisation d'OpenFeign pour appeler les API entre microservices.

## Structure du Projet

```
|-- config-server
|-- eureka-server
|-- microservice-produits
|-- microservice-commandes
```

## Prérequis

- Java 17+
- Maven 3.6+
- Un dépôt GitHub contenant les fichiers de configuration des microservices.

## Installation

### 1. Config Server

1. Clonez le répertoire `config-server`.
2. Modifiez le fichier `application.properties` pour ajouter l'URL de votre dépôt GitHub :

   ```properties
   spring.cloud.config.server.git.uri=https://github.com/<votre-utilisateur>/<votre-repo>.git
   spring.cloud.config.server.git.default-label=main
   ```
3. Lancez le serveur avec :
   ```bash
   mvn spring-boot:run
   ```

### 2. Eureka Server

1. Clonez le répertoire `eureka-server`.
2. Lancez le serveur avec :
   ```bash
   mvn spring-boot:run
   ```

### 3. Microservice Produits

1. Clonez le répertoire `microservice-produits`.
2. Ajoutez la configuration Eureka dans `application.properties` :

   ```properties
   spring.application.name=microservice-produits
   eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
   spring.cloud.config.uri=http://localhost:8888
   ```
3. Lancez le microservice avec :
   ```bash
   mvn spring-boot:run
   ```

### 4. Microservice Commandes

1. Clonez le répertoire `microservice-commandes`.
2. Ajoutez la configuration Eureka dans `application.properties` :

   ```properties
   spring.application.name=microservice-commandes
   eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
   spring.cloud.config.uri=http://localhost:8888
   ```
3. Lancez le microservice avec :
   ```bash
   mvn spring-boot:run
   ```

### 5. Communication avec OpenFeign

- Assurez-vous qu'OpenFeign est activé dans les microservices :

   ```java
   @EnableFeignClients
   ```

- Exemple dans `microservice-commandes` pour appeler `microservice-produits` :

   ```java
   @FeignClient(name = "microservice-produits")
   public interface MicroserviceProductProxy {
       @GetMapping("/produits/{id}")
       Produit getProduitById(@PathVariable Long id);...
   }
   ```

## Test de l'Application

1. Accédez à l'interface Eureka pour vérifier l'enregistrement des services :
   - URL : [http://localhost:8761](http://localhost:8761)

2. Faites un appel via `microservice-commandes` pour vérifier la communication avec `microservice-produits`.

## Contribuer

Les contributions sont les bienvenues ! Pour toute modification, veuillez ouvrir une pull request.

## License

Ce projet est sous licence MIT. Consultez le fichier LICENSE pour plus d'informations.

