package com.gubrit.platica.repository;

import com.gubrit.platica.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {}
