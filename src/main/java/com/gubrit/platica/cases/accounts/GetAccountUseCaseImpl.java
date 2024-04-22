package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.annotations.TransactionalReading;
import com.gubrit.platica.annotations.UseCase;
import com.gubrit.platica.domain.Account;
import com.gubrit.platica.exception.AccountNotFoundException;
import com.gubrit.platica.repository.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class GetAccountUseCaseImpl implements GetAccountUseCase {

  private final AccountRepository accounts;

  @Override
  @TransactionalReading
  public Account by(@NonNull UUID id, @NonNull UUID userId) {
    log.info("Getting account: {} for userId: {}", id, userId);
    return accounts.findByIdAndUserId(id, userId)
                   .orElseThrow(() -> new AccountNotFoundException(id));
  }

}
