package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.domain.Account;
import lombok.NonNull;

import java.util.UUID;

public interface UpdateAccountUseCase {

  Account with(@NonNull UpdateAccountCmd command);

  record UpdateAccountCmd(
      @NonNull
      UUID userId,
      @NonNull
      UUID id,
      @NonNull
      String description,
      @NonNull
      Double initialBalance
  ) {

    public UpdateAccountCmd {
      if (description.isBlank()) {
        throw new IllegalArgumentException("description cannot be blank");
      }
    }

  }

}
