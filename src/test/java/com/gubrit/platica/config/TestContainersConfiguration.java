package com.gubrit.platica.config;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainersConfiguration {

  @Bean
  @ServiceConnection
  PostgreSQLContainer<?> postgresContainer() {
    return new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.5"));
  }

  @Bean
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  KeycloakContainer keycloakContainer(DynamicPropertyRegistry registry) {
    var container = new KeycloakContainer("quay.io/keycloak/keycloak:24.0.3");
    container.withRealmImportFile("keycloak/realm.json");
    registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                 () -> container.getAuthServerUrl() + "/realms/gubrit");
    registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
                 () -> container.getAuthServerUrl() + "/realms/gubrit/protocol/openid-connect/certs");
    return container;
  }

}
