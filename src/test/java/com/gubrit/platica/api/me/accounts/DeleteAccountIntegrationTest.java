package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.api.BaseIntegrationTest;
import com.gubrit.platica.domain.Account;
import com.gubrit.platica.repository.AccountRepository;
import com.gubrit.platica.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Tag("integration")
class DeleteAccountIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private UserRepository users;

  @Autowired
  private AccountRepository accounts;

  @AfterEach
  void tearDown() {
    users.deleteAll();
  }

  @Test
  @DisplayName("Given an anonymous user, when deleting an account, then it should return 401")
  void givenAnAnonymousUser_whenDeletingAnAccount_thenItShouldReturn401() {
    givenAnAnonymousUser()
        .when()
        .delete("/me/accounts/{id}", UUID.randomUUID())
        .then()
        .statusCode(401);
  }

  @Test
  @DisplayName("Given an authenticated user, when deleting an account, then it should return 204")
  void givenAnAuthenticatedUser_whenDeletingAnAccount_thenItShouldReturn204() {

    var account = accounts.save(
        Account.of(getUserId(), "My USD account", "USD", 1000.0)
    );

    givenAnAuthenticatedUser()
        .when()
        .delete("/me/accounts/{id}", account.getId())
        .then()
        .statusCode(204);
  }

  @Test
  @DisplayName("Given an authenticated user, when deleting an account that does not exist, then it should return 404")
  void givenAnAuthenticatedUser_whenDeletingAnAccountThatDoesNotExist_thenItShouldReturn404() {
    givenAnAuthenticatedUser()
        .when()
        .delete("/me/accounts/{id}", UUID.randomUUID())
        .then()
        .statusCode(404);
  }

}