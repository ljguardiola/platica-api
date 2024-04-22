package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.api.dto.AccountDto;
import com.gubrit.platica.cases.accounts.GetAccountsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GetAccountsController implements GetAccountsContract {

  private final GetAccountsUseCase getAccounts;

  @Override
  public List<AccountDto> getAccounts(UUID userId) {
    return getAccounts.by(userId)
                      .stream()
                      .map(AccountDto::of)
                      .toList();
  }

}
