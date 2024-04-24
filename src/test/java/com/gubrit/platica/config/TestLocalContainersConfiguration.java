package com.gubrit.platica.config;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@Slf4j
public class TestLocalContainersConfiguration {

  @Bean
  @ServiceConnection
  PostgreSQLContainer<?> postgresContainer() {
    var container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.5"));
    container.withReuse(true);
    container.withDatabaseName("platica");
    container.withUsername("platica");
    container.withPassword("s3cr3t");
    container.setPortBindings(List.of("23451:5432"));
    return container;
  }

  @Bean
  KeycloakContainer keycloakContainer(DynamicPropertyRegistry registry) {
    var container = new KeycloakContainer("quay.io/keycloak/keycloak:24.0.3");
    container.withReuse(true);
    container.withRealmImportFile("keycloak/realm.json");
    container.withFeaturesEnabled("token-exchange", "admin-fine-grained-authz");
    container.setPortBindings(List.of("8081:8080"));
    registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                 () -> container.getAuthServerUrl() + "/realms/gubrit");
    registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
                 () -> container.getAuthServerUrl() + "/realms/gubrit/protocol/openid-connect/certs");
    return container;
  }

}
