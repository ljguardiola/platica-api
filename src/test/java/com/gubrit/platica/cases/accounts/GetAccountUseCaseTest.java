package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.domain.Account;
import com.gubrit.platica.exception.AccountNotFoundException;
import com.gubrit.platica.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(SpringExtension.class)
class GetAccountUseCaseTest {

  @Mock
  private AccountRepository accounts;

  @InjectMocks
  private GetAccountUseCaseImpl getAccount;

  @Test
  @DisplayName("Given a user id and an account id, then should return account for that user")
  void givenUserIdAndAccountIdThenShouldReturnAccountForThatUser() {

    var usdAccount = new Account(randomUUID(), randomUUID(), "My USD account", "USD", 1000.0);

    when(accounts.findByIdAndUserId(usdAccount.getId(), usdAccount.getUser().getId()))
        .thenReturn(Optional.of(usdAccount));

    Account account = getAccount.by(usdAccount.getId(), usdAccount.getUser().getId());

    assertNotNull(account);
    assertEquals(usdAccount.getId(), account.getId());
    assertEquals(usdAccount.getUser(), account.getUser());
    assertEquals(usdAccount.getDescription(), account.getDescription());
    assertEquals(usdAccount.getCurrency(), account.getCurrency());
    assertEquals(usdAccount.getInitialBalance(), account.getInitialBalance());
  }

  @Test
  @DisplayName("Given a user id and an account id, then should throw exception when account not found")
  void givenUserIdAndAccountIdThenShouldThrowExceptionWhenAccountNotFound() {

    var accountID = randomUUID();
    var userID = randomUUID();

    when(accounts.findByIdAndUserId(accountID, userID))
        .thenReturn(Optional.empty());

    assertThrows(AccountNotFoundException.class, () -> getAccount.by(userID, accountID));
  }

}