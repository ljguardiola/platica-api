package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.annotations.TransactionalWriting;
import com.gubrit.platica.annotations.UseCase;
import com.gubrit.platica.domain.Account;
import com.gubrit.platica.exception.AccountNotFoundException;
import com.gubrit.platica.repository.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateAccountUseCaseImpl implements UpdateAccountUseCase {

  private final AccountRepository accounts;

  @Override
  @TransactionalWriting
  public Account with(@NonNull UpdateAccountCmd command) {
    log.info("Updating account: {}", command);
    var account = accounts.findByIdAndUserId(command.id(), command.userId())
                          .orElseThrow(() -> new AccountNotFoundException(command.id()));
    account.update(command.description(),
                   command.initialBalance());
    accounts.save(account);
    return account;
  }

}
