package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.api.BaseIntegrationTest;
import com.gubrit.platica.api.dto.AccountDto;
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

@Tag("integration")
class GetAccountIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private UserRepository users;

  @Autowired
  private AccountRepository accounts;

  @AfterEach
  void tearDown() {
    users.deleteAll();
  }

  @Test
  @DisplayName("Given an anonymous user, when requesting an account by id, then it should return 401")
  void givenAnAnonymousUser_whenRequestingAnAccountById_thenItShouldReturn401() {
    givenAnAnonymousUser()
        .when()
        .get("/me/accounts/{id}", UUID.randomUUID())
        .then()
        .statusCode(401);
  }

  @Test
  @DisplayName("Given an authenticated user, when requesting an account by id, then it should return the account")
  void givenAnAuthenticatedUser_whenRequestingAnAccountById_thenItShouldReturnTheAccount() {

    var account = accounts.save(
        Account.of(getUserId(), "My USD account", "USD", 1000.0)
    );

    AccountDto response = givenAnAuthenticatedUser()
        .when()
        .get("/me/accounts/{id}", account.getId())
        .then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .getObject(".", AccountDto.class);

    assertEquals(account.getId(), response.id());
    assertEquals(account.getDescription(), response.description());
    assertEquals(account.getCurrency().getCurrencyCode(), response.currency());
    assertEquals(account.getInitialBalance(), response.initialBalance());
  }

  @Test
  @DisplayName("Given an authenticated user, when requesting an account that does not exist, then it should return 404")
  void givenAnAuthenticatedUser_whenRequestingAnAccountThatDoesNotExist_thenItShouldReturn404() {
    givenAnAuthenticatedUser()
        .when()
        .get("/me/accounts/{id}", UUID.randomUUID())
        .then()
        .statusCode(404);
  }

}