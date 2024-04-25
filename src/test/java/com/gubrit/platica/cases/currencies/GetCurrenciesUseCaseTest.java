package com.gubrit.platica.cases.currencies;

import com.gubrit.platica.domain.Currency;
import com.gubrit.platica.repository.CurrencyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(SpringExtension.class)
class GetCurrenciesUseCaseTest {

  @Mock
  private CurrencyRepository currencies;

  @InjectMocks
  private GetCurrenciesUseCaseImpl getCurrencies;

  @Test
  @DisplayName("Should return all available currencies")
  void shouldReturnAllAvailableCurrencies() {

    var ars = new Currency(UUID.randomUUID(), "ARS");
    var usd = new Currency(UUID.randomUUID(), "USD");

    when(currencies.findAll())
        .thenReturn(List.of(ars, usd));

    List<Currency> currencies = getCurrencies.all();

    assertNotNull(currencies);
    assertEquals(2, currencies.size());
    assertNotNull(currencies.get(0).getId());
    assertEquals(ars.getCode(), currencies.get(0).getCode());
    assertNotNull(currencies.get(1).getId());
    assertEquals(usd.getCode(), currencies.get(1).getCode());
  }

}