package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.api.dto.AccountDto;
import com.gubrit.platica.cases.accounts.CreateAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CreateAccountController implements CreateAccountContract {

  private final CreateAccountUseCase createAccount;

  @Override
  public AccountDto create(UUID userId, CreateAccountRequest request) {
    var command = request.toCommand(userId);
    var account = createAccount.with(command);
    return AccountDto.of(account);

  }

}
