package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.cases.accounts.DeleteAccountUseCase;
import com.gubrit.platica.cases.accounts.DeleteAccountUseCase.DeleteAccountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeleteAccountController implements DeleteAccountContract {

  private final DeleteAccountUseCase deleteAccount;

  @Override
  public void delete(UUID userId, UUID accountId) {
    var command = new DeleteAccountCommand(userId, accountId);
    deleteAccount.with(command);
  }

}
