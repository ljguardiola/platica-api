package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.domain.Account;
import lombok.NonNull;

import java.util.UUID;

public interface GetAccountUseCase {

  Account by(@NonNull UUID id, @NonNull UUID userId);

}
