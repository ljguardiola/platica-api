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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("integration")
class GetAccountsIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private UserRepository users;

  @Autowired
  private AccountRepository accounts;

  @AfterEach
  void tearDown() {
    users.deleteAll();
  }

  @Test
  @DisplayName("Given an anonymous user, when requesting the accounts, then it should return 401")
  void givenAnAnonymousUser_whenRequestingTheAccounts_thenItShouldReturn401() {
    givenAnAnonymousUser()
        .when()
        .get("/me/accounts")
        .then()
        .statusCode(401);
  }

  @Test
  @DisplayName("Given an authenticated user, when requesting the accounts, then it should return the accounts")
  void givenAnAuthenticatedUser_whenRequestingTheAccounts_thenItShouldReturnTheAccounts() {

    var usdAccount = Account.of(getUserId(), "My USD account", "USD", 1000.0);
    var arsAccount = Account.of(getUserId(), "My ARS account", "ARS", 1000.0);

    accounts.saveAll(List.of(usdAccount, arsAccount));

    List<AccountDto> response = givenAnAuthenticatedUser()
        .when()
        .get("/me/accounts")
        .then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .getList(".", AccountDto.class);

    AccountDto usdExpected = AccountDto.of(usdAccount);
    AccountDto arsExpected = AccountDto.of(arsAccount);

    assertEquals(2, response.size());
    assertTrue(response.contains(usdExpected));
    assertTrue(response.contains(arsExpected));
  }

}