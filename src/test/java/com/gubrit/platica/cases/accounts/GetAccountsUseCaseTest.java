package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.domain.Account;
import com.gubrit.platica.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(SpringExtension.class)
class GetAccountsUseCaseTest {

  @Mock
  private AccountRepository accounts;

  @InjectMocks
  private GetAccountsUseCaseImpl getAccounts;

  @Test
  @DisplayName("Given a user id, then should return accounts for that user")
  void givenUserIdThenShouldReturnAccountsForThatUser() {

    var userId = UUID.randomUUID();
    var ars = new Account(UUID.randomUUID(), userId, "My USD account", "ARS", 1000.0);
    var usd = new Account(UUID.randomUUID(), userId, "My ARS account", "USD", 1000.0);

    when(accounts.findAllByUserId(userId))
        .thenReturn(List.of(ars, usd));

    List<Account> accounts = getAccounts.by(userId);

    assertNotNull(accounts);
    assertEquals(2, accounts.size());
    assertNotNull(accounts.get(0).getId());
    assertEquals(userId, accounts.get(0).getUser().getId());
    assertEquals(ars.getDescription(), accounts.get(0).getDescription());
    assertEquals(ars.getCurrency(), accounts.get(0).getCurrency());
    assertEquals(ars.getInitialBalance(), accounts.get(0).getInitialBalance());
    assertNotNull(accounts.get(1).getId());
    assertEquals(userId, accounts.get(1).getUser().getId());
    assertEquals(usd.getDescription(), accounts.get(1).getDescription());
    assertEquals(usd.getCurrency(), accounts.get(1).getCurrency());
    assertEquals(usd.getInitialBalance(), accounts.get(1).getInitialBalance());
  }

}