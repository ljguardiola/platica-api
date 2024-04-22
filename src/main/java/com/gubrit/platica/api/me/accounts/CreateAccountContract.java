package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.annotations.CurrentUser;
import com.gubrit.platica.api.dto.AccountDto;
import com.gubrit.platica.cases.accounts.CreateAccountUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

public interface CreateAccountContract {

  @PostMapping("/me/accounts")
  @ResponseStatus(HttpStatus.CREATED)
  AccountDto create(
      @CurrentUser UUID user,
      @RequestBody @Valid CreateAccountRequest request
  );

  record CreateAccountRequest(
      @NotBlank
      String description,
      @NotBlank
      String currency,
      @NotNull
      Double initialBalance
  ) {

    public CreateAccountUseCase.CreateAccountCmd toCommand(UUID userId) {
      return new CreateAccountUseCase.CreateAccountCmd(
          userId,
          description,
          currency,
          initialBalance
      );
    }

  }

}
