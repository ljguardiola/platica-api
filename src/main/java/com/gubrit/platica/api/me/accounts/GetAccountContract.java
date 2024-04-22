package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.annotations.CurrentUser;
import com.gubrit.platica.api.dto.AccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

public interface GetAccountContract {

  @GetMapping("/me/accounts/{accountId}")
  @ResponseStatus(HttpStatus.OK)
  AccountDto getAccount(
      @CurrentUser UUID userId,
      @PathVariable UUID accountId
  );

}
