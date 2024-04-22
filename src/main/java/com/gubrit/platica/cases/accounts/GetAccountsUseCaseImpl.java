package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.annotations.TransactionalReading;
import com.gubrit.platica.annotations.UseCase;
import com.gubrit.platica.domain.Account;
import com.gubrit.platica.repository.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class GetAccountsUseCaseImpl implements GetAccountsUseCase {

  private final AccountRepository repository;

  @Override
  @TransactionalReading
  public List<Account> by(@NonNull UUID userId) {
    log.info("Getting accounts for userId: {}", userId);
    return repository.findAllByUserId(userId);
  }

}
