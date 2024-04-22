package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.domain.Account;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface GetAccountsUseCase {

  List<Account> by(@NonNull UUID userId);

}
