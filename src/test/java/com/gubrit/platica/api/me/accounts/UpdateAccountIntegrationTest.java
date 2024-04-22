package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.api.BaseIntegrationTest;
import com.gubrit.platica.api.dto.AccountDto;
import com.gubrit.platica.api.me.accounts.UpdateAccountContract.UpdateAccountRequest;
import com.gubrit.platica.domain.Account;
import com.gubrit.platica.repository.AccountRepository;
import com.gubrit.platica.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag("integration")
class UpdateAccountIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private UserRepository users;

  @Autowired
  private AccountRepository accounts;

  @AfterEach
  void tearDown() {
    users.deleteAll();
  }

  @Test
  @DisplayName("Given an anonymous user, when updating an account, then it should return 401")
  void givenAnAnonymousUser_whenUpdatingAnAccount_thenItShouldReturn401() {

    var command = new UpdateAccountRequest("My updated account", 2000.0);

    givenAnAnonymousUser()
        .contentType(APPLICATION_JSON_VALUE)
        .body(command)
        .when()
        .patch("/me/accounts/{id}", UUID.randomUUID())
        .then()
        .statusCode(401);
  }

  @Test
  @DisplayName("Given an authenticated user, when updating an account, then it should return 200")
  void shouldUpdateAccount() {

    var account = accounts.save(
        Account.of(getUserId(), "My USD account", "USD", 1000.0)
    );

    var command = new UpdateAccountRequest("My updated account", 2000.0);

    var updated = givenAnAuthenticatedUser()
        .contentType(APPLICATION_JSON_VALUE)
        .body(command)
        .when()
        .patch("/me/accounts/{id}", account.getId())
        .then()
        .statusCode(200)
        .contentType(APPLICATION_JSON_VALUE)
        .extract()
        .jsonPath()
        .getObject(".", AccountDto.class);

    assertEquals(command.description(), updated.description());
    assertEquals(command.initialBalance(), updated.initialBalance());
  }

  @Test
  @DisplayName("Given an authenticated user, when updating an account without a description, then it should return 400")
  void givenAnAuthenticatedUser_whenUpdatingAnAccountWithoutDescription_thenItShouldReturn400() {

    var account = accounts.save(
        Account.of(getUserId(), "My USD account", "USD", 1000.0)
    );

    var command = new UpdateAccountRequest(null, 2000.0);

    givenAnAuthenticatedUser()
        .contentType(APPLICATION_JSON_VALUE)
        .body(command)
        .when()
        .patch("/me/accounts/{id}", account.getId())
        .then()
        .statusCode(400);
  }

  @Test
  @DisplayName("Given an authenticated user, when updating an account without an initial balance, then it should return 400")
  void givenAnAuthenticatedUser_whenUpdatingAnAccountWithoutInitialBalance_thenItShouldReturn400() {

    var account = accounts.save(
        Account.of(getUserId(), "My USD account", "USD", 1000.0)
    );

    var command = new UpdateAccountRequest("My updated account", null);

    givenAnAuthenticatedUser()
        .contentType(APPLICATION_JSON_VALUE)
        .body(command)
        .when()
        .patch("/me/accounts/{id}", account.getId())
        .then()
        .statusCode(400);
  }

  @Test
  @DisplayName("Given an authenticated user, when updating an account that does not exist, then it should return 404")
  void givenAnAuthenticatedUser_whenUpdatingAnAccountThatDoesNotExist_thenItShouldReturn404() {

    var command = new UpdateAccountRequest("My updated account", 2000.0);

    givenAnAuthenticatedUser()
        .contentType(APPLICATION_JSON_VALUE)
        .body(command)
        .when()
        .patch("/me/accounts/{id}", UUID.randomUUID())
        .then()
        .statusCode(404);
  }

}