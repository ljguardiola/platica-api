package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.cases.accounts.DeleteAccountUseCase.DeleteAccountCommand;
import com.gubrit.platica.exception.AccountNotFoundException;
import com.gubrit.platica.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(SpringExtension.class)
class DeleteAccountUseCaseTest {

  @Mock
  private AccountRepository accounts;

  @InjectMocks
  private DeleteAccountUseCaseImpl deleteAccount;

  @Test
  @DisplayName("Given valid command, should delete account")
  void givenValidCommandShouldDeleteAccount() {

    DeleteAccountCommand command = new DeleteAccountCommand(randomUUID(), randomUUID());

    when(accounts.deleteByIdAndUserId(command.accountId(), command.userId()))
        .thenReturn(1L);

    assertDoesNotThrow(() -> deleteAccount.with(command));
  }

  @Test
  @DisplayName("Given invalid command, should throw exception when account not found")
  void givenInvalidCommandShouldThrowExceptionWhenAccountNotFound() {

    DeleteAccountCommand command = new DeleteAccountCommand(randomUUID(), randomUUID());

    when(accounts.deleteByIdAndUserId(command.accountId(), command.userId()))
        .thenReturn(0L);

    assertThrows(AccountNotFoundException.class, () -> deleteAccount.with(command));
  }

}