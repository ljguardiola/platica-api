package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.api.BaseIntegrationTest;
import com.gubrit.platica.api.dto.AccountDto;
import com.gubrit.platica.api.me.accounts.CreateAccountContract.CreateAccountRequest;
import com.gubrit.platica.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag("integration")
class CreateAccountIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private UserRepository users;

  @AfterEach
  void tearDown() {
    users.deleteAll();
  }

  @Test
  @DisplayName("Given an anonymous user, when creating an account, then it should return 401")
  void givenAnAnonymousUser_whenCreatingAnAccount_thenItShouldReturn401() {

    var request = new CreateAccountRequest("My first account", "USD", 0.0);

    givenAnAnonymousUser()
        .contentType(APPLICATION_JSON_VALUE)
        .body(request)
        .when()
        .post("/me/accounts")
        .then()
        .statusCode(401);
  }

  @Test
  @DisplayName("Given an authenticated user, when creating an account, then it should return 201")
  void shouldCreateAccount() {

    var request = new CreateAccountRequest("My first account", "USD", 0.0);

    var response = givenAnAuthenticatedUser()
        .contentType(APPLICATION_JSON_VALUE)
        .body(request)
        .when()
        .post("/me/accounts")
        .then()
        .statusCode(201)
        .contentType(APPLICATION_JSON_VALUE)
        .extract()
        .jsonPath()
        .getObject(".", AccountDto.class);

    assertNotNull(response.id());
    assertEquals(request.description(), response.description());
    assertEquals(request.currency(), response.currency());
    assertEquals(request.initialBalance(), response.initialBalance());
  }

  @Test
  @DisplayName("Given an authenticated user, when creating an account without a description, then it should return 400")
  void givenAnAuthenticatedUser_whenCreatingAnAccountWithoutDescription_thenItShouldReturn400() {

    var request = new CreateAccountRequest(null, "USD", 0.0);

    givenAnAuthenticatedUser()
        .contentType(APPLICATION_JSON_VALUE)
        .body(request)
        .when()
        .post("/me/accounts")
        .then()
        .statusCode(400);
  }

  @Test
  @DisplayName("Given an authenticated user, when creating an account without a currency, then it should return 400")
  void givenAnAuthenticatedUser_whenCreatingAnAccountWithoutCurrency_thenItShouldReturn400() {

    var request = new CreateAccountRequest("My first account", null, 0.0);

    givenAnAuthenticatedUser()
        .contentType(APPLICATION_JSON_VALUE)
        .body(request)
        .when()
        .post("/me/accounts")
        .then()
        .statusCode(400);
  }

  @Test
  @DisplayName("Given an authenticated user, when creating an account without an initial balance, then it should return 400")
  void givenAnAuthenticatedUser_whenCreatingAnAccountWithoutInitialBalance_thenItShouldReturn400() {

    var request = new CreateAccountRequest("My first account", "USD", null);

    givenAnAuthenticatedUser()
        .contentType(APPLICATION_JSON_VALUE)
        .body(request)
        .when()
        .post("/me/accounts")
        .then()
        .statusCode(400);
  }

}