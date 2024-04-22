package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.domain.Account;
import lombok.NonNull;

import java.util.UUID;

public interface CreateAccountUseCase {

  Account with(@NonNull CreateAccountCmd command);

  record CreateAccountCmd(
      @NonNull
      UUID userId,
      @NonNull
      String description,
      @NonNull
      String currency,
      @NonNull
      Double initialBalance
  ) {

    public CreateAccountCmd {
      if (description.isBlank()) {
        throw new IllegalArgumentException("description cannot be blank");
      }
      if (currency.isBlank()) {
        throw new IllegalArgumentException("currency cannot be blank");
      }
    }

  }

}
