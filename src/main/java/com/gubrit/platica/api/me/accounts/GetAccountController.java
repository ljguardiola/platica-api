package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.api.dto.AccountDto;
import com.gubrit.platica.cases.accounts.GetAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GetAccountController implements GetAccountContract {

  private final GetAccountUseCase getAccount;

  @Override
  public AccountDto getAccount(UUID userId, UUID accountId) {
    var account = getAccount.by(accountId, userId);
    return AccountDto.of(account);
  }

}
