package com.gubrit.platica.api.currencies;

import com.gubrit.platica.api.dto.CurrencyDto;
import com.gubrit.platica.cases.currencies.GetCurrenciesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetCurrenciesController implements GetCurrenciesContract {

  private final GetCurrenciesUseCase getCurrencies;

  @Override
  public List<CurrencyDto> getCurrencies() {
    return getCurrencies.all()
                        .stream()
                        .map(CurrencyDto::of)
                        .toList();
  }

}
