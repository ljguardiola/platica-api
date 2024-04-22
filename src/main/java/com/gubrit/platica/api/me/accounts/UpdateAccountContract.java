package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.annotations.CurrentUser;
import com.gubrit.platica.api.dto.AccountDto;
import com.gubrit.platica.cases.accounts.UpdateAccountUseCase.UpdateAccountCmd;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

public interface UpdateAccountContract {

  @PatchMapping("/me/accounts/{id}")
  @ResponseStatus(HttpStatus.OK)
  AccountDto create(
      @CurrentUser UUID userId,
      @PathVariable UUID id,
      @RequestBody @Valid UpdateAccountRequest request
  );

  record UpdateAccountRequest(
      @NotBlank
      String description,
      @NotNull
      Double initialBalance
  ) {

    public UpdateAccountCmd toCommand(UUID userId, UUID id) {
      return new UpdateAccountCmd(
          userId,
          id,
          description,
          initialBalance
      );
    }

  }

}
