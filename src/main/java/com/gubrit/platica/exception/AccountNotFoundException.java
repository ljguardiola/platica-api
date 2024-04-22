package com.gubrit.platica.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(UUID id) {
    super("Account not found: " + id);
  }

}
