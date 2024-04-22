package com.gubrit.platica.cases.accounts;

import lombok.NonNull;

import java.util.UUID;

public interface DeleteAccountUseCase {

  void with(@NonNull DeleteAccountCommand command);

  record DeleteAccountCommand(
      @NonNull
      UUID userId,
      @NonNull
      UUID accountId
  ) {
  }

}
