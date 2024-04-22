package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.cases.accounts.UpdateAccountUseCase.UpdateAccountCmd;
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
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(SpringExtension.class)
class UpdateAccountUseCaseTest {

  @Mock
  private AccountRepository accounts;

  @InjectMocks
  private UpdateAccountUseCaseImpl updateAccount;

  @Test
  @DisplayName("Given valid command, should update account")
  void givenValidCommandShouldUpdateAccount() {

    var saved = new Account(randomUUID(), randomUUID(), "My account", "USD", 1000.0);
    var command = new UpdateAccountCmd(saved.getId(), saved.getUser().getId(), "My account updated", 0.0);

    when(accounts.findByIdAndUserId(any(UUID.class), any(UUID.class)))
        .thenReturn(Optional.of(saved));

    Account account = updateAccount.with(command);

    verify(accounts, times(1)).save(account);

    assertNotNull(account);
    assertEquals(command.description(), account.getDescription());
    assertEquals(command.initialBalance(), account.getInitialBalance());

    // This should be the same as the saved account
    assertEquals(saved.getId(), account.getId());
    assertEquals(saved.getUser(), account.getUser());
    assertEquals(saved.getCurrency(), account.getCurrency());
  }

  @Test
  @DisplayName("Given an account that does not exist, should throw exception")
  void givenAccountDoesNotExistShouldThrowException() {

    UpdateAccountCmd command = new UpdateAccountCmd(randomUUID(), randomUUID(), "My USD account", 1000.0);

    when(accounts.findById(command.id()))
        .thenReturn(Optional.empty());

    assertThrows(AccountNotFoundException.class, () -> updateAccount.with(command));
  }

  @Test
  @DisplayName("Given a blank description, should throw exception")
  void shouldThrowExceptionWhenDescriptionIsBlank() {

    var accountId = randomUUID();
    var userId = randomUUID();

    assertThrows(IllegalArgumentException.class, () -> new UpdateAccountCmd(userId, accountId, "", 1000.0));
  }

}