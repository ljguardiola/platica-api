package com.gubrit.platica.api.currencies;

import com.gubrit.platica.api.BaseIntegrationTest;
import com.gubrit.platica.api.dto.CurrencyDto;
import com.gubrit.platica.domain.Currency;
import com.gubrit.platica.repository.CurrencyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("integration")
class GetCurrenciesIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private CurrencyRepository currencies;

  @Test
  @DisplayName("Given an anonymous user, when requesting the currencies, then it should return 401")
  void givenAnAnonymousUser_whenRequestingTheCurrencies_thenItShouldReturn401() {
    givenAnAnonymousUser()
        .when()
        .get("/currencies")
        .then()
        .statusCode(401);
  }

  @Test
  @DisplayName("Given an authenticated user, when requesting the currencies, then it should return the currencies")
  void givenAnAuthenticatedUser_whenRequestingTheCurrencies_thenItShouldReturnTheCurrencies() {

    var usdCurrency = Currency.of("USD");
    var arsCurrency = Currency.of("ARS");

    currencies.saveAll(List.of(usdCurrency, arsCurrency));

    List<CurrencyDto> response = givenAnAuthenticatedUser()
        .when()
        .get("/currencies")
        .then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .getList(".", CurrencyDto.class);

    CurrencyDto usdExpected = CurrencyDto.of(usdCurrency);
    CurrencyDto arsExpected = CurrencyDto.of(arsCurrency);

    assertEquals(2, response.size());
    assertTrue(response.contains(usdExpected));
    assertTrue(response.contains(arsExpected));
  }

}
