package com.gubrit.platica.cases.currencies;

import com.gubrit.platica.annotations.TransactionalReading;
import com.gubrit.platica.annotations.UseCase;
import com.gubrit.platica.domain.Currency;
import com.gubrit.platica.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class GetCurrenciesUseCaseImpl implements GetCurrenciesUseCase {

  private final CurrencyRepository repository;

  @Override
  @TransactionalReading
  public List<Currency> all() {
    log.info("Getting all currencies");
    return repository.findAll();
  }

}
