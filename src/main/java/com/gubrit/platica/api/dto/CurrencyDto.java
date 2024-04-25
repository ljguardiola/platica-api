package com.gubrit.platica.api.dto;

import com.gubrit.platica.domain.Currency;

import java.util.UUID;

public record CurrencyDto(
    UUID id,
    String code
) {

  public static CurrencyDto of(Currency currency) {
    return new CurrencyDto(
        currency.getId(),
        currency.getCode()
    );
  }

}
