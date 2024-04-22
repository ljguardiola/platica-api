package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.annotations.TransactionalWriting;
import com.gubrit.platica.annotations.UseCase;
import com.gubrit.platica.domain.Account;
import com.gubrit.platica.repository.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

  private final AccountRepository accounts;

  @Override
  @TransactionalWriting
  public Account with(@NonNull CreateAccountCmd command) {
    log.info("Creating account: {}", command);
    var account = Account.of(command.userId(),
                             command.description(),
                             command.currency(),
                             command.initialBalance());
    accounts.save(account);
    return account;
  }

}
