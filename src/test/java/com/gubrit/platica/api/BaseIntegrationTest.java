package com.gubrit.platica.api;

import com.gubrit.platica.config.TestContainersConfiguration;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(TestContainersConfiguration.class)
public abstract class BaseIntegrationTest extends TestWithAuthentication {

  @LocalServerPort
  protected int port;

  protected RequestSpecification given() {
    return RestAssured.given().port(port);
  }

  protected RequestSpecification givenAnAnonymousUser() {
    return given();
  }

  protected RequestSpecification givenAnAuthenticatedUser() {
    return given()
        .auth()
        .oauth2(getUserToken());
  }

}
