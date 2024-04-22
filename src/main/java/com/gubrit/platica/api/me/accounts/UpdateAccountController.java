package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.api.dto.AccountDto;
import com.gubrit.platica.cases.accounts.UpdateAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UpdateAccountController implements UpdateAccountContract {

  private final UpdateAccountUseCase updateAccount;

  @Override
  public AccountDto create(UUID userId, UUID id, UpdateAccountRequest request) {
    var command = request.toCommand(userId, id);
    var account = updateAccount.with(command);
    return AccountDto.of(account);
  }

}
