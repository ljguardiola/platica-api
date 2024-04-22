package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.cases.accounts.CreateAccountUseCase.CreateAccountCmd;
import com.gubrit.platica.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Tag("unit")
@ExtendWith(SpringExtension.class)
class CreateAccountUseCaseTest {

  @Mock
  private AccountRepository accounts;

  @InjectMocks
  private CreateAccountUseCaseImpl createAccount;

  @Test
  @DisplayName("Given a valid command, should create an account")
  void givenValidCommandShouldCreateAccount() {

    var command = new CreateAccountCmd(randomUUID(), "My first account", "USD", 0.0);

    var account = createAccount.with(command);

    verify(accounts, times(1)).save(account);

    assertNotNull(account);
    assertNotNull(account.getId());
    assertEquals(command.userId(), account.getUser().getId());
    assertEquals(command.description(), account.getDescription());
    assertEquals(command.currency(), account.getCurrency().getCurrencyCode());
    assertEquals(command.initialBalance(), account.getInitialBalance());
  }

  @Test
  @DisplayName("Given a command with empty description, should throw exception")
  void givenCommandWithEmptyDescriptionShouldThrowException() {

    var userId = randomUUID();

    assertThrows(IllegalArgumentException.class, () -> new CreateAccountCmd(userId, "", "USD", 0.0));
  }

  @Test
  @DisplayName("Given a command with empty currency, should throw exception")
  void givenCommandWithEmptyCurrencyShouldThrowException() {

    var userId = randomUUID();

    assertThrows(IllegalArgumentException.class, () -> new CreateAccountCmd(userId, "My first account", "", 0.0));
  }

}