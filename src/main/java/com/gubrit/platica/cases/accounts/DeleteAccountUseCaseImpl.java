package com.gubrit.platica.cases.accounts;

import com.gubrit.platica.annotations.TransactionalWriting;
import com.gubrit.platica.annotations.UseCase;
import com.gubrit.platica.exception.AccountNotFoundException;
import com.gubrit.platica.repository.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeleteAccountUseCaseImpl implements DeleteAccountUseCase {

  private final AccountRepository accounts;

  @Override
  @TransactionalWriting
  public void with(@NonNull DeleteAccountCommand command) {
    log.info("Deleting account: {}", command);
    if (accounts.deleteByIdAndUserId(command.accountId(), command.userId()) == 0) {
      throw new AccountNotFoundException(command.accountId());
    }
  }

}
