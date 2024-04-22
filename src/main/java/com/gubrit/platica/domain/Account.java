package com.gubrit.platica.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.CascadeType.MERGE;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "accounts")
public class Account {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToOne(cascade = MERGE, optional = false)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "currency", nullable = false, updatable = false)
  private Currency currency;

  @Column(name = "initial_balance", nullable = false)
  private Double initialBalance;

  public Account(UUID id, UUID userId, String description, String currency, Double initialBalance) {
    this(id, User.of(userId), description, Currency.getInstance(currency), initialBalance);
  }

  public static Account of(UUID userId, String description, String currency, Double initialBalance) {
    return new Account(UUID.randomUUID(), userId, description, currency, initialBalance);
  }

  public void update(String description, Double initialBalance) {
    this.description = description;
    this.initialBalance = initialBalance;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Account account = (Account) object;
    return Objects.equals(id, account.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
