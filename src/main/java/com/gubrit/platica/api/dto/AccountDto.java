package com.gubrit.platica.api.dto;

import com.gubrit.platica.domain.Account;

import java.util.UUID;

public record AccountDto(
    UUID id,
    String description,
    String currency,
    Double initialBalance
) {

  public static AccountDto of(Account account) {
    return new AccountDto(
        account.getId(),
        account.getDescription(),
        account.getCurrency().getCurrencyCode(),
        account.getInitialBalance()
    );
  }

}
