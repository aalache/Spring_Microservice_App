package com.microservice.microservice_commandes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties("mes-config-ms")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RefreshScope
public class ApplicationPropertiesConfiguration {
    private Long commandesLast;
}
