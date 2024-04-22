package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.annotations.CurrentUser;
import com.gubrit.platica.api.dto.AccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

public interface GetAccountsContract {

  @GetMapping("/me/accounts")
  @ResponseStatus(HttpStatus.OK)
  List<AccountDto> getAccounts(
      @CurrentUser UUID userId
  );

}
