package com.gubrit.platica.api.currencies;

import com.gubrit.platica.api.dto.CurrencyDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface GetCurrenciesContract {

  @GetMapping("/currencies")
  @ResponseStatus(HttpStatus.OK)
  List<CurrencyDto> getCurrencies();

}
